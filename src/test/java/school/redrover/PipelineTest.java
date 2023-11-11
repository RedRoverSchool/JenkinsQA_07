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
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineTest extends BaseTest {

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

    @Test
    public void testCreateWithValidName() {
        final String pipelineName = "PipelineName";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + pipelineName + "/']")).click();

        Assert.assertEquals(getDriver().getTitle(), pipelineName + " [Jenkins]");
    }

    @Test
    public void testCreateWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"itemname-required\"]")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertTrue(
                getDriver().findElement(By.cssSelector(".disabled")).isDisplayed());
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

    @Test
    public void testRenamePipeline() {
        String pipelineNameForCreate = "Hello";
        String pipelineNameForRename = "HaveAGoodDay";

        createPipeline(pipelineNameForCreate, true);

        getDriver().findElement(By.xpath("//*[@id='job_" + pipelineNameForCreate + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + pipelineNameForCreate + "/confirm-rename']")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input.validated")).clear();
        getDriver().findElement(By.cssSelector(".jenkins-input.validated")).sendKeys(pipelineNameForRename);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id= 'jenkins-home-link']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//td/a[@href='job/" + pipelineNameForRename + "/']")).isDisplayed());
    }

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
    public void testCreatePipeline() {
        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();
        WebElement itemName = getDriver().findElement(By.id("name"));
        itemName.sendKeys("MyPipeline");
        WebElement pipeLine = getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"));
        pipeLine.click();
        WebElement button = getDriver().findElement(By.id("ok-button"));
        button.click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/MyPipeline/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Pipeline MyPipeline");
    }

    @Test
    public void testCreatePipelineValidName() {
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
    public void testCreatePipelineWithValidName() {
        final String PipelineName = "PipelineProjectName";
        getDriver().findElement(By.cssSelector("a[href*='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input[class ='jenkins-input']")).sendKeys(PipelineName);
        getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("button[type = 'submit']")).click();
        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("li[class = 'jenkins-breadcrumbs__list-item']")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("a[href = 'job/PipelineProjectName/']")).getText(), PipelineName);
    }

    @Test
    public void testCreatePipelineProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        WebElement nameField = getDriver().findElement(By.xpath("//input[@name='name']"));
        nameField.clear();
        nameField.sendKeys("MyPipeline");

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='job/MyPipeline/']")).isDisplayed());

    }

    @Test
    public void testOpenLogsFromStageView() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        Select select = new Select(getDriver().findElement(By.xpath("//div[@class='samples']/select")));
        select.selectByValue("hello");
        clickSaveConfiguration();

        clickBuildNow();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='badge']/a[text()='#1']")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(
                By.xpath("//tbody[@class='tobsTable-body']//div[@class='duration']"))).perform();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='btn btn-small cbwf-widget cbwf-controller-applied stage-logs']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//pre[@class='console-output']")).getText(),
                "Hello World");
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

        createPipeline(PIPELINE_NAME, false);
        List<String> parameterChoices = Arrays.asList("one", "two");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click()",
                getDriver().findElement(By.xpath("//label[text()='This project is parameterized']")));
        js.executeScript("arguments[0].scrollIntoView(true)",
                getDriver().findElement(By.xpath("//label[text()='This project is parameterized']")));
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDriver().findElement(By.id("yui-gen4")).click();
        getDriver().findElement(By.name("parameter.name")).sendKeys("parameterName");
        for (int i = 0; i < parameterChoices.size(); i++) {
            if (i != parameterChoices.size() - 1) {
                getDriver().findElement(By.name("parameter.choices")).sendKeys(parameterChoices.get(i) + "\n");
            } else {
                getDriver().findElement(By.name("parameter.choices")).sendKeys(parameterChoices.get(i));
            }
        }
        clickSaveConfiguration();

        clickBuildNow();

        List<String> buildParameters = getDriver().findElements(By.xpath("//select[@name='value']/option"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(buildParameters, parameterChoices);
    }
}
