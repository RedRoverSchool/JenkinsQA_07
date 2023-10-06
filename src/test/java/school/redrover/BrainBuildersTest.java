package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrainBuildersTest {

    @Test
    public void  testCreatingDoubleRoom() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://automationintesting.online");

            driver.findElement(By.linkText("admin panel")).click();

            if (driver.findElement(By.id("username")).isDisplayed()) {
                WebElement inputName = driver.findElement(By.id("username"));
                WebElement inputPassword = driver.findElement(By.id("password"));
                inputName.click();
                inputName.sendKeys("admin");
                inputPassword.click();
                inputPassword.sendKeys("password");
                driver.findElement(By.id("doLogin")).click();
            }

            Thread.sleep(2000);
            driver.findElement(By.id("roomName")).click();
            driver.findElement(By.id("roomName")).sendKeys("111");
            driver.findElement(By.id("type")).click();
            {
                WebElement dropdown = driver.findElement(By.id("type"));
                dropdown.findElement(By.xpath("//option[. = 'Double']")).click();
            }
            driver.findElement(By.id("accessible")).click();
            {
                WebElement dropdown = driver.findElement(By.id("accessible"));
                dropdown.findElement(By.xpath("//option[. = 'true']")).click();
            }

            driver.findElement(By.id("roomPrice")).click();
            driver.findElement(By.id("roomPrice")).sendKeys("150");
            driver.findElement(By.id("wifiCheckbox")).click();
            driver.findElement(By.id("refreshCheckbox")).click();
            driver.findElement(By.id("tvCheckbox")).click();
            driver.findElement(By.id("safeCheckbox")).click();
            driver.findElement(By.id("createRoom")).click();
            driver.findElement(By.linkText("home page")).click();

            Thread.sleep(5000);
            Assert.assertTrue(driver.getPageSource().contains("Double"));
        } finally {
            driver.quit();
        }
    }
}
