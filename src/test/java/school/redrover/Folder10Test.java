package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder10Test extends BaseTest {

    private static final String NEW_ITEM_NAME = "testfolder";

    @Test
    public void testCreate() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NEW_ITEM_NAME);
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_ITEM_NAME + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText(),
                NEW_ITEM_NAME);
    }


}
