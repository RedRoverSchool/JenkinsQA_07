package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

    public class Folder12Test extends BaseTest {
        private final String NESTED_FOLDER_NAME = "Inserted Folder";
        private final String RENAMED_FOLDER_NAME = "Renamed Folder";

        private void goToDashboard() {
            getDriver().findElement(By.linkText("Dashboard")).click();
        }

        private void addDescription(String description) {
            getDriver().findElement(By.id("description-link")).click();
            getDriver().findElement(By.name("description")).clear();
            getDriver().findElement(By.name("description")).sendKeys(description);
            getDriver().findElement(By.name("Submit")).click();
        }

        @Test
        public void testCreateNewFolder() {
            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("name")).sendKeys("Original Folder");
            getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
            goToDashboard();

            assertEquals(getDriver().findElement(
                    By.xpath("//a[@href = 'job/Original%20Folder/']")).getText(), "Original Folder");
        }

        @Test(dependsOnMethods = "testCreateNewFolder")
        public void testRenameFolderThroughtLeftPanelWithValidName() {
            getDriver().findElement(By.xpath("//a[@href = 'job/Original%20Folder/']")).click();
            getDriver().findElement(By.xpath("//a[@ href='/job/Original%20Folder/confirm-rename']")).click();
            getDriver().findElement(By.name("newName")).clear();
            getDriver().findElement(By.name("newName")).sendKeys(RENAMED_FOLDER_NAME);
            getDriver().findElement(By.name("Submit")).click();
            goToDashboard();

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='job_" + RENAMED_FOLDER_NAME + "']/td[3]/a/span")).getText(), RENAMED_FOLDER_NAME);
        }

        @Test(dependsOnMethods = "testRenameFolderThroughtLeftPanelWithValidName")
        public void testRenameFolderThroughtLeftPanelWithEmptyName() {
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            getDriver().findElement(By.xpath("//a[@ href='/job/Renamed%20Folder/confirm-rename']")).click();
            getDriver().findElement(By.name("newName")).clear();
            getDriver().findElement(By.name("newName")).sendKeys("");
            getDriver().findElement(By.name("Submit")).click();

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='main-panel']/p")).getText(), "No name is specified");
        }

        @Test(dependsOnMethods = "testRenameFolderThroughtLeftPanelWithEmptyName")
        public void testRenameFolderThroughtLeftPanelWithSpecialCharacters() {
            char unsafeCharacter = '$';

            goToDashboard();
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            getDriver().findElement(By.xpath("//a[@ href='/job/Renamed%20Folder/confirm-rename']")).click();
            getDriver().findElement(By.name("newName")).clear();
            getDriver().findElement(By.name("newName")).sendKeys(RENAMED_FOLDER_NAME + unsafeCharacter);
            getDriver().findElement(By.name("Submit")).click();

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='main-panel']/p")).getText(), "‘$’ is an unsafe character");
        }

        @Test(dependsOnMethods = "testRenameFolderThroughtLeftPanelWithSpecialCharacters")
        public void testAddFolderDescription() {
            goToDashboard();
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            addDescription("First description");

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='description']/div[1]")).getText(), "First description");
        }

        @Test(dependsOnMethods = "testAddFolderDescription")
        public void testChangeFolderDescription() {
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            addDescription("Second description");

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='description']/div[1]")).getText(), "Second description");
        }

        @Test(dependsOnMethods = "testChangeFolderDescription")
        public void testDeleteFolderDescription() {
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            addDescription("");

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='description']/div[1]")).getText(), "");
        }

        @Test(dependsOnMethods = "testDeleteFolderDescription")
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

        @Test(dependsOnMethods = "testConfigureFolderCheckConfigurationMenu")
        public void testCreateJobInFolder() {
            getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
            getDriver().findElement(By.linkText("Create a job")).click();

            getDriver().findElement(By.id("name")).sendKeys("First Project");
            getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();

            goToDashboard();
            getDriver().findElement(By.xpath("//*[@id= 'job_" + RENAMED_FOLDER_NAME + "']/td[3]/a")).click();

            assertTrue(getDriver().findElement(
                    By.xpath("//*[@id='job_First Project']/td[3]/a/span")).isDisplayed());
        }

        @Test(dependsOnMethods = "testCreateJobInFolder")
        public void testMoveFolder() {
            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("name")).sendKeys(NESTED_FOLDER_NAME);
            getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();

            goToDashboard();
            getDriver().findElement(By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a")).click();
            getDriver().findElement(By.xpath("//*[@href='/job/Inserted%20Folder/move']")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select/option[2]")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();

            goToDashboard();
            getDriver().findElement(By.xpath("//*[@id= 'job_" + RENAMED_FOLDER_NAME + "']/td[3]/a")).click();

            assertEquals(getDriver().findElement(
                    By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a/span")).getText(), NESTED_FOLDER_NAME);
        }
    }