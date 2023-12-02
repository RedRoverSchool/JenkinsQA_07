package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.MultibranchPipelineConfigurationPage;
import school.redrover.model.MultibranchPipelineDetailsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";

    private static final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";

    private static final String MULTIBRANCH_PIPELINE_NON_EXISTING_NAME = "MultibranchPipelineNonExistingName";

    private final List<String> requiredNamesOfTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
            "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");

    private List<String> getTextOfWebElements(List<WebElement> elements) {
        List<String> textOfWebElements = new ArrayList<>();

        for (WebElement element : elements) {
            textOfWebElements.add(element.getText());
        }
        return textOfWebElements;
    }

    @Test
    public void testMultibranchPipelineCreationWithCreateAJob() {

        String multibranchBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOk(new MultibranchPipelineConfigurationPage(getDriver()))
                .getJobNameFromBreadcrumb();

        Assert.assertEquals(multibranchBreadcrumbName, MULTIBRANCH_PIPELINE_NAME,
                multibranchBreadcrumbName + " name doesn't match " + MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testRenameMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        String expectedResultName = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineConfigurationPage(getDriver()))
                .confirmRename(MULTIBRANCH_PIPELINE_NAME)
                .clearField()
                .inputName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .buttonSubmit()
                .getJobName();

        String nameH1 = new MultibranchPipelineConfigurationPage(getDriver()).getHeadLineText();

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

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        List<String> actualTasksText = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .getTasksText();

        Assert.assertEquals(actualTasksText, expectedTasksText);
    }

    @Test
    public void testDeleteMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NEW_NAME, true);

        String actualResult = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineConfigurationPage(getDriver()))
                .clickButtonDelete()
                .clickRedButtonYes()
                .multibranchPipelineName();

        Assert.assertNotEquals(
                actualResult, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultiConfigurationPipeline() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOk(new MultibranchPipelineConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.isProjectExist(MULTIBRANCH_PIPELINE_NAME));
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
    public void testEnabledByDefault() {
        String status = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickConfigure()
                .getDisableToggleText();

        Assert.assertEquals(status, "Enabled");
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationPipeline", "testEnabledByDefault"})
    public void testSeeAAlertAfterDisableMultibranchPipeline() {
        String actualStatusMessage = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickDisable()
                .getDisableStatusMessage();

        Assert.assertTrue(actualStatusMessage.contains("This Multibranch Pipeline is currently disabled"),
                "Incorrect or missing text");
    }

    @Test
    public void testMultibranchNameDisplayBreadcrumbTrail() {

        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, false);

        String pipelineNameExpected = getDriver().findElement(By.xpath("//a[contains(text(),'"
                + MULTIBRANCH_PIPELINE_NAME + "')]")).getText();
        Assert.assertEquals(pipelineNameExpected, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testMultibranchCreationFromExisting() {

        boolean homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .populateFieldCopyFrom(MULTIBRANCH_PIPELINE_NAME)
                .clickOk(new MultibranchPipelineConfigurationPage(getDriver()))
                .goHomePage()
                .getJobList()
                .contains(MULTIBRANCH_PIPELINE_NEW_NAME);

        Assert.assertTrue(homePage);
    }

    @Test (dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testMultibranchCreationFromNonExisting() {

        String error = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTIBRANCH_PIPELINE_NEW_NAME)
                .populateFieldCopyFrom(MULTIBRANCH_PIPELINE_NON_EXISTING_NAME)
                .clickOk(new MultibranchPipelineConfigurationPage(getDriver()))
                .error();

        Assert.assertEquals(error, "Error");
    }


    @Ignore("PR#2042, failed with error: expected [MultibranchPipeline] but found [Search for 'MultibranchPipeline']")
    @Test(dependsOnMethods = "testMultibranchPipelineCreationWithCreateAJob")
    public void testFindByQuickSearch() {
        MultibranchPipelineDetailsPage multibranchPipelineDetailsPage = new HomePage(getDriver())
                .useSearchBox(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineDetailsPage(getDriver()));

        Assert.assertEquals(multibranchPipelineDetailsPage.getHeadLineText(), MULTIBRANCH_PIPELINE_NAME);
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
                .getHeadLineText();

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
                .getHeadLineText();

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

        TestUtils.createFolder(this, "Nested Folder", true);
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));
        namesOfTasks.removeAll(requiredNamesOfTasks);

        Assert.assertEquals(namesOfTasks.toString(), "[Move]");
    }

    @Test
    public void testVisibilityOfAdditionalTaskOfSidebarMenuIfProjectInsideFolder() {
        final String folderName = "Wrapper Folder";

        TestUtils.createFolder(this, folderName, false);
        TestUtils.createMultibranchPipeline(this, MULTIBRANCH_PIPELINE_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='" + MULTIBRANCH_PIPELINE_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));

        Assert.assertTrue(namesOfTasks.contains("Move"), "Move is not the additional task of sidebar menu on the left");
    }

    @Test(dependsOnMethods = "testRenameUsingSidebar")
    public void testDisable() {

        String disabledText = new HomePage(getDriver())
                .clickJobByName(MULTIBRANCH_PIPELINE_NEW_NAME, new MultibranchPipelineDetailsPage(getDriver()))
                .clickDisable()
                .getDisableStatusMessage();

        Assert.assertTrue(disabledText.contains("This Multibranch Pipeline is currently disabled"));
    }

    @Test(dependsOnMethods = "testDisable")
    public void testEnableFromStatusPage() {
        getDriver().findElement(By.xpath("//*[@id=\"job_Test_Folder\"]/td[3]/a")).click();

        getDriver().findElement(By.xpath("//form[@id='enable-project']/button")).click();

        Assert.assertEquals(getDriver().findElement(By.name(
                "Submit")).getText(), "Disable Multibranch Pipeline");
    }
}
