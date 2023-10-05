package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupUnderdogsTest {

    WebDriver driver;

//    @BeforeMethod
//    public void before() {
//        driver = new FirefoxDriver();
//    }

    @AfterMethod
    public void after() {
        driver.quit();
    }

    @Test
    public void tereshenkov99BottlesTitleTest() {
        driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement title = driver.findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String titleValue = title.getText();

        Assert.assertEquals(titleValue, "99 Bottles of Beer");

    }

    @Test
    public void tereshenkov99BottlesLastMenuLinkTestGetAttribute() {
    driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement lastMenuLink = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));

        String lastMenuLinkValue = lastMenuLink.getAttribute("textContent");
        Assert.assertEquals(lastMenuLinkValue, "Submit new Language");

    }

    @Test
    public void tereshenkov99BottlesLastMenuLinkTestGetText() {
        driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement lastMenuLink = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));

         String lastMenuLinkValue = lastMenuLink.getText();
         Assert.assertEquals(lastMenuLinkValue, "SUBMIT NEW LANGUAGE");

    }


    //TC_11_07 что надпись об исключении красным цветом и с маленькой буквы
    @Test
    public void maksinTestInactive() {
        driver = new FirefoxDriver();
        driver.get("http://www.99-bottles-of-beer.net/team.html");
        WebElement header = driver.findElement(By.xpath
                ("/html/body/div/div[3]/p[7]/font/b"));
        //Assert.assertTrue(header.getText().toLowerCase().equals(header.getText())); //одинаковые
        Assert.assertEquals(header.getText(), header.getText().toLowerCase());     //одинаковые
        Assert.assertEquals(header.getCssValue("color"), "rgb(255, 0, 0)");
    }




}
