package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AnzhAmazonTest {
    private final String PAGE_URL = "https://www.amazon.com/";

    @Test
    public void testSearch() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(PAGE_URL);

            // Locate the input element by its class attribute
           // WebElement searchBox = driver.findElement(By.className("nav-input"));

            // Locate the input element by its XPath
            WebElement searchBox = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));

            // Perform actions on the search box
            searchBox.sendKeys("Selenium WebDriver");

            // Enter text
            searchBox.submit(); // Submit the form (assuming this triggers the search)

            // Pause the execution for 5 seconds (5000 milliseconds)
            Thread.sleep(2000);

            // After performing actions, you can continue with your test logic
        } finally {
            // Close the WebDriver session in the finally block to ensure it's always closed
            driver.quit();
        }
    }




}





