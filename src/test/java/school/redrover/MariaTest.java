package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

    public class MariaTest {

        @Test
        public void testSearchMD() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            try {
                driver.get("https://www.selenium.dev/selenium/web/web-form.html");

                String title = driver.getTitle();
                Assert.assertEquals("Web form", title);

                Thread.sleep((7000));

                WebElement textBox = driver.findElement(By.name("my-text"));
                WebElement submitButton = driver.findElement(By.cssSelector("button"));

                textBox.sendKeys("Selenium");
                submitButton.click();

                WebElement message = driver.findElement(By.id("message"));
                String value = message.getText();
                Assert.assertEquals("Received!", value);
            } finally {
                driver.quit();
            }
        }
    }

