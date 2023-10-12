package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PilipenkoTest {

    @Test
    public void testTitle() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://tn.by/");

        Assert.assertEquals(driver.getTitle(), "");
        driver.quit();
    }

    @Test
    public void testSearch() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://tn.by/");

        WebElement searchIcon = driver.findElement(By.xpath("//a[@class = 'search']"));
        searchIcon.click();

        WebElement textBox = driver.findElement(By.xpath("//input[@type = 'search']"));
        textBox.sendKeys("календар");

        WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'submit-button']"));
        searchButton.click();

        WebElement title = driver.findElement(By.xpath("//h1[@class = 'sidebar_title2']"));
        Assert.assertEquals(title.getText(), "Вынікі пошуку: “календар”");

        driver.quit();
    }

    @Test
    public void testFilter() throws InterruptedException{
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://tn.by/");

        WebElement catalogButton = driver.findElement(By.xpath("//a[@href = 'https://tn.by/shop/']"));
        catalogButton.click();

        WebElement languageOption = driver.findElement(By.xpath("//div[contains(text(),'Мова')]"));
        languageOption.click();

        WebElement language = driver.findElement(By.xpath("//label[@for = 'filter-checkgroup-id-mova-bielaruskaja']"));
        Thread.sleep(500);
        language.click();

        WebElement filterItem = driver.findElement(By.xpath("//span[@class = 'pc-active-filter__item-text-el']"));
        Assert.assertEquals(filterItem.getText(), "беларуская");

        driver.quit();
    }

}
