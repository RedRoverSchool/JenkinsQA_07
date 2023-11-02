package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.AssertJUnit.assertEquals;

    public class Folder12Test extends BaseTest {
        private final String FOLDER_NAME = "Original Folder";
        private final String NESTED_FOLDER_NAME = "Inserted Folder";
        private final String FREESTYLE_PROJECT = "First Project";

        private void createNewFolder(String folderName) {
            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("name")).sendKeys(folderName);
            getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
        }

        private void createNewFreestyleProject(String projectName) {
            getDriver().findElement(By.id("name")).sendKeys(projectName);
            getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
        }

        private void goToDashboard() {
            getDriver().findElement(By.linkText("Dashboard")).click();
        }

        @Test
        public void TestCreateJobInFolder() {
            createNewFolder(FOLDER_NAME);
            getDriver().findElement(By.linkText("Create a job")).click();
            createNewFreestyleProject(FREESTYLE_PROJECT);
            goToDashboard();
            getDriver().findElement(By.xpath("//*[@id= 'job_" + FOLDER_NAME + "']/td[3]/a")).click();

            Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='job_" + FREESTYLE_PROJECT + "']/td[3]/a/span")).isDisplayed());
        }

        @Test
        public void TestMoveFolder() {
            createNewFolder(FOLDER_NAME);
            goToDashboard();

            createNewFolder(NESTED_FOLDER_NAME);
            goToDashboard();

            getDriver().findElement(By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a")).click();
            getDriver().findElement(By.xpath("//*[@href='/job/Inserted%20Folder/move']")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select/option[2]")).click();
            getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();

            goToDashboard();
            getDriver().findElement(By.xpath("//*[@id= 'job_" + FOLDER_NAME + "']/td[3]/a")).click();

            assertEquals(getDriver().findElement(By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a/span")).getText(), NESTED_FOLDER_NAME);
        }

    }