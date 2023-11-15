package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Footer4Test extends BaseTest {

    @Test
    public void testClickRestApi() {

        getDriver().findElement(By.xpath("//a[@class='jenkins-button jenkins-button--tertiary rest-api']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testClickRestApi")
    public void testRestApiLinkRedirectionMainMenu() {
        List<By> pages = List.of(
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[5]/span/a"));

        for (By locator : pages) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.linkText("REST API")).click();

            Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
            Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
            getDriver().navigate().back();
        }
    }
}