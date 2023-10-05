package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupSevenTest {
    @Test
    public void kylieTitleTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://kyliecosmetics.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Kylie Cosmetics by Kylie Jenner | Kylie Skin | Kylie Baby");

        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException{
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://kyliecosmetics.com/collections/kylie-cosmetics");

            Thread.sleep(1000);

            WebElement searchButton = driver.findElement(By.xpath("//a[@title='Search']"));
            searchButton.click();

            WebElement searchInput = driver.findElement(By.xpath("//input[@id='SearchForm-Header-Query']"));
            searchInput.sendKeys("lip kit");

            WebElement searchButtonNext = driver.findElement(By.xpath("//button[@id='SearchForm-Header-Submit']"));
            searchButtonNext.click();

            WebElement title = driver.findElement(By.xpath("//h1[normalize-space()='Search']"));
            String value = title.getText();
            Assert.assertEquals(value, "SEARCH");

        } finally {
            driver.quit();
        }
    }
}
