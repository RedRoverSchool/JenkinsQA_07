package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testReturnWithLogo() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }
    @Test
    public void testClickLogoToMainPage() {

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText().contains("Welcome to Jenkins!"));
    }
}
