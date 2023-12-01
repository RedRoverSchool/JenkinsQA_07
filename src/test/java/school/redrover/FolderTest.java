package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "FolderName";
    private static final String NAME_FOR_BOUNDARY_VALUES = "A";
    private static final String RENAMED_FOLDER = "RenamedFolder";
    private static final String NESTED_FOLDER = "Nested";
    private static final String JOB_NAME = "New Job";

    @DataProvider
    public Object[][] provideUnsafeCharacters() {

        return new Object[][]{
                {"#"}, {"&"}, {"?"}, {"!"}, {"@"}, {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"\\"}, {"<"}, {">"},
                {"["}, {"]"}, {":"}, {";"}
        };
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void utilsGoNameField() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();
    }

    private void create(String folderName) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FOLDER_NAME)
                .selectItemFolder()
                .clickOk(new FolderConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        HomePage homePage = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(RENAMED_FOLDER)
                .clickSubmit()
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(RENAMED_FOLDER));
    }

    @Test(dependsOnMethods = "testRename")
    public void testCreateNewJob() {
        boolean isJobCreated = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickCreateJob()
                .typeItemName(JOB_NAME)
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .isJobExist();

        Assert.assertTrue(isJobCreated);
    }

    @Test(dependsOnMethods = "testCreateNewJob")
    public void testMoveFolderToFolder() {
        FolderDetailsPage folderDetailsPage = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(NESTED_FOLDER)
                .goHomePage()
                .clickJobByName(NESTED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickMove()
                .clickArrowDropDownMenu()
                .clickFolderByName(RENAMED_FOLDER)
                .clickMove()
                .goHomePage()
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()));

        Assert.assertTrue(folderDetailsPage.getJobListInsideFolder().contains(NESTED_FOLDER));
    }

    @Test(dependsOnMethods = {"testCreate", "testRename"})
    public void testAddDisplayName() {
        final String expectedFolderDisplayName = "Best folder";

        String actualFolderDisplayName = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickConfigure()
                .typeDisplayName(expectedFolderDisplayName)
                .clickSave()
                .goHomePage()
                .getJobDisplayName();

        Assert.assertEquals(actualFolderDisplayName, expectedFolderDisplayName);
    }

    @Ignore
    @Test
    public void testRenameFolderUsingBreadcrumbDropdownOnFolderPage() {

        final String NEW_FOLDER_NAME = "FolderNew";

        createFolder(FOLDER_NAME);

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), NEW_FOLDER_NAME,
                FOLDER_NAME + " is not equal " + NEW_FOLDER_NAME);
    }

    @Test
    public void testErrorMessageIsDisplayedWithoutFolderName() {
        String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectItemFolder()
                .getRequiredNameErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void testOKButtonIsNotClickableWithoutFolderName() {
        boolean isOkButtonDisabled = new HomePage(getDriver())
                .clickNewItem()
                .selectItemFolder()
                .isOkButtonEnabled();

        Assert.assertFalse(isOkButtonDisabled, "OK button is clickable when it shouldn't be!");
    }

    @Ignore
    @Test
    public void testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder() {
        String actualTooltipValue = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(FOLDER_NAME)
                .clickSaveButton()
                .clickNewItemButton()
                .createPipeline(JOB_NAME)
                .clickSaveButton()
                .clickBuildNowButton()
                .getTooltipAttributeValue();

        Assert.assertEquals(actualTooltipValue, "Success > Console Output");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder")
    public void testDeletePipelineInsideOfFolder() {
        int sizeOfEmptyJobListInsideOfFolderAfterJobDeletion = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickJobByName(JOB_NAME, new PipelineDetailsPage(getDriver()))
                .deletePipelineJobInsideOfFolder()
                .getJobListInsideFolder().size();

        Assert.assertEquals(sizeOfEmptyJobListInsideOfFolderAfterJobDeletion, 0);
    }

    @Test(dataProvider = "provideUnsafeCharacters")
    public void testCreateNameSpecialCharactersGetMessage(String unsafeChar) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectItemFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» ‘" + unsafeChar + "’ is an unsafe character");
    }

    @Test(dataProvider = "provideUnsafeCharacters")
    public void testDisabledOkButtonCreateWithInvalidName(String unsafeChar) {
        boolean enabledOkButton = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectItemFolder()
                .isOkButtonEnabled();

        Assert.assertFalse(enabledOkButton);
    }

    @Test
    public void testPositiveBoundaryValuesName() {
        String listCreated = new  HomePage(getDriver())
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES)
                .goHomePage()
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES.repeat(255))
                .goHomePage()
                .getJobList()
                .toString();

        Assert.assertTrue(listCreated.contains(NAME_FOR_BOUNDARY_VALUES));
        Assert.assertTrue(listCreated.contains(NAME_FOR_BOUNDARY_VALUES.repeat(255)));
    }

    @Test
    public void testNegativeBoundaryValuesNameGetErrorMessage() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FOR_BOUNDARY_VALUES.repeat(256))
                .selectItemFolder()
                .clickOk(new ErrorPage(getDriver()))
                .getErrorMassageFromOopsPage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testNegativeBoundaryValuesNameAbsenceOnHomePage() {
        boolean createdInvalidBoundaryValues= new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FOR_BOUNDARY_VALUES.repeat(256))
                .selectItemFolder()
                .clickOk(new ErrorPage(getDriver()))
                .goHomePage()
                .getJobList().contains(NAME_FOR_BOUNDARY_VALUES.repeat(256));

        Assert.assertFalse(createdInvalidBoundaryValues);
    }

    @Test(dependsOnMethods = "testRename")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "This is Folder's description";

        String actualDescription = new HomePage(getDriver())
                .clickAnyJobCreated(new FolderDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .typeDescription(descriptionText)
                .clickSave()
                .getActualFolderDescription();

        Assert.assertEquals(actualDescription, descriptionText);
    }

    @Test(dependsOnMethods = {"testAddDescriptionToFolder", "testRename", "testCreate"})
    public void testEditDescriptionOfFolder() {
        final String newDescriptionText = "This is new Folder's description";

        String actualUpdatedDescription = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .typeDescription(newDescriptionText)
                .clickSave()
                .getActualFolderDescription();

        Assert.assertEquals(actualUpdatedDescription, newDescriptionText);
    }

    @Test(dependsOnMethods = {"testAddDescriptionToFolder"})
    public void testDeleteDescriptionOfFolder() {
        FolderDetailsPage folderDescription = new HomePage(getDriver())
                .clickAnyJobCreated(new FolderDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionTextArea()
                .clickSave();

        Assert.assertTrue(folderDescription.getActualFolderDescription().isEmpty());
        Assert.assertEquals(folderDescription.getDescriptionButtonText(), "Add description");
    }

    @Ignore
    @Test
    public void testSubFolderInBreadcrumbs() {
        final String folderName = "Test Folder";
        final String subfolderName = "Test SubFolder";

        create(folderName);

        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']",
                folderName.replace(" ", "%20")))).click();
        getDriver().findElement(By.xpath("//li/a[@href='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(subfolderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        List<String> listBreadcrumbsItems = getDriver().findElements(
                        By.xpath("//li[@class='jenkins-breadcrumbs__list-item']/a"))
                .stream().map(WebElement::getText).toList();
        List<String> listExpectedItems = new ArrayList<>(List.of
                (new String[]{"Dashboard", folderName, subfolderName}));

        Assert.assertEquals(listBreadcrumbsItems, listExpectedItems);
    }

    @Test
    public void testAddDescription() {
        final String description = "Test123";

        createFolder("Test");

        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), description);
    }

    @Test
    public void testClickPreview() {
        createFolder(FOLDER_NAME);

        getDriver().findElement(By.name("_.description")).sendKeys("description123");
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(getDriver().findElement(By.className("textarea-preview")).getText(), "description123");
    }

    @Test
    public void testRenameWithEndingPeriod() {
        char period = '.';

        createFolder(FOLDER_NAME);
        getDashboardLink();
        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("FOLDER_WITH_UNSAFE_CHARACTER" + period);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "A name cannot end with ‘" + period + "’");
    }

    @Ignore
    @Test
    public void testMoveThroughSidePanel() {
        create(FOLDER_NAME);
        getDashboardLink();

        create(NESTED_FOLDER);
        getDashboardLink();

        getDriver().findElement(By.xpath("//a[@href='job/" + NESTED_FOLDER + "/']")).click();
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[contains(text(),'Jenkins » " + FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDashboardLink();
        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + NESTED_FOLDER + "/']")).click();

        ArrayList<String> expectedBreadcrumbs = new ArrayList<>();
        expectedBreadcrumbs.add("Dashboard");
        expectedBreadcrumbs.add(FOLDER_NAME);
        expectedBreadcrumbs.add(NESTED_FOLDER);

        ArrayList<String> actualBreadcrumbs = new ArrayList<>();
        List<WebElement> breadcrumbs = getDriver().findElements(By.xpath("//li/a[@class='model-link']"));
        for (WebElement eachBreadcrumb : breadcrumbs) {
            actualBreadcrumbs.add(eachBreadcrumb.getText());
        }

        Assert.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs don't match");
    }

    @Ignore
    @Test(dependsOnMethods = "testRename")
    public void testRenameFolderThroughLeftPanelWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.xpath("//a[@ href='/job/Renamed%20Folder/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/p")).getText(), "No name is specified");
    }

    @Ignore
    @Test(dependsOnMethods = "testAddFolderDescription")
    public void testChangeFolderDescription() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys("Second description");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='description']/div[1]")).getText(), "Second description");
    }

    @Ignore
    @Test
    public void testConfigureFolderCheckConfigurationMenu() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

        List<String> listOfExpectedMenuItems = Arrays.asList("General", "Health metrics", "Properties");

        List<WebElement> listOfMenuItems = getDriver().findElements(
                By.xpath("//span[@class = 'task-link-text' and contains(., '')]"));
        List<String> extractedMenuItems = listOfMenuItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(extractedMenuItems, listOfExpectedMenuItems);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreate")
    public void folderDescriptionPreviewWorksCorrectly() {
        String description = "Folder description";
        HomePage homePage = new HomePage(getDriver());
        String previewText = homePage.clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickConfigureFolder()
                .typeDescription(description)
                .clickPreviewDescription()
                .getFolderDescription();

        Assert.assertEquals(previewText, description);
    }

    @Test
    public void testAddChildHealthMetric() {

        boolean isChildHealthMetricDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(FOLDER_NAME)
                .clickHealthMetricsInSideMenu()
                .clickHealthMetrics()
                .clickAddHealthMetric()
                .selectChildHealthMetric()
                .clickSaveButton()
                .clickConfigureInSideMenu()
                .clickHealthMetrics()
                .isChildHealthMetricDisplayed();

        Assert.assertTrue(isChildHealthMetricDisplayed);
    }

    @Test(dependsOnMethods = "testAddChildHealthMetric")
    public void testDisplayingHelpTextButtonRecursive() {
        final String expectedText = "Controls whether items within sub-folders will be considered as contributing to the health of this folder.";

        String helpText = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickConfigureFolder()
                .clickHealthMetrics()
                .clickHelpButtonRecursive()
                .getHelpBlockText();

        Assert.assertEquals(helpText, expectedText);
    }
}
