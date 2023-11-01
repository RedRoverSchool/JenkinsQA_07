package school.redrover;

import org.openqa.selenium.By;
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
}
