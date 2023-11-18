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
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";
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

    private void createMultibranchPipelineAndClickDashboard(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
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
                .getJobName();

        Assert.assertEquals(multibranchBreadcrumbName, MULTIBRANCH_PIPELINE_NAME,
                multibranchBreadcrumbName + " name doesn't match " + MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testRenameMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + MULTIBRANCH_PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//h1")).getText().contains(MULTIBRANCH_PIPELINE_NEW_NAME));

        String breadcrumbName = getDriver().findElement(
                By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).getText();
        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NEW_NAME,
                breadcrumbName + MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testErrorMessageRenameWithDotAtTheEnd() {

        final String ERROR_MESSAGE = "A name cannot end with ‘.’";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys(".");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testErrorMessageRenameWithLessThanSign() {

        final String ERROR_MESSAGE = "‘&lt;’ is an unsafe character";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.SHIFT + ",");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testErrorMessageRenameWithTwoUnsafeCharacters() {

        final String ERROR_MESSAGE = "‘#’ is an unsafe character";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys("#" + Keys.SHIFT + ".");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Ignore
    @Test
    public void testAllTaskTextInSidebar() {
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside']")).click();

        List<String> taskText = List.of(
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

        int a = 1;
        for (String expectedText : taskText) {
            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + a++ + "]")).getText(),
                    expectedText);
        }
    }

    @Test
    public void testDeleteMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NEW_NAME);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + MULTIBRANCH_PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/delete']")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertNotEquals(
                getDriver().findElement(By.xpath("//td//a[@href]/span")).getText(),
                MULTIBRANCH_PIPELINE_NAME);
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
        .clickJobDropdownMenu(MULTIBRANCH_PIPELINE_NAME)
        .clickRenameDropdownMenu(MULTIBRANCH_PIPELINE_NAME)
        .typeNewName(MULTIBRANCH_PIPELINE_NEW_NAME)
        .clickSubmit()
        .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

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
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);

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
        String pipelineNameExpected =  getDriver().findElement(By.xpath("//a[contains(text(),'"
                + MULTIBRANCH_PIPELINE_NAME + "')]")).getText();
        Assert.assertEquals(pipelineNameExpected, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testMultibranchCreationFromExisting() {

        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.className("task-link")).click();

        getDriver().findElement(By.id("name")).sendKeys("Multi2");
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String expectedName = getDriver().findElement(By.xpath("//span[text()='Multi2']")).getText();
        Assert.assertEquals(expectedName, "Multi2");
    }

    @Test
    public void testMultibranchCreationFromNonExisting() {

        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.className("task-link")).click();

        getDriver().findElement(By.id("name")).sendKeys("Multi3");
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys("Multi0");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        String error = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(error, "Error");
    }

    @Test(dependsOnMethods = "testRenameResultOnPageHeading")
    public void testErrorForUnsafeChar() {
        goMultibranchPipelinePage(MULTIBRANCH_PIPELINE_NEW_NAME);

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME + "!");

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"));
        actions.moveToElement(element).moveByOffset(100, 50).click().build().perform();

        WebElement error_message = getDriver().findElement(By.xpath("//div[@class='error']"));

        getWait2().until(d -> error_message.isDisplayed());

        Assert.assertEquals(error_message.getText(), "‘!’ is an unsafe character");
    }

    @Test
    public void testRenameUsingSidebar() {
       createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        goMultibranchPipelinePage(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[contains(text(),'"
                + MULTIBRANCH_PIPELINE_NEW_NAME + "')]")).getText(), MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test
    public void testRenameResultInBreadcrumb() {
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        goMultibranchPipelinePage(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        List<WebElement> elements = getDriver().findElements(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"));
        List<String> breadcrumb = new ArrayList<>();
        for (WebElement element : elements) {
            breadcrumb.add(element.getText());
        }

        Assert.assertTrue(breadcrumb.contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Test
    public void testRenameResultOnPageHeading() {
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        goMultibranchPipelinePage(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test
    public void testRenameResultOnDashboard() {
        createMultibranchPipelineAndClickDashboard(MULTIBRANCH_PIPELINE_NAME);
        goMultibranchPipelinePage(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//div[8]/span/a")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).clear();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        getDashboardLink();

        List<WebElement> elements = getDriver().findElements(By.xpath("//td/a"));
        List<String> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            jobs.add(element.getText());
        }

        Assert.assertTrue(jobs.contains(MULTIBRANCH_PIPELINE_NEW_NAME));
    }

    @Test
    public void testSidebarMenuConsistingOfTenTasks() {
        final int quantityOfTasks = 10;

        createProject("Multibranch Pipeline", MULTIBRANCH_PIPELINE_NAME, true);
        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        Assert.assertEquals(
                getDriver().findElements(By.xpath("//span[@class='task-link-wrapper ']")).size(),
                quantityOfTasks);
    }

    @Test
    public void testVisibilityTasksOfSidebarMenu() {
        createProject("Multibranch Pipeline", MULTIBRANCH_PIPELINE_NAME, true);
        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));

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

        getDriver().findElement(By.xpath("//tr[@id='job_Test_Folder']//a[@href='job/" +name + "/']")).click();
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
