package old;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class AnzhAmazonTest {
    private final String PAGE_URL = "https://www.amazon.com/";

    @Test
    public void testSearch() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(PAGE_URL);

            WebElement searchBox = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));

            searchBox.sendKeys("Selenium WebDriver");


            searchBox.submit();


            Thread.sleep(2000);


        } finally {
           driver.quit();
        }
    }




}





