package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LashkoAmazonTest {
    @Test
    public void testSearch() throws InterruptedException {
        String PAGE_URL = "https://www.amazon.com/";

        WebDriver driver = new ChromeDriver();

        try {
            driver.get(PAGE_URL);

            WebElement searchBox = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));

            searchBox.sendKeys("Selenium WebDriver");

            searchBox.submit();

            Thread.sleep(2000);

            WebElement titleElement = driver.findElement(By.xpath("//span[contains(text(), 'Results')]"));
            String value = titleElement.getText();

            Assert.assertTrue(value.contains("Results"));


        } finally {
           driver.quit();
        }
    }

}





