package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Pipeline2Test extends BaseTest {

    private void createAPipeline(String jobName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void goDashboardByBreadcrumb() {
        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();
    }

    @Test
    private void runHelloWorldBuildInPipeline(String jobName) throws InterruptedException {
        createAPipeline(jobName);

        getDriver().findElement(By.xpath("//div[@id ='tasks']//a[@href ='/job/" + jobName + "/configure']")).click();

        WebElement selectSample = getDriver().findElement(By.xpath("//div[@class='samples']/select"));
        Select select = new Select(selectSample);
        select.selectByValue("hello");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//div[@id = 'tasks']//a[contains(@href, '/job/" + jobName + "/build')]")).click();
        Thread.sleep(2000);
    }

    @Test
    public void testCreate() {
        final String jobName = "New_Pipeline";

        createAPipeline(jobName);

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), jobName);
    }

    @Test
    public void testDelete() {
        final String jobName = "MyPipeline";

        createAPipeline(jobName);

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href ='/']")).click();

        getDriver().findElement(By.xpath("//td/a[@href ='job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testDescriptionDisplays() {
        final String jobName = "Pipeline1";
        final String description = "Description of the Pipeline ";

        createAPipeline(jobName);
        goDashboardByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href ='job/" + jobName + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.cssSelector("textarea[name ='description']")).sendKeys(description + jobName);
        getDriver().findElement(By.xpath("//div[@id = 'description']//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@class = 'jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling :: div")).getText(), description + jobName);
    }

    @Test
    public void testPermalinksIsEmpty() {
        final String jobName = "NewPipeline";

        createAPipeline(jobName);
        goDashboardByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        String permalinksInfo = getDriver().findElement(By.xpath("//ul[@class = 'permalinks-list']")).getText();

        Assert.assertTrue(permalinksInfo.isEmpty());
    }

    @Test
    public void testPermalinksContainBuildInformation() throws InterruptedException {
        final String jobName = "Pipeline2";

        createAPipeline(jobName);
        goDashboardByBreadcrumb();

        getDriver().findElement(By.xpath("//td//a[@title = 'Schedule a Build for " + jobName + "']")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        List<WebElement> permalinks = getDriver().findElements(By.cssSelector(".permalink-item"));

        Assert.assertEquals(permalinks.size(), 4);
        Assert.assertTrue(permalinks.get(0).getText().contains("Last build (#1)"));
        Assert.assertTrue(permalinks.get(1).getText().contains("Last stable build (#1)"));
        Assert.assertTrue(permalinks.get(2).getText().contains("Last successful build (#1)"));
        Assert.assertTrue(permalinks.get(3).getText().contains("Last completed build (#1)"));
    }

    @Test
    public void testStageViewBeforeBuild() {
        final String jobName = "Pipeline3";

        createAPipeline(jobName);
        goDashboardByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + jobName + "/']")).click();

        String stageViewInfo = getDriver().findElement(By.xpath("//div[@id = 'pipeline-box']/div")).getText();

        Assert.assertEquals(stageViewInfo, "No data available. This Pipeline has not yet run.");
    }

    @Test
    public void testStageViewAfterRunningSampleBuild() throws InterruptedException {
        final String jobName = "PipelineForBuild";

        runHelloWorldBuildInPipeline(jobName);
        goDashboardByBreadcrumb();

        getDriver().findElement(By.xpath("//tr[@id ='job_" + jobName + "']//a[@href = 'job/" + jobName + "/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class = 'table-box']")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@class = 'jobsTable']//th[@class = 'stage-header-name-0']")).getText(),"Hello");
    }
}
