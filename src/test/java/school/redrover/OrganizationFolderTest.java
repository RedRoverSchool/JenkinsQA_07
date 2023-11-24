package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NewItemPage;
import school.redrover.model.OrganizationFolderConfigurationPage;
import school.redrover.model.OrganizationFolderDetailsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

public class OrganizationFolderTest extends BaseTest {
    private static final String PROJECT_NAME = "Organization Folder";
    private static final String NEW_PROJECT_NAME = "Organization Folder Renamed";

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

    @Test (dependsOnMethods = "testCreateProjectWithUnsafeCharacters")
    public void testCreateOrganizationFolderWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectOrganizationFolder();

        Assert.assertEquals(newItemPage.getRequiredNameErrorMessage(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled(), "OK button should NOT be enabled");
    }

    @Test (dependsOnMethods = "testCreateOrganizationFolderWithEmptyName")
    public void testCreateOrganizationFolderWithSpaceInsteadOfName() {
        final String nameWithSpace = " ";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(nameWithSpace)
                .selectOrganizationFolder()
                .clickOkWithError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "Error\n" + "No name is specified");
    }

    @Test (dependsOnMethods = "testCreateOrganizationFolderWithSpaceInsteadOfName")
    public void testCreateOrganizationFolderWithLongName() {
        final String longName = "a".repeat(256);

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(longName)
                .selectOrganizationFolder()
                .clickOkWithError()
                .getRequestErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test (dependsOnMethods = "testCreateOrganizationFolderWithLongName")
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

    @Test (dependsOnMethods = "testCreateOrganizationFolderWithInvalidNameWithTwoDots")
    public void testCreateOrganizationFolderWithInvalidNameWithDotAtEnd() {
        final String name = "name.";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(name)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    private void returnHomeJenkins() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createProject(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createOrganizationFolder(String organizationFolderName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();
    }

    @Test
    public void testRenameProjectFromProjectDropdown() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, true);

        String newProjectName = new HomePage(getDriver())
                .hoverOverJobDropdownMenu(PROJECT_NAME)
                .clickRenameOrganizationFolderDropdownMenu()
                .enterNewName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectFromProjectPage() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, false);

        String newProjectName = new OrganizationFolderDetailsPage(getDriver())
                .clickRenameOptionFromLeftSideMenu()
                .enterNewName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testRenameProjectWithSameName() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, true);

        String message = new HomePage(getDriver())
                .hoverOverJobDropdownMenu(PROJECT_NAME)
                .clickRenameOrganizationFolderDropdownMenu()
                .getWarningMessageText();

        Assert.assertEquals(message, "The new name is the same as the current name.");
    }

    @Test
    public void testDisableProject() {
        TestUtils.createOrganizationFolder(this, PROJECT_NAME, false);

        String disableMessageText = new OrganizationFolderDetailsPage(getDriver())
                .clickDisableButton()
                .getDisabledMessageText();

        Assert.assertEquals(disableMessageText, "This Organization Folder is currently disabled");
    }

    @Test
    public void testCloneNotExistingJob() {
        String organizationFolderName = "Organization Folder";
        String organizationFolderWrongName = "Organization Folder Wrong";
        String organizationFolderCloneName = "Organization Folder Clone";
        String errorTitle = "Error";
        String errorMessage = "No such job:";

        createOrganizationFolder(organizationFolderName);
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderCloneName);
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(organizationFolderWrongName);
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), errorTitle);
        Assert.assertTrue(getDriver().findElement(By.xpath("//p")).getText().contains(errorMessage));
    }

    @Test
    public void testCloneOrganizationFolder() {
        String organizationFolderName = "Organization Folder Parent";
        String organizationFolderCloneName = "Organization Folder Clone";
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement checkBox = getDriver().findElement(By.xpath("//section[2]//label[contains(text(), 'Periodically if not otherwise run')]"));
        actions.moveToElement(checkBox).click().build().perform();
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderCloneName);
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.id("ok-button")).click();
        boolean isCheckBoxChecked = getDriver().findElement(By.xpath("//section[2]//input[@id=\"cb2\"]")).isSelected();
        Assert.assertTrue(isCheckBoxChecked);
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id='job_" + organizationFolderName + "']//td//span")).getText(), organizationFolderName);
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id='job_" + organizationFolderCloneName + "']//td//span")).getText(), organizationFolderCloneName);
    }

    @Test
    public void testDisableExistingOrganizationFolder() {

        final String folderName = "OrganizationFolder";

        createProject(folderName);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", folderName))).click();
        getDriver().findElement(By.name("Submit")).click();
        WebElement submitEnable = getDriver().findElement(By.name("Submit"));

        Assert.assertTrue(submitEnable.isDisplayed() && submitEnable.getText().contains("Enable"), "Folder is enable!");
    }

    @Test
    public void testDisableByButton() {
        final String folderName = "OrganizationFolderEnable";

        createProject(folderName);

        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//span[text()='OrganizationFolderEnable']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@name='Submit']")).getText(),
                "Enable");
        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@method='post']")).getText().contains("This Organization Folder is currently disabled"));
    }

    @Test
    public void testOnDeletingOrganizationFolder() {
        HomePage homePage = new HomePage(getDriver());

        homePage.clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .clickSave().clickDelete();

        Assert.assertFalse(homePage.getJobList().contains(PROJECT_NAME));
    }


    @Test
    public void testRedirectAfterDeleting() {
        HomePage homePage = new HomePage(getDriver());

        homePage.clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .clickSave().clickDelete();

        Assert.assertEquals(homePage.getTitle(), "Dashboard [Jenkins]");
    }
}
