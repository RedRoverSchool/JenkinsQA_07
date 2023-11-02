package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder3Test extends BaseTest {

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        returnToJenkinsDashboard();
    }
    private void returnToJenkinsDashboard() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
    }

    @Test
    public void testCreate() {
        final String folderName = "Folder1";

        createFolder(folderName);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@href='job/" + folderName + "/']")).getText(), folderName);
    }

    @Test
    public void testRename() {
        final String folderName = "Folder1";
        final String renamedFolder = "Edited Folder1";

        createFolder(folderName);

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        returnToJenkinsDashboard();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), renamedFolder);
    }

    @Test
    public void testMoveFolderToFolder() {
        createFolder("Main");
        createFolder("Nested");

        getDriver().findElement(By.xpath("//td/a[@href='job/Nested/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Nested/move']")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[@value='/Main']")).click();
        getDriver().findElement(By.name("Submit")).click();
        returnToJenkinsDashboard();

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a/span")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@class='jenkins-table__link model-link inside']")).getText(), "Nested");
    }
}
