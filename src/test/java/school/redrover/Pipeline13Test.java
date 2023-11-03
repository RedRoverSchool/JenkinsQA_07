package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline13Test extends BaseTest {
    final String pipeLineName = "PipeLineProject";

    private void createNewPipeline() {
        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(pipeLineName);
        getDriver().findElement(By.xpath("//li[@class = 'org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

    }

    @Test
    public void testCreatPipeline() {
        createNewPipeline();

        getDriver().findElement(By.xpath("//span[text() = 'PipeLineProject']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[contains(text() ,'Pipeline PipeLineProject')]")).getText(),
                "Pipeline PipeLineProject");
    }

    @Test
    public void testRenamePipeline() {
        final String pipeLineNewName = "PipeLineNewProject";

        createNewPipeline();

        getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/PipeLineProject/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).sendKeys(pipeLineNewName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[text() = '" + pipeLineNewName + "']")).getText(),
                pipeLineNewName);
    }
}



