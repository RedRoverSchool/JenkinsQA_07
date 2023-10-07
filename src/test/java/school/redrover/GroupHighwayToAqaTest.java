package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupHighwayToAqaTest {

    @Test
    public void testInvalidCreds() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demo.prestashop.com/#/en/front");

        Thread.sleep(11000);

        driver.switchTo().frame("framelive");

        WebElement signInLink = driver.findElement(By.xpath("//span[text()='Sign in']"));

        signInLink.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@autocomplete='email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@aria-label='Password input']"));
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));

        emailInput.sendKeys("test@test.com");
        passwordInput.sendKeys("Qwerty123$");
        submitBtn.click();

        WebElement alertMessage = driver.findElement(By.className("alert-danger"));

        Assert.assertEquals(alertMessage.getText(), "Authentication failed.");

        driver.quit();
    }

}
