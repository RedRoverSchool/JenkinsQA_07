package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Footer2Test extends BaseTest {

    @Test
    public void testRestApiClickability(){
        getDriver().findElement(By.linkText("REST API")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='REST API']")).isDisplayed());
    }

    @Test
    public void testRestApiLinkRedirectionMainMenu() {
        List<By> pages = List.of(
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[5]/span/a")
        );
        for (By locator : pages) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.linkText("REST API")).click();
            Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='REST API']")).isDisplayed());
            String currentURL = getDriver().getCurrentUrl();
            Assert.assertTrue(currentURL.contains("api"));
            getDriver().navigate().back();
        }
    }
   @Test
   public void testRestApiLinkRedirectionUserArea() {
        List <By> userPages = List.of(
                By.xpath("//*[@id='page-header']/div[3]/a[1]/span"),
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[4]/span/a"),
                By.xpath("//*[@id='tasks']/div[5]/span/a"),
                By.xpath("//*[@id='tasks']/div[6]/span/a")
        );
       for (By locator : userPages) {
           getDriver().findElement(locator).click();
           getDriver().findElement(By.linkText("REST API")).click();
           Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='REST API']")).isDisplayed());
           String currentURL = getDriver().getCurrentUrl();
           Assert.assertTrue(currentURL.contains("api"));
           getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[1]/span")).click();
       }
   }
}
