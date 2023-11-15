package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Folder8Test extends BaseTest {

    public void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    public void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Ignore
    @Test
    public void testCreate() {

        final String folderName = "NewFolder";
        createFolder(folderName);
        goToJenkinsHomePage();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).getText(),
                folderName);
    }

    @Ignore
    @Test
    public void testMoveFolderToFolder() {

        final String mainFolderName = "Main Folder";
        final String nestedFolderName = "Nested Folder";

        createFolder(mainFolderName);
        goToJenkinsHomePage();
        createFolder(nestedFolderName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//tr[@id = 'job_" + nestedFolderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/Nested%20Folder/move']")).click();
        getDriver().findElement(By.xpath("//div[@class = 'task '][7]")).click();
        getDriver().findElement(By.xpath("//select[@name = 'destination']")).click();
        getDriver().findElement(By.xpath("//option[@value = '/" + mainFolderName + "']")).click();
        getDriver().findElement(By.name("Submit")).click();
        goToJenkinsHomePage();

        List elementList = getDriver().findElements(By.xpath("//table[@id = 'projectstatus']//*[contains(text(), 'Nested Folder')]"));
        Assert.assertTrue(elementList.size() == 0);

        getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).click();
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tr[@id = 'job_" + nestedFolderName + "']//a//span")).getText(),
                nestedFolderName);
    }
}
