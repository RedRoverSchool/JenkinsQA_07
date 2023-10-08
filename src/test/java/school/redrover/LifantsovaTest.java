package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LifantsovaTest {

    @Test
    public void  testSearch() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://resh.edu.ru/");

            Thread.sleep(3000);

            WebElement textBox = driver.findElement(By.id("search"));
            textBox.sendKeys("Математика");

            Thread.sleep(1000);

            WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div/header/div/div/div[2]/form/div/button"));
            searchButton.click();

            WebElement title = driver.findElement(By.className("content-title"));
            String value = title.getText();
            Assert.assertEquals(value, "РЕЗУЛЬТАТЫ ПОИСКА");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void  testSearch1() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://resh.edu.ru/");

            Thread.sleep(3000);

            WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/header/nav/div/div/a[1]"));
            element.click();

            Thread.sleep(1000);

            WebElement title = driver.findElement(By.className("content-title"));
            String value = title.getText();
            Assert.assertEquals(value, "УЧЕБНЫЕ ПРЕДМЕТЫ");
        } finally {
            driver.quit();
        }
    }
}
