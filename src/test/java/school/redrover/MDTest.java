package school.redrover;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MDTest {

    @Test
    public void testSearch_MD() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");

        WebElement textBox = driver.findElement(By.className("gLFyf"));
        textBox.sendKeys("Selenium");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.className("gNO89b"));
        searchButton.click();

        WebElement title = driver.findElement(By.xpath("//div/span[@class = \"VuuXrf\"]"));
        String value = title.getText();
        Assert.assertEquals("Selenium", value);

        driver.quit();
    }
}
