package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectThTest extends BaseTest {

    public void createProject (){

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Project_1");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']//li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
    }


    @Test
    public void TestRenameFreestyleProject (){

        final String NEW_NAME = "Project_2";

        createProject();
        getDriver().findElement(By.xpath("//*[@id='job_Project_1']/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_NAME);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_Project_2']/td[3]/a/span")).getText(), NEW_NAME);
    }
}