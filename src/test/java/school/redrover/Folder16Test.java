package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder16Test extends BaseTest {
        @Test
        public void testCreateNewFolder() {
            final String nameNewFolder="Java Test";

            getDriver().findElement(By.linkText("Create a job")).click();
            getDriver().findElement(
                    By.xpath("//div/input[@class='jenkins-input']")).sendKeys(nameNewFolder);
            getDriver().findElement(
                    By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]/label")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

            Assert.assertEquals(getDriver().findElement(
                    By.cssSelector("#breadcrumbs > li:nth-child(3) > a")).getText(),nameNewFolder);
        }
}
