package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupUnicornsTest {

    @Test
    public void usPsPageOpenTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        String title = driver.getTitle();
        assertEquals("Welcome | USPS", title);
        driver.quit();
    }

    @Test
    public void usPsSendMailPackageTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        WebElement send = driver.findElement(By.xpath("//a[@id='mail-ship-width']"));
        send.click();
        String sendTitle = driver.getTitle();
        assertEquals("Send Mail & Packages | USPS", sendTitle);
        driver.quit();
    }
}