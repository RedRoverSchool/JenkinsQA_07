package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupSevenTest {
    @Test
    public void YMCA_Test() {

        WebDriver driver = new FirefoxDriver();
        driver.get("https://ymcacapecod.org/");

        WebElement textBox = driver.findElement(By.className("field"));
        WebElement SearchButton = driver.findElement(By.className("submit"));

        textBox.sendKeys("pool");
        SearchButton.click();

        WebElement findelement = driver.findElement(By.xpath("//*[@id=\"folio\"]/nav/ul/li[2]/a"));
        findelement.click();

        WebElement text = driver.findElement(By.xpath("//*[@id=\"content\"]/article/p[4]/strong/a"));
        text.click();

        String value = text.getText();
        Assert.assertEquals( value,"CLICK HERE TO REGISTER ONLINE!");

        driver.quit();
    }
}
