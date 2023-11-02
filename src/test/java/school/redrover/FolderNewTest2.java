package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderNewTest2 extends BaseTest {

    @Test
    public void createFolder1() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("HSE");
        getDriver().findElement(By.xpath("//*[text()='Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();
        getDriver().findElement(By.xpath("//li//a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td//a//*[text()='HSE']")).getText(), "HSE");
    }
}