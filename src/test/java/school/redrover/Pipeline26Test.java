package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline26Test extends BaseTest {

    private final String PIPELINE_NAME = "PipelineName";

    public void createPipeline() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    public void goBackToDashboard() {
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
    }

    @Test
    public void testCreate() {
        createPipeline();
        goBackToDashboard();

        String foundName = getDriver().findElement(By.xpath("//*[@href='job/" + PIPELINE_NAME + "/']")).getText();
        Assert.assertEquals(foundName, PIPELINE_NAME);
    }

    @Test
    public void testPipelineRename() {
        final String newPipelineName = "NewPipelineName";

        createPipeline();
        goBackToDashboard();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();

        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL + "a");
        getDriver().findElement(By.name("newName")).sendKeys(newPipelineName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String confirmingName = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(confirmingName, "Pipeline " + newPipelineName);
    }
}
