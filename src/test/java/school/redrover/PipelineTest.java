package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.List;

public class PipelineTest extends BaseTest {

    private static final String JOB_NAME = "NewPipeline";

    @Test
    public void testCreatePipeline() {
        boolean pipeLineCreated = new HomePage(getDriver())
                .clickNewItem()
                .createPipeline(JOB_NAME)
                .goHomePage()
                .getJobList()
                .contains(JOB_NAME);

        Assert.assertTrue(pipeLineCreated);
    }

    @Test
    public void testCreateWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectPipelineProject();

        Assert.assertEquals(newItemPage.getRequiredNameErrorMessage(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
        Assert.assertEquals(newItemPage.getRequiredNameErrorMessageColor(), "rgba(255, 0, 0, 1)");
    }

    @Ignore
    @Test
    public void testCreateWithDuplicateName() {
        TestUtils.createPipeline(this, JOB_NAME, true);

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(JOB_NAME)
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘" + JOB_NAME + "’");
    }

    @Test
    public void testPipelineRename() {
        final String updatedJobName = "Updated job name";

        TestUtils.createPipeline(this, JOB_NAME, false);

        String currentName = new PipelineDetailsPage(getDriver())
                .clickRenameInSideMenu()
                .enterNewName(updatedJobName)
                .clickRenameButton(new PipelineDetailsPage(getDriver()))
                .goHomePage()
                .getJobList()
                .toString();

        Assert.assertTrue(currentName.contains(updatedJobName));
    }

    @Test
    public void testVerifyBuildIconOnDashboard() {
        TestUtils.createPipeline(this, JOB_NAME, false);

        boolean buildIconIsDisplayed = new PipelineDetailsPage(getDriver())
                .clickBuildNow()
                .isBuildIconDisplayed();

        Assert.assertTrue(buildIconIsDisplayed);
    }

    @Test
    public void testPipelineNoNameError() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .createPipeline(JOB_NAME)
                .clickSaveButton()
                .goHomePage()
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickRenameInSideMenu()
                .clearInputName()
                .clickRenameButton(new ErrorPage(getDriver()))
                .getErrorFromMainPanel();

        Assert.assertEquals(errorMessage, "Error" + '\n' + "No name is specified");
    }

    @Test
    public void testCreatePipelineProject() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(JOB_NAME)
                .selectPipelineProject()
                .clickOk(new PipelineConfigurationPage(getDriver()))
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatePipeline")
    public void testOpenLogsFromStageView() {
        String stageLogsText = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickConfigure()
                .selectPipelineScriptSampleByValue("hello")
                .clickSaveButton()
                .clickBuildNow()
                .clickLogsInStageView().getStageLogsModalText();

        Assert.assertEquals(stageLogsText, "Hello World");
    }

    @Test
    public void testBuildRunTriggeredByAnotherProject() {
        final String upstreamPipelineName = "Upstream Pipe";

        TestUtils.createPipeline(this, JOB_NAME, true);
        TestUtils.createPipeline(this, upstreamPipelineName, false);

        boolean isJobInBuildQueue = new PipelineDetailsPage(getDriver())
                .clickConfigure()
                .setBuildAfterOtherProjectsCheckbox()
                .setProjectsToWatch(JOB_NAME)
                .clickAlwaysTriggerRadio()
                .clickSaveButton()
                .goHomePage()
                .clickBuildByGreenArrow(JOB_NAME)
                .isJobInBuildQueue(upstreamPipelineName);

        Assert.assertTrue(isJobInBuildQueue);
    }

    @Test
    public void testStagesAreDisplayedInStageView() {
        final List<String> stageNames = List.of(new String[]{"test", "build", "deploy"});
        final String pipelineScript = "stage('test') {}\nstage('build') {}\nstage('deploy') {}";

        TestUtils.createPipeline(this, JOB_NAME, false);

        List<String> actualStageNames = new PipelineDetailsPage(getDriver())
                .clickConfigure()
                .setPipelineScript(pipelineScript)
                .clickSaveButton()
                .clickBuildNow()
                .getStagesNames();

        Assert.assertEquals(actualStageNames, stageNames);
    }

    @Ignore
    @Test
    public void testBuildWithStringParameter() {
        final String parameterName = "textParam";
        final String parameterValue = "some text";
        final String pipelineScript = String.format("stage('test') {\necho \"${%s}\"\n", parameterName);

        TestUtils.createPipeline(this, JOB_NAME, false);

        String logsText = new PipelineDetailsPage(getDriver())
                .clickConfigure()
                .clickProjectIsParameterized()
                .clickAddParameter()
                .selectStringParameter()
                .setParameterName(parameterName)
                .setPipelineScript(pipelineScript)
                .clickSaveButton()
                .clickBuildWithParameters()
                .setStringParameterValue(parameterValue)
                .clickBuildButton(new PipelineDetailsPage(getDriver()))
                .clickLogsInStageView()
                .getStageLogsModalText();

        Assert.assertTrue(logsText.contains(parameterValue));
    }

