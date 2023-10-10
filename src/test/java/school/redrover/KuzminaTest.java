package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class KuzminaTest {

    @Test
    public void testParksOpening() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://parks.canada.ca/pn-np");

        String firstTitle = driver.getTitle();
        Assert.assertEquals(firstTitle, "National parks");

        WebElement findANationalParkButton = driver.findElement(By.xpath("//a[normalize-space()='Find a national park']"));
        findANationalParkButton.click();

        String secondTitle = driver.getTitle();
        Assert.assertEquals(secondTitle, "Find a national park");

        driver.quit();
    }
}
