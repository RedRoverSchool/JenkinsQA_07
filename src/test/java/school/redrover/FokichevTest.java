package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FokichevTest extends BaseTest {

    private static final String NEW_ITEM_NAME = "first";

    @Test
    public void testLogin() {

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testLogout() {

        getDriver().findElement(By.xpath("//a[@href='/logout']")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//main[@id='main-panel']//h1")).getText(),
                "Sign in to Jenkins");
    }

    @Test
    public void testCreateNewFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NEW_ITEM_NAME);
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[3]/a")).getText(),
                NEW_ITEM_NAME);
    }

}
