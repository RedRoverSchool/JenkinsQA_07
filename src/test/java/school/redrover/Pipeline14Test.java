package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline14Test extends BaseTest {

    @Test
    public void testCreatePipeline () {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input [@name='name']")).sendKeys("PipelineName");
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//li/a[@class= 'model-link']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/PipelineName/']")).click();

        Assert.assertEquals
                (getDriver().findElement(By.xpath("//h1[@class= 'job-index-headline page-headline']")).getText(),
                        "Pipeline PipelineName");
    }
}
