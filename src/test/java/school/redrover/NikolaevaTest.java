package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class NikolaevaTest {
    @Test
    public void  testSearch() throws InterruptedException {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = new EdgeDriver();

        driver.get("https://www.w3schools.com/");

        WebElement textBox = driver.findElement(By.xpath("//input[@id='search2']"));
        textBox.sendKeys("SQL Tutorial");

        Thread.sleep(2100);

        WebElement searchButton = driver.findElement(By.xpath("//button[@id='learntocode_searchbtn']"));
        searchButton.click();

        WebElement title = driver.findElement(By.cssSelector("h1"));
        String value = title.getText();
        Assert.assertEquals(value, "SQL Tutorial");

        driver.quit();
    }


}
