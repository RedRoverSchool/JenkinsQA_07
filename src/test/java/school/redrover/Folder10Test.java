package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import school.redrover.runner.BaseTest;
import org.testng.annotations.Test;
public class Folder10Test extends BaseTest {

    @Test
    public void testCreatingANewFolder() {
        final String folder = "Folder_1";

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]//a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folder);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']//button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_Folder_1']/td[3]/a")).getText(), folder);

    }
}
