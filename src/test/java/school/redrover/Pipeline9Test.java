package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline9Test extends BaseTest {

    private void createPipeline (String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreatePipeline() {
        final String pipelineName = "Test Pipeline";

        createPipeline(pipelineName);
        getDriver().findElement(By.xpath("//td//a[@href = 'job/Test%20Pipeline/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Pipeline " + pipelineName);
    }

    @Test
    public void testAddDescriptionPipeline() {
        final String pipelineName = "Test Pipeline";
        final String description = "This is Test Pipeline";

        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//td//a[@href = 'job/Test%20Pipeline/']")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//div[@id = 'description']//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@class = 'jenkins-!-margin-bottom-0']//child::div[1]")).getText(), description);
    }

    @Test
    public void testStageViewBeforeBuild() {
        final String pipelineName = "Test Pipeline";
        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//td//a[@href = 'job/Test%20Pipeline/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'pipeline-box']//child::div")).getText(),
                "No data available. This Pipeline has not yet run.");
    }

    @Test
    public void testPermalinksIsEmpty() {
        final String pipelineName = "Test Pipeline";
        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//td//a[@href = 'job/Test%20Pipeline/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//ul[@class = 'permalinks-list']")).getText().isEmpty());
    }
}

