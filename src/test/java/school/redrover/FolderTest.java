package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import static org.testng.AssertJUnit.assertEquals;


public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "Folder";

    private void creationNewFolder(String folderName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void folderCreation(String FOLDER_NAME) {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Folder']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
    }

    private void createFolderAddReturnToDashboard(String folderName) {
        getDriver().findElement(By.className("task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
    }

    private WebElement findJobByName(String name) {
        return getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", name)));
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

        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/confirm-rename']",oldFolderName))).click();
        WebElement inputName = getDriver().findElement(By.name("newName"));
        inputName.clear();
        inputName.sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();
        getDashboardLink();

        Assert.assertEquals(findJobByName(newFolderName).getText(),
                newFolderName);


    }

    private void creatNewFolder(String folderName) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Ignore
    @Test
    public void testRenameWithInvalidName() {
        final String oldFolderName = "Old folder";
        final String invalidFolderName = "*";

        creationNewFolder(oldFolderName);

        getDashboardLink();

        getDriver().findElement(By.xpath("//*[@id='job_" + oldFolderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]/span/a")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(invalidFolderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/p")).getText(), "‘" + invalidFolderName + "’ is an unsafe character");
    }

    @Ignore
    @Test
    public void TestMoveFolder() {
        final String firstFolderName = "Original Folder";
        final String secondFolderName = "Inserted Folder";

        creatNewFolder(firstFolderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        creatNewFolder(secondFolderName);
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
    public void testCreatingNewFolder() {
        final String folderName = "TestFolder";

        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//img[@class='icon-folder icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).getText(),
                folderName);

    }

    @Test
    public void testCreatingNewFolder1 () {
       final String folderName = "My new project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-nested-projects\"]/ul/li[1]")).click();
                getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]"));

        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        Assert.assertEquals
                (getDriver().findElement(By.xpath("//span[text()='My new project']")).getText(),
                        folderName);

    }

    @Ignore
    @Test
    public void testRenameFolderUsingBreadcrumbDropdownOnFolderPage() {

        final String NEW_FOLDER_NAME = "FolderNew";

        folderCreation(FOLDER_NAME);

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), NEW_FOLDER_NAME,
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

    @Test
    public void testAddDisplayName() {
        final String folderDisplayName = "Best folder";

        createFolderAddReturnToDashboard(FOLDER_NAME);

        WebElement folder = getDriver().findElement(By.xpath("//*[@id='job_" + FOLDER_NAME + "']/td[3]/a"));
        new Actions(getDriver())
                .moveToElement(folder)
                .click()
                .perform();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderDisplayName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        String folderName = getDriver()
                .findElement(By.xpath("//*[@id='job_" + FOLDER_NAME + "']/td[3]/a/span"))
                .getText();

        Assert.assertEquals(folderName, folderDisplayName);
    }

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
}

