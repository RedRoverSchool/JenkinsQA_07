package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OlGavrikovTest {

    @Test
    public void testSite() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");

        String titleLoginPage = driver.getTitle();
        Assert.assertEquals(titleLoginPage, "The Internet");

        WebElement firstClick = driver.findElement(By.xpath("//a[@href=\"/basic_auth\"]"));
        firstClick.click();

        driver.quit();
    }

    @Test
    public void testSite2() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");

        String titleLoginPage = driver.getTitle();
        Assert.assertEquals(titleLoginPage, "The Internet");

        WebElement secondClick = driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[11]/a"));
        secondClick.click();

        driver.quit();
    }

    @Test
    public void testSite3() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");

        String titleLoginPage = driver.getTitle();
        Assert.assertEquals(titleLoginPage, "The Internet");

        WebElement thirdClick = driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[7]/a"));
        thirdClick.click();

        driver.quit();
    }


}

