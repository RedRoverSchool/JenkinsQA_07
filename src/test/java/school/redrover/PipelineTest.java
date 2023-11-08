package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineTest extends BaseTest {

    private final String PIPELINE_NAME = "Pipeline test";

    private void createPipeline(String pipelineName, boolean returnToDashboard) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.name("Submit")).click();

        if (returnToDashboard) {
            getDriver().findElement(By.id("jenkins-name-icon")).click();
        }
    }

    private void saveConfiguration() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'build')]")).click();
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
        saveConfiguration();

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
        saveConfiguration();

        goToDashboard();
        getDriver().findElement(
                        By.xpath(String.format("//span[text()='%s']/../../..//a[contains(@href,'build?')]", PIPELINE_NAME)))
                .click();
        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[@class='pane pane-grow']/a")).getText(),
                upstreamPipelineName);
    }
}
