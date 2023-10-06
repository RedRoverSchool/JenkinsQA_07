package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IronTeamGroupTest {
    @Test
    public void javaPageTest() throws InterruptedException {
        // Check Java page of site
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.w3schools.com/");
            WebElement textBox = driver.findElement(By.id("search2"));
            textBox.sendKeys("Java Tutorial");
            WebElement searchButton = driver.findElement(By.id("learntocode_searchbtn"));
            Thread.sleep(500);
            searchButton.click();
           WebElement title = driver.findElement(By.cssSelector("h1"));
            String value = title.getText();
           Assert.assertEquals(value, "Java Tutorial");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testCustomerLogin() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
            Thread.sleep(1000);
            WebElement loginButton = driver.findElement(By.cssSelector("[ng-click='customer()']"));
            loginButton.click();
            Thread.sleep(1000);
            driver.findElement(By.id("userSelect")).click();
            driver.findElement(By.cssSelector("[value='2']")).click();
            driver.findElement(By.cssSelector("[type='submit']")).click();
            Thread.sleep(1000);

            WebElement result = driver.findElement(By.xpath("//span[@class='fontBig ng-binding']/parent::strong"));
            Assert.assertEquals(result.getText(), "Welcome Harry Potter !!");
        } finally {
            driver.quit();
        }
    }
}
