package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.HomePage;
import school.redrover.model.MultibranchPipelineConfigurationPage;
import school.redrover.model.MultibranchPipelineDetailsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";

    private static final String MULTIBRANCH_PIPELINE_NON_EXISTING_NAME = "MultibranchPipelineNonExistingName";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final List<String> requiredNamesOfTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
            "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")))).click();
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//input[@class='jenkins-input']")))).sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='ok-button']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        if (goToHomePage) {
            getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id(HOME_PAGE)))).click();
        }
    }

    private List<String> getTextOfWebElements(List<WebElement> elements) {
        List<String> textOfWebElements = new ArrayList<>();

        for (WebElement element : elements) {
            textOfWebElements.add(element.getText());
        }
        return textOfWebElements;
    }

    private void createMultibranchPipelineWithNewItemAndClickDashboard(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

    }

    private void createMultibranchPipelineWithCreateAJob() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void goMultibranchPipelinePage(String pipelineName) {
        getDriver().findElement(By.xpath("//span[normalize-space()='" + pipelineName + "']")).click();
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();
    }

    private void returnToJenkinsHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
    }

    private void createMultibranchPipeline(String name) {
        returnToJenkinsHomePage();

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testMultibranchPipelineCreationWithCreateAJob() {

        String multibranchBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineOption()
                .clickOk()
                .getJobNameFromBreadcrumb();

        Assert.assertEquals(multibranchBreadcrumbName, MULTIBRANCH_PIPELINE_NAME,
                multibranchBreadcrumbName + " name doesn't match " + MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testRenameMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipelineWithNewItemAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

        String expectedResultName = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineConfigurationPage(getDriver()))
                .confirmRename(MULTIBRANCH_PIPELINE_NAME)
                .clearField()
                .inputName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .buttonSubmit()
                .getJobName();

        String nameH1 = new MultibranchPipelineConfigurationPage(getDriver()).headerName();

        Assert.assertTrue(nameH1.contains(MULTIBRANCH_PIPELINE_NEW_NAME));

        Assert.assertEquals(expectedResultName, MULTIBRANCH_PIPELINE_NEW_NAME,
                expectedResultName + MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test
    public void testErrorMessageRenameWithDotAtTheEnd() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String dotErrorMessage = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .addCharsToExistingName(".")
                .clickSubmitError()
                .getErrorMessage();

        Assert.assertEquals(dotErrorMessage, "A name cannot end with ‘.’");
    }

    @Test
    public void testErrorMessageRenameWithLessThanSign() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String lessThanSignErrorMessage = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .addCharsToExistingName(Keys.SHIFT + ",")
                .clickSubmitError()
                .getErrorMessage();

        Assert.assertEquals(lessThanSignErrorMessage, "‘&lt;’ is an unsafe character");
    }


    @Test
    public void testErrorMessageRenameWithTwoUnsafeChars() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String twoUnsafeCharsErrorMessage = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .addCharsToExistingName("#" + Keys.SHIFT + ".")
                .clickSubmitError()
                .getErrorMessage();

        Assert.assertEquals(twoUnsafeCharsErrorMessage, "‘#’ is an unsafe character");
    }

    @Test
    public void testAllTaskTextInSidebar() {
        final List<String> expectedTasksText = List.of(
                "Status",
                "Configure",
                "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events",
                "Delete Multibranch Pipeline",
                "People",
                "Build History",
                "Rename",
                "Pipeline Syntax",
                "Credentials");

        createMultibranchPipelineWithNewItemAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

        List<String> actualTasksText = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .getTasksText();

        Assert.assertEquals(actualTasksText, expectedTasksText);
    }

    @Test
    public void testDeleteMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipelineWithNewItemAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        createMultibranchPipelineWithNewItemAndClickDashboard(MULTIBRANCH_PIPELINE_NEW_NAME);
        String expectedResult = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineConfigurationPage(getDriver()))
                .clickButtonDelete()
                .clickRedButtonYes()
                .multibranchPipelineName();

        Assert.assertNotEquals(
                expectedResult, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultiConfigurationPipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        WebElement nameField = getDriver().findElement(By.xpath("//input[@name='name']"));
        nameField.clear();
        nameField.sendKeys("MyMultiConfigurationPipeline");

        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline'] ")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='job/MyMultiConfigurationPipeline/']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testRenameMultibranchDropdownDashboard() {
        HomePage homePage = new HomePage(getDriver())
                .clickJobName(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameDropdownMenu(MULTIBRANCH_PIPELINE_NAME)
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .clickSubmit()
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = {"testMultibranchPipelineCreationWithCreateAJob", "testRenameMultibranchDropdownDashboard"})
    public void testRenameMultibranchDropdownBreadcrumbs() {
        getDriver().findElement(By.xpath("//td[3]/a/span")).click();

        WebElement breadcrumbName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]/a"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(breadcrumbName).perform();

        WebElement breadcrumbArrow = getDriver().findElement(By.xpath("//li[3]/a/button"));
        actions.sendKeys(breadcrumbArrow, Keys.ENTER).perform();

        getDriver().findElement(By.xpath("//a[@href='/job/"
                + MULTIBRANCH_PIPELINE_NEW_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[3]/a/span")).getText(), MULTIBRANCH_PIPELINE_NAME,
                MULTIBRANCH_PIPELINE_NEW_NAME + "is not equal" + MULTIBRANCH_PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationPipeline")
    public void testEnableByDefault() {
        getDriver().findElement(By.xpath("//a[@href='job/MyMultiConfigurationPipeline/']")).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//*[@id='toggle-switch-enable-disable-project']/label")).getText(), "Enabled");
    }

    @Test
    public void testSeeAAlertAfterDisableMultibranchPipeline() {
        createMultibranchPipelineWithNewItemAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("a[href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.cssSelector("button[formNoValidate]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("form[method='post']")).getText().
                        contains("This Multibranch Pipeline is currently disabled"),
                "Incorrect or missing text");
    }

    @Test
    public void testMultibranchNameDisplayBreadcrumbTrail() {
        createMultibranchPipelineWithCreateAJob();
        String pipelineNameExpected = getDriver().findElement(By.xpath("//a[contains(text(),'"
                + MULTIBRANCH_PIPELINE_NAME + "')]")).getText();
        Assert.assertEquals(pipelineNameExpected, MULTIBRANCH_PIPELINE_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testMultibranchCreationFromExisting() {

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .populateFieldCopyFrom(MULTIBRANCH_PIPELINE_NAME)
                .clickOk()
                .buttonSubmit()
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Test (dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testMultibranchCreationFromNonExisting() {

        String error = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .populateFieldCopyFrom(MULTIBRANCH_PIPELINE_NON_EXISTING_NAME)
                .clickOk()
                .error();

        Assert.assertEquals(error, "Error");
    }

    @Ignore
    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testFindByQuickSearch() {
        MultibranchPipelineDetailsPage multibranchPipelineDetailsPage = new HomePage(getDriver())
                .searchBox(new MultibranchPipelineDetailsPage(getDriver()), MULTIBRANCH_PIPELINE_NAME);

        Assert.assertEquals(multibranchPipelineDetailsPage.getTitle(), MULTIBRANCH_PIPELINE_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testFindByQuickSearch")
    public void testErrorForUnsafeChar() {
        String error_message = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME + "!")
                .clickBlank()
                .getErrorMessage();

        Assert.assertEquals(error_message, "‘!’ is an unsafe character");
    }

    @Test
    public void testRenameUsingSidebar() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String name = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .clickSubmit()
                .getTitle();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test
    public void testRenameResultInBreadcrumb() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        List<String> breadcrumb = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .clickSubmit()
                .getBreadcrumbChain();

        Assert.assertTrue(breadcrumb.contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Test
    public void testRenameResultOnPageHeading() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String name = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .clickSubmit()
                .getTitle();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test
    public void testRenameResultOnDashboard() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        List<String> jobs = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .clickSubmit()
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobs.contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Test
    public void testSidebarMenuConsistingOfTenTasks() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        int numberOfTasksFromLeftSidebarMenu = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .getNameOfTasksFromSidebarMenu().size();

        Assert.assertEquals(numberOfTasksFromLeftSidebarMenu, requiredNamesOfTasks.size());
    }

    @Test
    public void testVisibilityOfTasksOnSidebarMenu() {
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        List<String> namesOfTasks = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .getNameOfTasksFromSidebarMenu();

        Assert.assertEquals(namesOfTasks, requiredNamesOfTasks);
    }

    @Test
    public void testVisibilityOfAdditionalTaskOfSidebarMenuIfFolderIsCreated() {
        createProject("Folder", "Nested Folder", true);
        createProject("Multibranch Pipeline", MULTIBRANCH_PIPELINE_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));
        namesOfTasks.removeAll(requiredNamesOfTasks);

        Assert.assertEquals(namesOfTasks.toString(), "[Move]");
    }

    @Test
    public void testVisibilityOfAdditionalTaskOfSidebarMenuIfProjectInsideFolder() {
        final String folderName = "Wrapper Folder";

        createProject("Folder", folderName, false);
        createProject("Multibranch Pipeline", MULTIBRANCH_PIPELINE_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));

        Assert.assertTrue(namesOfTasks.contains("Move"), "Move is not the additional task of sidebar menu on the left");
    }

    @Test
    public void testDisableMultibranchPipelineWithHomePage() {
        String name = "Test_Folder";
        String expectedResult = "Enable";

        createMultibranchPipeline(name);
        returnToJenkinsHomePage();

        getDriver().findElement(By.xpath("//tr[@id='job_Test_Folder']//a[@href='job/" + name + "/']")).click();
        getDriver().findElement(By.xpath("//form[@id='disable-project']/button")).click();

        WebElement enableButton = getDriver().findElement(By.xpath("//form[@id='enable-project']/button"));
        String actualResult = enableButton.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testDisableMultibranchPipeline() {
        createMultibranchPipeline("Test_Folder");
        String expectedResult = "Disabled";

        getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']/label")).click();

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        WebElement elementPage = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(
                "//span[@id='toggle-switch-enable-disable-project']/label/span[text()='Disabled']"))));
        String nameToggle = elementPage.getText();

        Assert.assertEquals(nameToggle, expectedResult);
    }

    @Test(dependsOnMethods = "testDisableMultibranchPipelineWithHomePage")
    public void testEnableFromStatusPage() {
        getDriver().findElement(By.xpath("//*[@id=\"job_Test_Folder\"]/td[3]/a")).click();

        getDriver().findElement(By.xpath("//form[@id='enable-project']/button")).click();

        Assert.assertEquals(getDriver().findElement(By.name(
                "Submit")).getText(), "Disable Multibranch Pipeline");
    }
}
