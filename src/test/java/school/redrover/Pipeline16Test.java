package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline16Test  extends BaseTest {

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys("PipelineCreate");
        getDriver().findElement(By.xpath("//li[@class = 'org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//div /button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name ='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class=\"job-index-headline page-headline\"]")).getText(), "Pipeline PipelineCreate");

    }
}