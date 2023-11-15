package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Folder4Test extends BaseTest {

    private static final String MAIN_FOLDER = "MainFolder";

    @Test
    public void testCreateFolder() {
        createFolder(MAIN_FOLDER);

        navigateToDashboard();

        assertTrue(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", MAIN_FOLDER))).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testMoveFolderToFolder() {
        final String folder2Name = "NestedFolder";

        createFolder(folder2Name);
        getDriver().findElement(By.xpath("//a[contains(@href, 'move')]")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath(String.format("//*[@id='main-panel']//option[@value='/%s']", MAIN_FOLDER))).click();
        getDriver().findElement(By.name("Submit")).click();

        navigateToDashboard();

        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", MAIN_FOLDER))).click();

        String nestedFolders = getDriver().findElement(By.xpath("//table[@id='projectstatus']")).getText();
        assertTrue(nestedFolders.contains(folder2Name));
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testCreateJobInsideFolder() {
        final String jobName = "NewProject";

        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", MAIN_FOLDER))).click();
        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        navigateToDashboard();
        getDriver().findElement(By.xpath(String.format("//*[@id='job_%s']/td[3]/a", MAIN_FOLDER))).click();

        assertTrue(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", jobName))).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "This is Folder's description";

        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", MAIN_FOLDER))).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
        assertEquals(actualDescription, descriptionText);
    }

    @Test(dependsOnMethods = {"testCreateFolder", "testAddDescriptionToFolder"})
    public void testEditDescriptionOfFolder() {
        final String newDescriptionText = "This is new Folder's description";

        navigateToItem(MAIN_FOLDER);
        getDriver().findElement(By.xpath("//a[contains(@href, 'editDescription')]")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(newDescriptionText);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String actualNewDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
        assertEquals(actualNewDescription, newDescriptionText);
    }

    @Test(dependsOnMethods = {"testCreateFolder", "testAddDescriptionToFolder"})
    public void testDeleteDescriptionOfFolder() {
        navigateToItem(MAIN_FOLDER);
        getDriver().findElement(By.xpath("//a[contains(@href, 'editDescription')]")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String textOfDescriptionField = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
        assertEquals(textOfDescriptionField, "");

        String appearanceOfAddDescriptionButton = getDriver().findElement(By.xpath("//div[@id='description']/div[2]")).getText();
        assertEquals(appearanceOfAddDescriptionButton, "Add description");
    }

    private void navigateToItem(String itemName) {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", itemName))).click();
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void navigateToDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }
}
