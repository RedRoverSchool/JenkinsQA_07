package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupForwardTest {

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ldoceonline.com/");

        WebElement textBox = driver.findElement(By.className("search_input"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@type='submit']"));

        textBox.sendKeys("readable");
        searchButton.click();
        Thread.sleep(600);
        WebElement titleElement = driver.findElement(By.className("HYPHENATION"));
        String value = titleElement.getText();
        Assert.assertEquals(value, "read‧a‧ble");

        driver.quit();

    }
}

