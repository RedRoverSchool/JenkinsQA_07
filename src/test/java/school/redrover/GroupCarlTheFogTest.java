package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupCarlTheFogTest {
    @Test
    public void hireRightTest() {


        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Employment Background Checks, Background Screening | HireRight");

        WebElement cacheButton = driver.findElement(By.xpath("//div[@class='CookieConsent']//button[contains(text(), 'Continue')]"));

        cacheButton.click();
        driver.quit();

    }

    @Test
    public void registerNowDisplayTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hireright.com");
        String expectedText = "Register Now";
        String registerNow= "//a[@class = 'btn btn--primary btn--hover-red-dark btn-active-red-darker'][contains(text(),'Register Now')]";
        WebElement registerNowBTN = driver.findElement(By.xpath(registerNow));
        registerNowBTN.getText();

        Assert.assertEquals(registerNowBTN.getText(), expectedText);

        driver.quit();

    }
}
