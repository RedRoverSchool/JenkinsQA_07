package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class DeleteFreestyleProjectTest extends BaseTest {
    private void createFreestyleProject (){

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Test project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

    }

    @Test
    public void testDeleteProject(){

        createFreestyleProject();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'job/Test%20project')]")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]//span[2]")).click();
        getDriver().switchTo().alert().accept();

        final String welcome = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/h1")).getText();

        Assert.assertEquals(welcome,"Welcome to Jenkins!");
    }
}
