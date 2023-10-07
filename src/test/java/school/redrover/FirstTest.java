package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class FirstTest {

    @Test
    public void eightComponents() {

        WebDriver driver = new FirefoxDriver();
        driver.get("https://ymcacapecod.org/");

//        String title = driver.getTitle();
//        Assert.assertEquals( title,"Свежие сборники песен в формате и MP3 и FLAC торрентом");

        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.className("field"));
        WebElement SearchButton = driver.findElement(By.className("submit"));

        textBox.sendKeys("pool");
        SearchButton.click();

        WebElement findelement = driver.findElement(By.xpath("//*[@id=\"folio\"]/nav/ul/li[2]/a"));
        findelement.click();
//        WebElement element = driver.findElement(By.className("button"));
//        element.click();
//
//        WebElement text = driver.findElement(By.className("chooseMembership__membership-col"));
        WebElement text = driver.findElement(By.xpath("//*[@id=\"content\"]/article/p[4]/strong/a"));
        text.click();

        String value = text.getText();
        Assert.assertEquals( value,"CLICK HERE TO REGISTER ONLINE!");

        driver.quit();
    }

}
