package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Pipeline9Test extends BaseTest {

    private final static String PIPELINE_NAME = "Pipeline1";

    @Test
    public void testCreatePipeline() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + PIPELINE_NAME + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Pipeline " + PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testAddDescriptionPipeline() {
        final String description = "This is Test Pipeline";

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + PIPELINE_NAME + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//div[@id = 'description']//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@class = 'jenkins-!-margin-bottom-0']//child::div[1]")).getText(), description);
    }

    @Test(dependsOnMethods = "testAddDescriptionPipeline")
    public void testStageViewBeforeBuild() {

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + PIPELINE_NAME + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'pipeline-box']//child::div")).getText(),
                "No data available. This Pipeline has not yet run.");
    }

    @Test(dependsOnMethods = "testStageViewBeforeBuild")
    public void testPermalinksIsEmpty() {
        getDriver().findElement(By.xpath("//td//a[@href = 'job/"+ PIPELINE_NAME +"/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//ul[@class = 'permalinks-list']")).getText().isEmpty());
    }

    @Test(dependsOnMethods = "testPermalinksIsEmpty")
    public void testPermalinksContainsInfo() throws InterruptedException {
        final List<String> permalinksInfo = List.of(
                "Last build (#1)",
                "Last stable build (#1)",
                "Last successful build (#1)",
                "Last completed build (#1)"
        );

        getDriver().findElement(By.xpath("//td//a[@title = 'Schedule a Build for " + PIPELINE_NAME + "']")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        List<WebElement> permalinks = getDriver().findElements(By.className("permalink-item"));

        Assert.assertEquals(permalinks.size(), 4);
        for (int i = 0; i < permalinks.size(); i++) {
            Assert.assertTrue(permalinks.get(i).getText().contains(permalinksInfo.get(i)));
        }
    }
}

