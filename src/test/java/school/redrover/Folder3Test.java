package school.redrover;

import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Folder3Test extends BaseTest {
    final String nameFolder = "NewFolder";

    private void createFolder() {

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(nameFolder);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + nameFolder + "/']")).click();

    }

    @Test(description = "Rename Folder")
    public void testRename() {
        String renewNameFolder = "NewFolderTest";

        createFolder();

        getDriver().findElement(By.xpath("//a[@href='/job/" + nameFolder + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(renewNameFolder);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        String actualNameFolder = getDriver().findElement(By.xpath("//div[@id ='main-panel']/h1")).getText();

        Assert.assertEquals(actualNameFolder, renewNameFolder, "Заголовок переименованной не совпадает");
    }

}
