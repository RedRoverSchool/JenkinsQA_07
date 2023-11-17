package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FolderGeneralConfigurationPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.Arrays;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "Folder";
    private static final String FOLDER_NAME_2 = "My new project";
    private static final String NAME_FOR_BOUNDARY_VALUES = "A";

    @Test
    public void testCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FOLDER_NAME)
                .selectItemFolder()
                .clickOk(new FolderGeneralConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(FOLDER_NAME));
    }

    private void creationNewFolder(String folderName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void createFolder() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Folder']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
    }

    private WebElement findJobByName(String name) {
        return getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", name)));
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
    public void testRenameWithValidNameFromDropDownMenu() {

        final String folderName = "Folder1";
        final String renamedFolder = "Folder111";
        creationNewFolder(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Folder1/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + renamedFolder + "']")).isDisplayed());
    }

    @Test
    public void testRenameFolder() {

        final String oldFolderName = "FolderToRename";
        final String newFolderName = "RenamedFolder";

        creationNewFolder(oldFolderName);

        getDashboardLink();

        findJobByName(oldFolderName).click();

        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/confirm-rename']", oldFolderName))).click();
        WebElement inputName = getDriver().findElement(By.name("newName"));
        inputName.clear();
        inputName.sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();
        getDashboardLink();

        Assert.assertEquals(findJobByName(newFolderName).getText(),
                newFolderName);
    }

    @Ignore
    @Test
    public void TestMoveFolder() {
        final String firstFolderName = "Original Folder";
        final String secondFolderName = "Inserted Folder";

        create(firstFolderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        create(secondFolderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + secondFolderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//*[@href='/job/Inserted%20Folder/move']")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select/option[2]")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//*[@id= 'job_" + firstFolderName + "']/td[3]/a")).click();

        assertEquals(getDriver().findElement(By.xpath("//*[@id='job_" + secondFolderName + "']/td[3]/a/span")).getText(), secondFolderName);
    }

    @Ignore
    @Test
    public void testRenameFolderUsingBreadcrumbDropdownOnFolderPage() {

        final String NEW_FOLDER_NAME = "FolderNew";

        createFolder();

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), NEW_FOLDER_NAME,
                FOLDER_NAME + " is not equal " + NEW_FOLDER_NAME);
    }

    @Ignore
    @Test
    public void testErrorMessageIsDisplayedWithoutFolderName() {
        String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        boolean errorMessageDisplayed = getDriver().findElement(By.id("itemname-required")).isDisplayed();
        String actualErrorMessage = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertTrue(errorMessageDisplayed, "Error message for empty name is not displayed!");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "The error message does not match the expected message!");
    }

    @Ignore
    @Test
    public void testOKbuttonIsNotClickableWithoutFolderName() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        boolean okButtonDisabled = "true".equals(okButton.getAttribute("disabled"));

        Assert.assertTrue(okButtonDisabled, "OK button is clickable when it shouldn't be!");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatingNewFolder1")
    public void testAddDisplayName() {
        final String folderDisplayName = "Best folder";

        WebElement folder = getDriver().findElement(By.xpath("//*[@id='job_" + FOLDER_NAME_2 + "']/td[3]/a"));
        new Actions(getDriver())
                .moveToElement(folder)
                .click()
                .perform();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderDisplayName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        String actualFolderName = getDriver()
                .findElement(By.xpath("//*[@id='job_" + FOLDER_NAME_2 + "']/td[3]/a/span"))
                .getText();

        Assert.assertEquals(actualFolderName, folderDisplayName);
    }

    @Ignore
    @Test
    public void testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Folder/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Pipeline");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Folder/job/Pipeline/build?delay=0sec']")).click();

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[@href='/job/Folder/job/Pipeline/1/console']")))
                .perform();


        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@href='/job/Folder/job/Pipeline/1/console']")).getAttribute("tooltip"),
                "Success > Console Output");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder")
    public void testDeletePipelineInsideOfFolder() {
        getDriver().findElement(By.xpath("//a[@href='job/Folder/']")).click();
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[@href='/job/Folder/configure']")))
                .click()
                .perform();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();


        getDriver().findElement(By.xpath("//a[@href='job/Pipeline/']")).click();
        getDriver().findElement(By.xpath("//a[@data-url='/job/Folder/job/Pipeline/doDelete']")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.className("h4")).getText(), "This folder is empty");
    }

    @Test
    public void testCreateNameSpecialCharacters() {
        List<String> invalidNames = Arrays.asList("#", "&", "?", "!", "@", "$", "%", "^", "*", "|", "/", "\\", "<", ">", "[", "]", ":", ";");

        utilsGoNameField();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']"));

        for (String invalidName: invalidNames) {

            inputName.sendKeys(invalidName);

            Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']")).getText(), "» ‘" + invalidName + "’ is an unsafe character");

            inputName.clear();
        }
    }

    @Test
    public void testBoundaryValuesName() {
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(1));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//h1[text() = 'Configuration']"));

        getDashboardLink();
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(255));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//h1[text() = 'Configuration']"));

        getDashboardLink();
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(256));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[@style = 'text-align: center']")).getText(), "A problem occurred while processing the request.");
    }
}

