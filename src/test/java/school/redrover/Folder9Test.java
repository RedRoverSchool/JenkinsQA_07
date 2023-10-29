package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder9Test extends BaseTest {

    @Test
    public void testCreatingNewFolder () {
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("TestFolder");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_TestFolder']/td[3]/a/span")).getText(),
                "TestFolder");
    }

}
