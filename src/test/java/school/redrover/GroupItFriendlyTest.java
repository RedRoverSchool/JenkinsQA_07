package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

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

}
