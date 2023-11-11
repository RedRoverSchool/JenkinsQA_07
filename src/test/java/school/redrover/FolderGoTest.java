package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
public class FolderGoTest extends BaseTest {
    @Test
   public void testCreateNewFolder() {
        final String folder_Name="Java Test";

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(
                By.xpath("//div/input[@class='jenkins-input']")).sendKeys(folder_Name);
        getDriver().findElement(By.cssSelector("#j-add-item-type-nested-projects ")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.className("bottom-sticker-inner--stuck")).click();
        Assert.assertEquals(getDriver().findElement(

                By.cssSelector("#breadcrumbs > li:nth-child(3) > a")).getText(),folder_Name);
    }

}



