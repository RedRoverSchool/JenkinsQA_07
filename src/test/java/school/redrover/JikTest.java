package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JikTest extends BaseTest {

    @Test
    public void testTitleCheck() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            String title = driver.getTitle();
            Assert.assertEquals(title, "Web form");
        } finally {
            driver.quit();
        }
    }

    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void testTextInput() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement nameInput = driver.findElement(By.id("my-text-id"));
            nameInput.sendKeys("Jika");

            String enteredText = nameInput.getAttribute("value");
            Assert.assertEquals(enteredText, "Jika");
        } finally {
            driver.quit();
        }
    }
}