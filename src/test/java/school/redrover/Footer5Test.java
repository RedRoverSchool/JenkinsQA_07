package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Footer5Test extends BaseTest {

    @Test
    public void testClickRestApi () {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testClickRestApi")
    public void testRemoteApi() {
        List<By> menuItems = List.of(
            By.xpath("//a[@href='/asynchPeople/']"),
            By.xpath("//a[@href='/view/all/builds']"),
            By.xpath("//a[@href='/me/my-views']"));

        for (By locator : menuItems) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.linkText("REST API")).click();

            Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
            Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
            getDriver().navigate().back();
        }
    }
}