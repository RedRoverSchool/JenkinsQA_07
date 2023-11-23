package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NewItemPage;
import school.redrover.model.PipelineConfigurationPage;
import school.redrover.model.PipelineDetailsPage;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineTest extends BaseTest {
    private static final String JOB_NAME = "NewPipeline";
    private static final String JOB_ON_DASHBOARD_XPATH = "//tr[@id ='job_" + JOB_NAME + "']//a[@href = 'job/" + JOB_NAME + "/']";
    private static final String CONFIGURE_ON_SIDE_PANEL_XPATH = "//div[@id = 'tasks']//a[@href = '/job/" + JOB_NAME + "/configure']";
    private static final String CHECKBOX_TEXT = "Do not allow concurrent build";
    private final String PIPELINE_NAME = "Pipeline test";

    private void createPipeline(String pipelineName, boolean returnToDashboard) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        if (returnToDashboard) {
            goToDashboard();
        }
    }

    private void createPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickSaveConfiguration() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'build')]")).click();
    }

    private void clickConfigure() {
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'configure')]"))
                .click();
    }

    private void goToDashboard() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickProjectOnDashboard(String projectName) {
        getDriver().findElement(By
                .xpath(String.format("//a[@href='job/%s/']", projectName.replace(" ", "%20")))).click();
    }

    @Test
    public void testCreatePipeline() {
        final String validPipelineName = "NewPipeline";
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id ='name']")).sendKeys(validPipelineName);
        getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + validPipelineName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1")).getText(),
                "Pipeline " + validPipelineName);

    }

    @Test
    public void testCreateWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver()).
                clickNewItem().
                selectPipelineProject();

        Assert.assertEquals(newItemPage.getRequiredNameErrorMessage(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testPipelineEmptyNameHandling() {

        final String errorTextExpected = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id ='j-add-item-type-standalone-projects']//span[contains(text(), 'Pipeline')]")).click();

        WebElement error = getDriver().findElement(By.id("itemname-required"));
        String errorTextActual = error.getText();
        String errorTextColor = error.getCssValue("color");

        Assert.assertEquals(errorTextActual, errorTextExpected);
        Assert.assertEquals(errorTextColor, "rgba(255, 0, 0, 1)");
        Assert.assertEquals(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "true");
    }

    @Test
    public void testCreateWithDublicateName() {
        final String pipelineName = "PipelineName";
        createPipeline(pipelineName, true);

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"itemname-invalid\"]")).getText(),
                "» A job already exists with the name ‘" + pipelineName + "’");


        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#main-panel")).getText().contains("A job already exists with the name ‘" + pipelineName + "’"));

    }

    @Ignore
    @Test
    public void testPipelineRename() {
        final String pipelineName = "PipelineName";
        final String newPipelineName = "NewPipelineName";

        createPipeline(pipelineName, true);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + pipelineName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();

        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL + "a");
        getDriver().findElement(By.name("newName")).sendKeys(newPipelineName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String confirmingName = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(confirmingName, "Pipeline " + newPipelineName);
    }

    @Test
    public void testVerifyBuildIconOnDashboard() {

        createPipeline(PIPELINE_NAME, false);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'build')]")).click();

        final String[] buildIconTitle = getDriver().findElement(By.xpath("//div[@class='build-icon']/a"))
                .getAttribute("title").split(" ");
        final String buildStatus = buildIconTitle[0];

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath(
                String.format("//td/a/span[text()='%s']/../../../td/div/span/span/*[name()='svg' and @tooltip='%s']",
                        PIPELINE_NAME, buildStatus))).isDisplayed());
    }

    @Test
    public void testPipelineNoNameError() {
        final String pipelineName = "My_Pipline_project1";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();
        getDriver().findElement(By.xpath("//span[normalize-space()='" + pipelineName + "']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/My_Pipline_project1/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//button[normalize-space()='Rename']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Error");
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//p")).getText(),
                "No name is specified");
    }


    @Test
    public void testCreatePipelineProject() {
        final String PipelineName = "MyPipeline";

        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PipelineName)
                .selectPipelineProject()
                .clickOk(new PipelineConfigurationPage(getDriver()))
                .goHomePage()
                .getJobList();
        Assert.assertTrue(jobList.contains(PipelineName));
    }


    @Ignore
    @Test(dependsOnMethods = "testCreatePipeline")
    public void testOpenLogsFromStageView() {

        clickProjectOnDashboard(PIPELINE_NAME);
        clickConfigure();

        Select select = new Select(getDriver().findElement(By.xpath("//div[@class='samples']/select")));
        select.selectByValue("hello");
        clickSaveConfiguration();

        clickBuildNow();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='badge']/a[text()='#1']")));
        WebElement buildRecordInStageView = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@class='tobsTable-body']//div[@class='duration']")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(buildRecordInStageView).perform();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='btn btn-small cbwf-widget cbwf-controller-applied stage-logs']"))).click();

        String consoleLog = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre[@class='console-output']"))).getText();
        Assert.assertEquals(consoleLog, "Hello World");
    }

    @Test
    public void testBuildRunTriggeredByAnotherProject() {

        final String upstreamPipelineName = "Upstream Pipe";

        createPipeline(PIPELINE_NAME, true);
        createPipeline(upstreamPipelineName, false);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'configure')]"))
                .click();
        WebElement buildAfterOtherProjectsCheckbox = getDriver()
                .findElement(By.xpath("//label[text()='Build after other projects are built']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsCheckbox);
        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys(PIPELINE_NAME);
        WebElement alwaysTriggerRadio = getDriver().findElement(
                By.xpath("//label[text()='Always trigger, even if the build is aborted']"));
        js.executeScript("arguments[0].click();", alwaysTriggerRadio);
        clickSaveConfiguration();

        goToDashboard();
        getDriver().findElement(
                        By.xpath(String.format("//span[text()='%s']/../../..//a[contains(@href,'build?')]", PIPELINE_NAME)))
                .click();

        Assert.assertTrue(getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//td[@class='pane pane-grow']")))
                .getText()
                .contains(upstreamPipelineName));
    }

    @Test
    public void testStagesAreDisplayedInStageView() {
        final List<String> stageNames = List.of(new String[]{"test", "build", "deploy"});
        final String pipelineScript = "stage('test') {}\nstage('build') {}\nstage('deploy') {}";

        createPipeline(PIPELINE_NAME, false);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true)", getDriver().findElement(By.xpath("//div[@class='ace_line']")));
        getDriver().findElement(By.className("ace_text-input")).sendKeys(pipelineScript);
        clickSaveConfiguration();
        clickBuildNow();

        List<String> actualStageNames = getDriver()
                .findElements(By.xpath("//th[contains(@class, 'stage-header-name-')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(actualStageNames, stageNames);
    }

    @Test
    public void testBuildWithStringParameter() {
        final String parameterName = "textParam";
        final String parameterValue = "some text";
        final String scriptText = String.format("stage('test') {\necho \"${%s}\"\n", parameterName);

        createPipeline(PIPELINE_NAME, false);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click()",
                getDriver().findElement(By.xpath("//label[text()='This project is parameterized']")));
        WebElement addParameterBtn = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.id("yui-gen1-button")));
        js.executeScript("arguments[0].scrollIntoView(true)",
                getDriver().findElement(By.xpath("//label[text()='This project is parameterized']")));
        addParameterBtn.click();
        getDriver().findElement(By.id("yui-gen10")).click();

        getDriver().findElement(By.name("parameter.name")).sendKeys(parameterName);
        getDriver().findElement(By.className("ace_text-input")).sendKeys(scriptText);
        clickSaveConfiguration();

        clickBuildNow();
        getDriver().findElement(By.name("value")).sendKeys(parameterValue);
        getDriver().findElement(
                By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-!-build-color']")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='badge']/a[text()='#1']"))).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/console')]")).click();

        Assert.assertTrue(getDriver().findElement(By.className("console-output")).getText().contains(parameterValue));
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
    public void testPermalinksContainBuildInformation() throws InterruptedException {
        final String jobName = "Pipeline2";
        final List<String> buildsInfo = List.of("Last build (#1)", "Last stable build (#1)", "Last successful build (#1)",
                "Last completed build (#1)");

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td//a[@title = 'Schedule a Build for " + jobName + "']")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        List<WebElement> permalinks = getDriver().findElements(By.cssSelector(".permalink-item"));

        Assert.assertEquals(permalinks.size(), 4);
        for (int i = 0; i < permalinks.size(); i++) {
            Assert.assertTrue(permalinks.get(i).getText().contains(buildsInfo.get(i)));
        }
    }

    @Test
    public void testStageViewBeforeBuild() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();

        String stageViewInfo = getDriver().findElement(By.cssSelector("div#pipeline-box > div")).getText();

        Assert.assertEquals(stageViewInfo, "No data available. This Pipeline has not yet run.");
    }

    @Test
    public void testPermalinksContainsInfo() throws InterruptedException {
        final String pipelineName = "Pipeline_Test";
        final List<String> permalinksInfo = List.of(
                "Last build (#1)",
                "Last stable build (#1)",
                "Last successful build (#1)",
                "Last completed build (#1)"
        );

        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//td//a[@title = 'Schedule a Build for " + pipelineName + "']")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + pipelineName + "/']")).click();

        List<WebElement> permalinks = getDriver().findElements(By.className("permalink-item"));

        Assert.assertEquals(permalinks.size(), 4);
        for (int i = 0; i < permalinks.size(); i++) {
            Assert.assertTrue(permalinks.get(i).getText().contains(permalinksInfo.get(i)));
        }
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
