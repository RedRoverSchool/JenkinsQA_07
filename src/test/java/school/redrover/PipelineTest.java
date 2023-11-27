package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
    private static final String JOB_ON_DASHBOARD_XPATH = "//tr[@id ='job_" + JOB_NAME + "']//a[@href = 'job/" + JOB_NAME + "/']";
    private static final String CONFIGURE_ON_SIDE_PANEL_XPATH = "//div[@id = 'tasks']//a[@href = '/job/" + JOB_NAME + "/configure']";
    private static final String CHECKBOX_TEXT = "Do not allow concurrent build";
    private final String PIPELINE_NAME = "Name of the pipe";

    @Test
    public void testCreatePipeline() {
        boolean pipeLineCreated = new HomePage(getDriver())
                .clickNewItem()
                .createPipelinePage(PIPELINE_NAME)
                .goHomePage()
                .getJobList()
                .contains(PIPELINE_NAME);

        Assert.assertTrue(pipeLineCreated);
    }

    @Test
    public void testCreateWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectPipelineProject();

        Assert.assertEquals(newItemPage.getRequiredNameErrorMessage(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testPipelineEmptyNameHandling() {
        final String errorTextExpected = "» This field cannot be empty, please enter a valid name";

        NewItemPage newPipeline = new HomePage(getDriver())
                .clickNewItem()
                .selectPipelineProject();

        WebElement error = getDriver().findElement(By.id("itemname-required"));
        String errorTextActual = error.getText();
        String errorTextColor = error.getCssValue("color");

        Assert.assertEquals(errorTextActual, errorTextExpected);
        Assert.assertEquals(errorTextColor, "rgba(255, 0, 0, 1)");
        Assert.assertEquals(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "true");
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
        TestUtils.createPipeline(this, JOB_NAME, false);

        String currentName = new PipelineDetailsPage(getDriver())
                .clickRenameInSideMenu()
                .enterNewName(PIPELINE_NAME)
                .clickRenameButton(new PipelineDetailsPage(getDriver()))
                .goHomePage()
                .getJobDisplayName();

        Assert.assertEquals(currentName, PIPELINE_NAME);
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
                .createPipelinePage(JOB_NAME)
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
                .typeItemName(PIPELINE_NAME)
                .selectPipelineProject()
                .clickOk(new PipelineConfigurationPage(getDriver()))
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobList.contains(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testOpenLogsFromStageView() {
        String stageLogsText = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineDetailsPage(getDriver()))
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

        TestUtils.createPipeline(this, PIPELINE_NAME, true);
        TestUtils.createPipeline(this, upstreamPipelineName, false);

        boolean isJobInBuildQueue = new PipelineDetailsPage(getDriver())
                .clickConfigure()
                .setBuildAfterOtherProjectsCheckbox()
                .setProjectsToWatch(PIPELINE_NAME)
                .clickAlwaysTriggerRadio()
                .clickSaveButton()
                .goHomePage()
                .clickBuildByGreenArrow(PIPELINE_NAME)
                .isJobInBuildQueue(upstreamPipelineName);

        Assert.assertTrue(isJobInBuildQueue);
    }

    @Test
    public void testStagesAreDisplayedInStageView() {
        final List<String> stageNames = List.of(new String[]{"test", "build", "deploy"});
        final String pipelineScript = "stage('test') {}\nstage('build') {}\nstage('deploy') {}";

        TestUtils.createPipeline(this, PIPELINE_NAME, false);

        List<String> actualStageNames = new PipelineDetailsPage(getDriver())
                .clickConfigure()
                .setPipelineScript(pipelineScript)
                .clickSaveButton()
                .clickBuildNow()
                .getStagesNames();

        Assert.assertEquals(actualStageNames, stageNames);
    }

    @Test
    public void testBuildWithStringParameter() {
        final String parameterName = "textParam";
        final String parameterValue = "some text";
        final String pipelineScript = String.format("stage('test') {\necho \"${%s}\"\n", parameterName);

        TestUtils.createPipeline(this, PIPELINE_NAME, false);

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
                .typeItemName(PIPELINE_NAME)
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

    private void createAPipeline(String jobName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
    }

    private void goMainPageByBreadcrumb() {
        getDriver().findElement(By.xpath("//div[@id = 'breadcrumbBar']//a[@href = '/']")).click();
        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"), 1));
    }

    private void runHelloWorldBuildInPipeline(String jobName) {
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.textToBe(By.cssSelector("div#side-panel h1"), "Configure"));

        Select select = new Select(getDriver().findElement(By.xpath("//div[@class='samples']/select")));
        select.selectByValue("hello");
        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='ace_scroller']"), "Hello World"));

        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id = 'tasks']//a[contains(@href, '/job/" + jobName + "/build')]"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'table-box']")));
    }

    @Test(dependsOnMethods = "testDescriptionDisplays")
    public void testDelete() {
        getDriver().findElement((By.xpath(JOB_ON_DASHBOARD_XPATH))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Delete')]"))).click();

        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"), 1));

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testPipelineDeleteProject() {

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("newPipelineName");
        getDriver().findElement(By.xpath("//span[contains(text(), 'Pipeline')]")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'newPipelineName')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Pipeline')]")).click();

        getDriver().switchTo().alert().accept();

        String actualText = getDriver().findElement(By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText();

        Assert.assertEquals(
                actualText,
                "Welcome to Jenkins!",
                "Pipeline not deleted");
    }

    @Test
    public void testCreate() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        Assert.assertTrue(getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).getText(), JOB_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDescriptionDisplays() {
        final String description = "Description of the Pipeline";

        String actualDescription = new HomePage(getDriver())
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .clickAddDescription()
                .inputDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testPermalinksIsEmpty() {
        final String jobName = "NewPipeline";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        String permalinksInfo = getDriver().findElement(By.xpath("//ul[@class = 'permalinks-list']")).getText();

        Assert.assertTrue(permalinksInfo.isEmpty());
    }

    @Test
    public void testPermalinksContainBuildInformation() {

        final List<String> expectedPermalinksList = List.of(
                "Last build (#1)",
                "Last stable build (#1)",
                "Last successful build (#1)",
                "Last completed build (#1)"
        );

        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        List<String> actualPermalinksList = new HomePage(getDriver())
                .clickBuildByGreenArrow(JOB_NAME)
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .getPermalinksList();

        Assert.assertEquals(actualPermalinksList.size(), 4);
        Assert.assertEquals(actualPermalinksList, expectedPermalinksList);
    }

    @Test
    public void testStageViewBeforeBuild() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();

        String stageViewInfo = getDriver().findElement(By.cssSelector("div#pipeline-box > div")).getText();

        Assert.assertEquals(stageViewInfo, "No data available. This Pipeline has not yet run.");
    }

    @Test(dependsOnMethods = "testStageViewBeforeBuild")
    public void testStageViewAfterRunningSampleBuild() {
        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        runHelloWorldBuildInPipeline(JOB_NAME);

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class = 'table-box']"))).isDisplayed());
        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//table[@class = 'jobsTable']//th[@class = 'stage-header-name-0']")).getText(),
                "Hello");
    }

    @Test
    public void testSaveSettingsWhileConfigure() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]"))).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]/../input"))).isSelected());
    }

    @Test(dependsOnMethods = "testSaveSettingsWhileConfigure")
    public void testUnsavedSettingsWhileConfigure() {
        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]"))).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]/../input"))).isSelected());
    }

    @Test
    public void testTooltipsDescriptionCompliance() {
        final String jobName = "Another_Pipeline";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//tr[@id ='job_" + jobName + "']//a[@href = 'job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//div[@id = 'tasks']//a[@href = '/job/" + jobName + "/configure']")).click();

        List<WebElement> toolTips = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//a[contains(@tooltip, '')]"));
        List<WebElement> checkBoxesWithTooltips = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//label[@class = 'attach-previous ']"));

        Assert.assertEquals(toolTips.size(), 11);
        Assert.assertEquals(toolTips.size(), checkBoxesWithTooltips.size());
        for (int i = 0; i < toolTips.size(); i++) {
            Assert.assertEquals("Help for feature: " + checkBoxesWithTooltips.get(i).getText(), toolTips.get(i).getAttribute("title"));
        }
    }

    @Test
    public void testPermalinksBuildData() {
        final String jobName = "Pipeline1";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/build?delay=0sec']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-warning']")));

        getDriver().navigate().refresh();
        List<WebElement> permalinksBuildHistory = getWait5().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li[@class='permalink-item']")));

        Assert.assertEquals(permalinksBuildHistory.size(), 4);
        Assert.assertTrue(permalinksBuildHistory.get(0).getText().contains("Last build"));
        Assert.assertTrue(permalinksBuildHistory.get(1).getText().contains("Last stable build"));
        Assert.assertTrue(permalinksBuildHistory.get(2).getText().contains("Last successful build"));
        Assert.assertTrue(permalinksBuildHistory.get(3).getText().contains("Last completed build"));
    }
}