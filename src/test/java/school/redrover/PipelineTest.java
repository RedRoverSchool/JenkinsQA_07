package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

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


}
