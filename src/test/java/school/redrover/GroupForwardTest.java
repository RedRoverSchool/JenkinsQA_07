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

    @Test
    public void testToSpanish() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ldoceonline.com/");

        WebElement languageButton = driver.findElement(By.xpath("//span[@class='text']"));
        languageButton.click();
        WebElement spanishButton = driver.findElement(By.xpath("//a[@class='item' and text()='Español latino']"));
        spanishButton.click();

        WebElement title = driver.findElement(By.xpath("//h1[contains(text(),'Bienvenido')]"));
        String value = title.getText();

        Assert.assertEquals(value, "Bienvenido al Longman Dictionary of Contemporary English Online");

        driver.quit();

    }
}

