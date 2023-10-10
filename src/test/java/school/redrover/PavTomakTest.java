package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PavTomakTest {


    @Test
    public void testTypeIn() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://ymcacapecod.org/");


        WebElement textBox = driver.findElement(By.className("field"));
        textBox.sendKeys("Eastham");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.className("submit"));
        searchButton.click();

        WebElement title = driver.findElement(By.className("breadcrumb"));
        String value = title.getText();
        Assert.assertEquals(value, "You Are Here: YMCA Cape Cod > Search results for 'Eastham'");

        driver.quit();

    }

    @Test
    public void testMacsShack() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://macsseafood.com/restaurant/macs-shack-wellfleet/");

        WebElement viewMenu = driver.findElement(By.xpath("//*[@id='menu-link']/span[2]"));
        viewMenu.click();

        WebElement orderOnline = driver.findElement(By.xpath("//*[text() = 'Order Online']"));
        orderOnline.click();

        WebElement macsFishHouse = driver.findElement(By.xpath("//main//a[text() = 'Mac’s Fish House Provincetown']"));
        Assert.assertTrue(macsFishHouse.isDisplayed());

        driver.quit();


    }
}