    @Test
    public void testVerifyChoiceParameterCanBeSet() {
        List<String> parameterChoices = Arrays.asList("one", "two");

        List<String> buildParameters = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(JOB_NAME)
                .selectPipelineProject()
                .clickOk(new PipelineConfigurationPage(getDriver()))
                .clickProjectIsParameterized()
                .clickAddParameter()
                .selectChoiceParameter().setParameterName("parameterName")
                .setParameterChoices(parameterChoices)
                .clickSaveButton()
                .clickBuildWithParameters()
                .getChoiceParameterOptions();

        Assert.assertEquals(buildParameters, parameterChoices);
    }

    @Test(dependsOnMethods = "testDescriptionDisplays")
    public void testDelete() {
        boolean isPipelineExist = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .deleteFromSideMenu()
                .isProjectExist(JOB_NAME);

        Assert.assertFalse(isPipelineExist);

    }

    @Test
    public void testDescriptionDisplays() {
        final String description = "Description of the Pipeline";

        TestUtils.createPipeline(this, JOB_NAME, true);

        String actualDescription = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickAddDescription()
                .inputDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Ignore
    @Test(dependsOnMethods = "testDescriptionDisplays")
    public void testPermalinksIsEmpty() {
        boolean isPermalinksEmpty = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .isPermalinksEmpty();

        Assert.assertTrue(isPermalinksEmpty);
    }

    @Ignore
    @Test
    public void testPermalinksContainBuildInformation() {
        final List<String> expectedPermalinksList = List.of(
                "Last build (#1)",
                "Last stable build (#1)",
                "Last successful build (#1)",
                "Last completed build (#1)"
        );

        TestUtils.createPipeline(this, JOB_NAME, true);

        List<String> actualPermalinksList = new HomePage(getDriver())
                .clickBuildByGreenArrow(JOB_NAME)
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .getPermalinksList();

        Assert.assertEquals(actualPermalinksList.size(), 4);
        Assert.assertEquals(actualPermalinksList, expectedPermalinksList);
    }

    @Test
    public void testStageViewBeforeBuild() {
        TestUtils.createPipeline(this, JOB_NAME, true);

        String stageViewText = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .getStageViewAlertText();

        Assert.assertEquals(stageViewText, "No data available. This Pipeline has not yet run.");
    }

    @Test
    public void testSaveSettingsWhileConfigure() {
        TestUtils.createPipeline(this, JOB_NAME, true);

        boolean isDoNotAllowConcurrentBuildsSelected = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickConfigure()
                .clickDoNotAllowConcurrentBuilds()
                .clickSaveButton()
                .clickConfigure()
                .isDoNotAllowConcurrentBuildsSelected();

        Assert.assertTrue(isDoNotAllowConcurrentBuildsSelected);
    }

    @Test(dependsOnMethods = "testSaveSettingsWhileConfigure")
    public void testUnsavedSettingsWhileConfigure() {
        boolean isDoNotAllowConcurrentBuildSelected = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickConfigure()
                .clickDoNotAllowConcurrentBuilds()
                .goHomePage()
                .acceptAlert(new HomePage(getDriver()))
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickConfigure()
                .isDoNotAllowConcurrentBuildsSelected();

        Assert.assertTrue(isDoNotAllowConcurrentBuildSelected);
    }

    @Test
    public void testTooltipsDescriptionCompliance() {
        List<String> tooltipsTextsList = List.of(
                "Help for feature: Discard old builds",
                "Help for feature: Pipeline speed/durability override",
                "Help for feature: Preserve stashes from completed builds",
                "Help for feature: This project is parameterized",
                "Help for feature: Throttle builds",
                "Help for feature: Build after other projects are built",
                "Help for feature: Build periodically",
                "Help for feature: GitHub hook trigger for GITScm polling",
                "Help for feature: Poll SCM",
                "Help for feature: Quiet period",
                "Help for feature: Trigger builds remotely (e.g., from scripts)"
        );

        TestUtils.createPipeline(this, JOB_NAME, true);

        PipelineConfigurationPage pipelineConfigurationPage = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickConfigure();

        Assert.assertEquals(pipelineConfigurationPage.getNumOfTooltips(), 11);
        Assert.assertEquals(pipelineConfigurationPage.getTooltipsTitlesList(), tooltipsTextsList);
    }

    @Test
    public void testReplayBuildPipeline() {
        TestUtils.createPipeline(this, JOB_NAME, true);

        String lastBuildLink = new HomePage(getDriver())
                .clickBuildByGreenArrow(JOB_NAME)
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickLastBuildLink()
                .clickReplaySideMenu()
                .clickRunButton()
                .getLastBuildLinkText();

        Assert.assertTrue(lastBuildLink.contains("Last build (#2)"));
    }
}