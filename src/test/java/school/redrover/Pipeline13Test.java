package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline13Test extends BaseTest {

    @Test
    public void testCreateNewPipeline() {
        final String newPipeLineName = "PipeLineProject";

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(newPipeLineName);
        getDriver().findElement(By.xpath("//li[@class = 'org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
        getDriver().findElement(By.xpath("//span[text() = 'PipeLineProject']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[contains(text() ,'Pipeline PipeLineProject')]")).getText(),
                "Pipeline PipeLineProject");
    }
}

