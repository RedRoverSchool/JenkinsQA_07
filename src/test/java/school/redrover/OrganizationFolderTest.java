package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

public class OrganizationFolderTest extends BaseTest {
    private static final String PROJECT_NAME = "Organization Folder";
    private static final String NEW_PROJECT_NAME = "Organization Folder Renamed";
    private static final String CLONE_NAME = "Organization Folder Clone";

    @DataProvider
    public Object[][] provideRandomUnsafeCharacter() {
        String[] wrongCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "?", "|", ">", "[", "]"};
        int randomIndex = new Random().nextInt(wrongCharacters.length);
        return new Object[][]{{wrongCharacters[randomIndex]}};
    }

    @Test
    public void testCreateOrganizationFolderWithValidName() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .createOrganizationFolder(PROJECT_NAME)
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobList.contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithValidName")
    public void testCreateOrganizationFolderWithExistingName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithExistingName", dataProvider = "provideRandomUnsafeCharacter")
    public void testCreateProjectWithUnsafeCharacters(String unsafeChar) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» ‘" + unsafeChar + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testCreateProjectWithUnsafeCharacters")
    public void testCreateOrganizationFolderWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectOrganizationFolder();

        Assert.assertEquals(newItemPage.getRequiredNameErrorMessage(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled(), "OK button should NOT be enabled");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithEmptyName")
    public void testCreateOrganizationFolderWithSpaceInsteadOfName() {
        final String nameWithSpace = " ";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(nameWithSpace)
                .selectOrganizationFolder()
                .clickOkWithError(new ErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No name is specified");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithSpaceInsteadOfName")
    public void testCreateOrganizationFolderWithLongName() {
        final String longName = "a".repeat(256);

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(longName)
                .selectOrganizationFolder()
                .clickOkWithError(new AngryErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithLongName")
    public void testCreateOrganizationFolderWithInvalidNameWithTwoDots() {
        final String name = "..";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(name)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» “..” is not an allowed name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled(), "OK button should NOT be enabled");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithInvalidNameWithTwoDots")
    public void testCreateOrganizationFolderWithInvalidNameWithDotAtEnd() {
        final String name = "name.";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(name)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    @Test
    public void testRenameProjectFromProjectDropdown() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, true);

        String newProjectName = new HomePage(getDriver())
                .clickRenameJobDropdownMenu(PROJECT_NAME, new OrganizationFolderRenamePage(getDriver()))
                .enterNewName(NEW_PROJECT_NAME)
                .clickRenameButton(new OrganizationFolderDetailsPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectFromProjectPage() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, false);

        String newProjectName = new OrganizationFolderDetailsPage(getDriver())
                .clickRenameOption(new OrganizationFolderRenamePage(getDriver()))
                .enterNewName(NEW_PROJECT_NAME)
                .clickRenameButton(new OrganizationFolderDetailsPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectWithSameName() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, true);

        String message = new HomePage(getDriver())
                .clickRenameJobDropdownMenu(PROJECT_NAME, new OrganizationFolderRenamePage(getDriver()))
                .getWarningMessageText();

        Assert.assertEquals(message, "The new name is the same as the current name.");
    }

    @Test(dependsOnMethods = "testRenameProjectWithSameName")
    public void testDisableProject() {
        String disableMessageText = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new OrganizationFolderDetailsPage(getDriver()))
                .clickDisableButton()
                .getDisabledMessageText();

        Assert.assertEquals(disableMessageText, "This Organization Folder is currently disabled");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithInvalidNameWithDotAtEnd")
    public void testCloneNotExistingJob() {
        final String wrongName = "Organization Folder Wrong";

        String errorPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(CLONE_NAME)
                .enterExistentItemNameToClone(wrongName)
                .clickOk(new ErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(errorPage, "No such job: " + wrongName);
    }

    @Test(dependsOnMethods = "testCloneNotExistingJob")
    public void testCloneOrganizationFolder() {
        final String[] organizationFolderName = {PROJECT_NAME, CLONE_NAME};

        HomePage cloneFolder = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME,new OrganizationFolderDetailsPage(getDriver()))
                .clickConfigureSideMenu()
                .clickPeriodicallyCheckbox()
                .clickSave()
                .goHomePage()
                .clickNewItem()
                .typeItemName(CLONE_NAME)
                .enterExistentItemNameToClone(PROJECT_NAME)
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .goHomePage();

        for (String x: organizationFolderName) {
            Assert.assertTrue(cloneFolder.getJobList().contains(x));
        }
    }

    @Test(dependsOnMethods = "testCloneOrganizationFolder")
    public void testDisableExistingOrganizationFolder() {
        String disableButtonText = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new OrganizationFolderDetailsPage(getDriver()))
                .clickDisable()
                .submitButtonText();

        Assert.assertEquals(disableButtonText, "Enable");
    }

    @Test
    public void testOnDeletingOrganizationFolder() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .clickSave()
                .clickDelete()
                .getJobList();

        Assert.assertFalse(jobList.contains(PROJECT_NAME));
    }


    @Test
    public void testRedirectAfterDeleting() {
        String pageTitle = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .clickSave()
                .clickDelete().getTitle();

        Assert.assertEquals(pageTitle, "Dashboard [Jenkins]");
    }
}
