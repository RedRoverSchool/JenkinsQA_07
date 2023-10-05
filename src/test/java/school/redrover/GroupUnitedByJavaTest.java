package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupUnitedByJavaTest {
    @Test
    public void demoqaElementsRedirection() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            String title = driver.getTitle();
            Assert.assertEquals(title, "DEMOQA");
            WebElement elementsButton = driver.findElement(By.cssSelector(".top-card:nth-child(1)"));
            elementsButton.click();
            String currentUrl = driver.getCurrentUrl();
            String elementsUrl = "https://demoqa.com/elements";
            Assert.assertEquals(currentUrl, elementsUrl);
            Thread.sleep(2000);
        } finally {
            driver.quit();
        }
    }
}
