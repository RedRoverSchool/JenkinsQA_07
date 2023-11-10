package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Pipeline2Test extends BaseTest {

    private static final String JOB_NAME = "NewPipeline";
    private static final String JOB_ON_DASHBOARD_XPATH = "//tr[@id ='job_" + JOB_NAME + "']//a[@href = 'job/" + JOB_NAME + "/']";
    private static final String CONFIGURE_ON_SIDE_PANEL_XPATH = "//div[@id = 'tasks']//a[@href = '/job/" + JOB_NAME + "/configure']";
    private static final String CHECKBOX_TEXT = "Do not allow concurrent build";

    private void createAPipeline(String jobName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
    }

    private void goMainPageByBreadcrumb() {
        getDriver().findElement(By.xpath("//div[@id = 'breadcrumbBar']//a[@href = '/']")).click();
        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"), 1));
    }

    private void runHelloWorldBuildInPipeline(String jobName) {
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.textToBe(By.cssSelector("div#side-panel h1"),"Configure"));

        Select select = new Select( getDriver().findElement(By.xpath("//div[@class='samples']/select")));
        select.selectByValue("hello");
        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='ace_scroller']"), "Hello World"));

        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id = 'tasks']//a[contains(@href, '/job/" + jobName + "/build')]"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'table-box']")));
    }

    @Test
    public void testCreate() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(),
                JOB_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDelete() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_ON_DASHBOARD_XPATH))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Delete')]"))).click();

        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"), 1));

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testDescriptionDisplays() {
        final String jobName = "Pipeline1";
        final String description = "Description of the Pipeline ";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

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
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        String permalinksInfo = getDriver().findElement(By.xpath("//ul[@class = 'permalinks-list']")).getText();

        Assert.assertTrue(permalinksInfo.isEmpty());
    }

    @Test
    public void testPermalinksContainBuildInformation() throws InterruptedException {
        final String jobName = "Pipeline2";
        final List<String> buildsInfo = List.of("Last build (#1)", "Last stable build (#1)", "Last successful build (#1)",
                "Last completed build (#1)");

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td//a[@title = 'Schedule a Build for " + jobName + "']")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();

        List<WebElement> permalinks = getDriver().findElements(By.cssSelector(".permalink-item"));

        Assert.assertEquals(permalinks.size(), 4);
        for (int i = 0; i < permalinks.size(); i++) {
            Assert.assertTrue(permalinks.get(i).getText().contains(buildsInfo.get(i)));
        }
    }

    @Test
    public void testStageViewBeforeBuild() {
        final String jobName = "Pipeline3";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + jobName + "/']")).click();

        String stageViewInfo = getDriver().findElement(By.xpath("//div[@id = 'pipeline-box']/div")).getText();

        Assert.assertEquals(stageViewInfo, "No data available. This Pipeline has not yet run.");
    }

    @Test
    public void testStageViewAfterRunningSampleBuild() {
        createAPipeline(JOB_NAME);
        runHelloWorldBuildInPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class = 'table-box']"))).isDisplayed());
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//table[@class = 'jobsTable']//th[@class = 'stage-header-name-0']")).getText(),
                "Hello");
    }

    @Test
    public void testSaveSettingsWhileConfigure() {
        createAPipeline(JOB_NAME);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]"))).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]/../input"))).isSelected());
    }

    @Test
    public void testCreatingPipeline() {
        String pipeline = "ArtusomPipeline";
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='add-item-panel']//input[@id='name']")).sendKeys(pipeline);
        getDriver().findElement((By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"))).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']/span")).getText(), pipeline);
    }

    @Test(dependsOnMethods = "testSaveSettingsWhileConfigure")
    public void testUnsavedSettingsWhileConfigure() {
        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]"))).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        getDriver().findElement(By.xpath(JOB_ON_DASHBOARD_XPATH)).click();
        getDriver().findElement(By.xpath(CONFIGURE_ON_SIDE_PANEL_XPATH)).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(), '" + CHECKBOX_TEXT + "')]/../input"))).isSelected());
    }

    @Test
    public void testTooltipsDescriptionCompliance() {
        final String jobName = "Another_Pipeline";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//tr[@id ='job_" + jobName + "']//a[@href = 'job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//div[@id = 'tasks']//a[@href = '/job/" + jobName + "/configure']")).click();

        List<WebElement> toolTips = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//a[contains(@tooltip, '')]"));
        List<WebElement> checkBoxesWithTooltips = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//label[@class = 'attach-previous ']"));

        Assert.assertEquals(toolTips.size(), 11);
        Assert.assertEquals(toolTips.size(), checkBoxesWithTooltips.size());
        for (int i = 0; i < toolTips.size(); i++) {
            Assert.assertEquals("Help for feature: " + checkBoxesWithTooltips.get(i).getText(), toolTips.get(i).getAttribute("title"));
        }
    }

    @Test
    public void testPermalinksBuildData() throws InterruptedException {
        final String jobName = "Pipeline1";

        createAPipeline(jobName);
        goMainPageByBreadcrumb();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/build?delay=0sec']")).click();

        Thread.sleep(2000);
        getDriver().navigate().refresh();
        List<WebElement> permalinksBuildHistory = getDriver().findElements(By.xpath("//li[@class='permalink-item']"));

        Assert.assertEquals(permalinksBuildHistory.size(), 4);
        Assert.assertTrue(permalinksBuildHistory.get(0).getText().contains("Last build"));
        Assert.assertTrue(permalinksBuildHistory.get(1).getText().contains("Last stable build"));
        Assert.assertTrue(permalinksBuildHistory.get(2).getText().contains("Last successful build"));
        Assert.assertTrue(permalinksBuildHistory.get(3).getText().contains("Last completed build"));
    }
}


