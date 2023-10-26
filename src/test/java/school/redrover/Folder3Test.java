package school.redrover;

import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Folder3Test extends BaseTest {
    private final String NAMEFOLDER = "NewFolder";

    private final String RENAMEFOLDER = "NewFolderTest";

    private void createFolder(String newName) {

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(newName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + newName + "/']")).click();

    }

    @Test(description = "Rename Folder")
    public void testRename() {

        createFolder(NAMEFOLDER);

        getDriver().findElement(By.xpath("//a[@href='/job/" + NAMEFOLDER + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(RENAMEFOLDER);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        String actualNameFolder = getDriver().findElement(By.xpath("//div[@id ='main-panel']/h1")).getText();

        Assert.assertEquals(actualNameFolder, RENAMEFOLDER, "Заголовок переименованной не совпадает");
    }

}
