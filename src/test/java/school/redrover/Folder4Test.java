package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Folder4Test extends BaseTest {

    @Test
    public void testMoveFolderToFolder() {
        final String folder1Name = "MainFolder";
        final String folder2Name = "NestedFolder";

        createFolder(folder1Name);
        navigateToDashboard();

        createFolder(folder2Name);
        getDriver().findElement(By.xpath("//a[contains(@href, 'move')]")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath(String.format("//*[@id='main-panel']//option[@value='/%s']", folder1Name))).click();
        getDriver().findElement(By.name("Submit")).click();

        navigateToDashboard();

        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", folder1Name))).click();

        String nestedFolders = getDriver().findElement(By.xpath("//table[@id='projectstatus']")).getText();
        assertTrue(nestedFolders.contains(folder2Name));
    }

    @Test
    public void testCreateJobInsideFolder() {
        final String folderName = "NewFolder";
        final String jobName = "NewProject";

        createFolder(folderName);

        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/newJob']",folderName))).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        navigateToDashboard();
        getDriver().findElement(By.xpath(String.format("//*[@id='job_%s']/td[3]/a",folderName))).click();

        String jobNameInFolder = getDriver().findElement((By.xpath("//table[@id='projectstatus']//td[3]"))).getText();
        assertEquals(jobNameInFolder, jobName);
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void navigateToDashboard() {
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']//a[@href='/']")).click();
    }
}
