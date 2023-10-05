package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GroupItFriendlyTest {

    @Test
    public void testDemoQaOpenPage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement image = driver.findElement(By.xpath("//*[@id=\"app\"]/header/a/img"));
            image.click();
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testDemoQaChangePage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement element = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
            element.click();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement text = driver.findElement(By.xpath("//*[.='Please select an item from left to start practice.']"));
            String value = text.getText();
            Assert.assertEquals(value, "Please select an item from left to start practice.");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testDemoQaTextBox() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/elements");
            WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Text Box')]"));
            element.click();
            WebElement fullNameField = driver.findElement(By.xpath("//*[@id='userName']"));
            fullNameField.click();
            fullNameField.sendKeys("Adam Adams");
        } finally {
          driver.quit();
        }
    }
    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://so-yummi-qa.netlify.app/register");
        String randomUsername = "Test" + UUID.randomUUID().toString().substring(0, 8);
        String randomEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";



        Thread.sleep(1000);
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.click();
        usernameInput.sendKeys(randomUsername);
        WebElement emailInput = driver.findElement(By.id("emailInput"));
        emailInput.click();
        emailInput.sendKeys(randomEmail);
        WebElement passwordInput = driver.findElement(By.id("passwordInput"));
        passwordInput.click();
        passwordInput.sendKeys("Test@123456");


        WebElement searchButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton2.click();

        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://so-yummi-qa.netlify.app/home";
        Assert.assertEquals(currentUrl, expectedUrl, "The current URL does not match the expected URL.");

        driver.quit();
    }

}
