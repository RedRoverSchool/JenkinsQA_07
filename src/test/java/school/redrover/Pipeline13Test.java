package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline13Test extends BaseTest {
    private  final static String PIPE_LINE_NAME = "PipeLineProject";
    private  final static String PIPE_LINE_NEW_NAME = "PipeLineNewProject";

    private void createPipeline() {
        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPE_LINE_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name ("Submit"))).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
    }

    @Test
    public void testCreatePipeline() {
        createPipeline();

        getDriver().findElement(By.xpath("//span[text() = \"PipeLineProject\"]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[contains(text() ,'Pipeline PipeLineProject')]")).getText(),
                "Pipeline PipeLineProject");
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testRenamePipeline() {
        getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = '/job/PipeLineProject/confirm-rename']"))).click();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).sendKeys(PIPE_LINE_NEW_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Dashboard')]"))).click();

       Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[text() = '" + PIPE_LINE_NEW_NAME + "']")).getText(),
               PIPE_LINE_NEW_NAME);
    }
}



