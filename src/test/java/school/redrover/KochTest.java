package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class KochTest {
    @Test
    public void  testMyAgent() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://myagent.online/?#/");

        WebElement searchButton = driver.findElement(By.className("Button__Text"));
        searchButton.click();

        Thread.sleep(5100);

        System.out.println("Ciao, ciao");

        driver.quit();
    }
}




