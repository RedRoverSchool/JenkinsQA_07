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
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {
    private static final String NAME_FOR_SELECT_JOB = "Folder";
    private static final String FOLDER_NAME = "FolderName";
    private static final String NAME_FOR_BOUNDARY_VALUES = "A";
    private static final String RENAMED_FOLDER = "RenamedFolder";
    private static final String NESTED_FOLDER = "Nested";
    private static final String JOB_NAME = "New Job";
    private static final String DESCRIPTION_NAME = "Description Name";

    @DataProvider
    public Object[][] provideUnsafeCharacters() {

        return new Object[][]{
                {"#"}, {"&"}, {"?"}, {"!"}, {"@"}, {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"\\"}, {"<"}, {">"},
                {"["}, {"]"}, {":"}, {";"}
        };
    }

    @Test
    public void testCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .createJob(FOLDER_NAME, NAME_FOR_SELECT_JOB, new FolderConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        HomePage homePage = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(RENAMED_FOLDER)
                .clickRename()
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
                .clickMove(new FolderDetailsPage(getDriver()))
                .goHomePage()
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()));

        Assert.assertTrue(folderDetailsPage.getJobListInsideFolder().contains(NESTED_FOLDER));
    }

    @Test(dependsOnMethods = {"testCreate", "testRename"})
    public void testAddDisplayName() {
        final String expectedFolderDisplayName = "Best folder";

        List<String> jobList = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickConfigureFolder()
                .typeDisplayName(expectedFolderDisplayName)
                .clickSaveButton()
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobList.contains(expectedFolderDisplayName));
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
        String listJob = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES)
                .goHomePage()
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES.repeat(255))
                .goHomePage()
                .getJobList()
                .toString();

        Assert.assertTrue(listJob.contains(NAME_FOR_BOUNDARY_VALUES));
        Assert.assertTrue(listJob.contains(NAME_FOR_BOUNDARY_VALUES.repeat(255)));
    }

    @Test
    public void testNegativeBoundaryValuesNameGetErrorMessage() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FOR_BOUNDARY_VALUES.repeat(256))
                .selectItemFolder()
                .clickOkWithError(new ErrorPage(getDriver()))
                .getErrorMessageFromOopsPage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testNegativeBoundaryValuesNameAbsenceOnHomePage() {
        String listJob = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FOR_BOUNDARY_VALUES.repeat(256))
                .selectItemFolder()
                .clickOkWithError(new ErrorPage(getDriver()))
                .goHomePage()
                .getJobList()
                .toString();

        Assert.assertFalse(listJob.contains(NAME_FOR_BOUNDARY_VALUES.repeat(256)));
    }

    @Test(dependsOnMethods = "testRename")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "This is Folder's description";

        String actualDescription = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER,new FolderDetailsPage(getDriver()))
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
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionTextArea()
                .clickSave();

        Assert.assertTrue(folderDescription.getActualFolderDescription().isEmpty());
        Assert.assertEquals(folderDescription.getDescriptionButtonText(), "Add description");
    }

    @Test(dependsOnMethods = {"testCreate", "testRename"})
    public void testRenameWithEndingPeriod() {
        final String point = ".";

        String errorMessage = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(point)
                .clickRenameWithError(new ErrorPage(getDriver())).getErrorMessage();

        Assert.assertEquals(errorMessage, "“.” is not an allowed name");
    }

    @Test(dependsOnMethods = {"testCreate", "testRenameWithEndingPeriod"})
    public void testRenameFolderThroughLeftPanelWithEmptyName() {
        String errorMessage = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickRename()
                .typeNewName("")
                .clickRenameWithError(new ErrorPage(getDriver())).getErrorMessage();

        Assert.assertEquals(errorMessage, "No name is specified");
    }

    @Test(dependsOnMethods = {"testCreate", "testRename", "testRenameWithEndingPeriod", "testRenameFolderThroughLeftPanelWithEmptyName"})
    public void testFolderDescriptionPreviewWorksCorrectly() {

        String previewText = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickConfigureFolder()
                .typeDescription(DESCRIPTION_NAME)
                .clickPreviewDescription()
                .getFolderDescription();

        Assert.assertEquals(previewText, DESCRIPTION_NAME);
    }

    @Ignore
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

    @Ignore
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
