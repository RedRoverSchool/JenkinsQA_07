package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupQaClimbersTest {

    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.get("https://demoqa.com");
            driver.manage().window().maximize();

            driver.findElement(By.xpath("//div[@class='card-up'][1]")).click();
            driver.findElement(By.xpath("//span[ contains(text(), 'Text Box')]")).click();

            WebElement inputName = driver.findElement(By.id("userName"));
            WebElement inputEmail = driver.findElement(By.id("userEmail"));
            WebElement submitButton = driver.findElement(By.id("submit"));

            inputName.sendKeys("Jane Dou");
            inputEmail.sendKeys("example@example.com");
            js.executeScript("arguments[0].scrollIntoView();", submitButton);
            submitButton.click();

            String actualStringName = driver.findElement(By.id("name")).getText();
            String actualEmail = driver.findElement(By.id("email")).getText();

            assertEquals(actualStringName, "Name:Jane Dou");
            assertEquals(actualEmail, "Email:example@example.com");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void widgetPageTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com");
        driver.manage().window().maximize();
        try {
            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[1]")).click();
            driver.findElement(By.xpath("//*[@class='header-text'][1]")).click();
            Thread.sleep(200);
            driver.findElement(By.xpath("//*[@id=\"item-2\"]")).click();
            driver.findElement(By.xpath("//label[@class=\"custom-control-label\"][1]")).click();
            String title = driver.findElement(By.xpath("//p[@class=\"mt-3\"]/span")).getText();
            assertEquals(title, "Yes");
        }finally {
            driver.quit();
        }

    }
}
