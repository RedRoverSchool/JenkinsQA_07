package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JikTest {

    @Test
    public void titleCheck() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");
            Thread.sleep(1000);
            String title = driver.getTitle();
            Assert.assertEquals(title, "Web form");
        } finally {
            driver.quit();
        }
    }

    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void textInputTest() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement nameInput = driver.findElement(By.id("my-text-id"));
            nameInput.sendKeys("Artur Sabanadze");

            String enteredText = nameInput.getAttribute("value");
            Assert.assertEquals(enteredText, "Artur Sabanadze");
        } finally {
            driver.quit();
        }
    }
}