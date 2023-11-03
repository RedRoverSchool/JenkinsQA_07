package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline10Test extends BaseTest {

    @Test
    public void testCreatePipeline() {
        final String pipelineName = "PipelineName";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + pipelineName + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Pipeline " + pipelineName);
    }
}
