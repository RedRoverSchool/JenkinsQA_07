package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import school.redrover.runner.BaseTest;
import org.testng.annotations.Test;

public class Folder7Test extends BaseTest {
    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//img[@class='icon-folder icon-xlg']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test
    public void testMoveFolder () {
        final String nameSecondFolder = "Folder2";

        createFolder("Folder1");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        createFolder(nameSecondFolder);
        getDriver().findElement(By.xpath("//a[@href='/job/Folder2/move']")).click();
        getDriver().findElement(By.xpath("//option[@value='/Folder1']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();


        getDriver().findElement(By.xpath("//li[@class='children']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/']")).click();

        getDriver().findElement(By.xpath("//ol[@class='jenkins-breadcrumbs__list']//li[4]")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/job/Folder1/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).getText(),
                nameSecondFolder);
    }
}
