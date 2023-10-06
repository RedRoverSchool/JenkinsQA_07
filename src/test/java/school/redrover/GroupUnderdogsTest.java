package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupUnderdogsTest {

    WebDriver driver;

    String userName = "academic198405@gmail.com";
    String password = "BikeTrekMarlyn4!";
    String wrongPassword = "Sbbhbhbln2";
    String baseUrl = "https://www.trekbikes.com/us/en_US/";

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


    //text written in lower case and color red
    @Test
    public void maksinTestInactive() {
        driver = new FirefoxDriver();
        driver.get("http://www.99-bottles-of-beer.net/team.html");
        WebElement text = driver.findElement(By.xpath
                ("/html/body/div/div[3]/p[7]/font/b"));
        Assert.assertTrue(text.getText().toLowerCase().equals(text.getText()));
        Assert.assertEquals(text.getCssValue("color"), "rgb(255, 0, 0)");
    }

    @Test
    public void artuomTrack_correct_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(password);
        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);


        WebElement isAllNewArrivals = driver.findElement(By.xpath("(//*[@class=\"pdl-heading pdl-heading--xl \"])[1]"));

        String text = isAllNewArrivals.getText();
        Assert.assertEquals(text, "All-new arrivals");


    }

    @Test
    public void artuomTrack_Incorrect_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(wrongPassword);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);


        WebElement incorrectUser= driver.findElement(By.xpath("//*[text()='Incorrect username or password']"));

        String text = incorrectUser.getText();
        Assert.assertEquals(text, "Incorrect username or password");


    }
    @Test
    public void artuomMarlin4Test() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        WebElement mount = driver.findElement(By.xpath("//*[@id=\"expandMountainBikesMainMenu-compact\"]"));
        mount.click();
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement marlin = driver.findElement(By.xpath("(//*[text()='Marlin'])[1]"));
        js.executeScript("arguments[0].scrollIntoView();", marlin);
        marlin.click();
        Thread.sleep(1000);

        WebElement seeTheBikes = driver.findElement(By.xpath("//*[@title=\"SEE THE BIKES\"]"));
        seeTheBikes.click();
        Thread.sleep(2000);

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        WebElement marlin4 = driver.findElement(By.xpath("//*[text()='Marlin 4 Gen 2']"));
        js.executeScript("arguments[0].scrollIntoView();", marlin4);
        Thread.sleep(2000);

        String bikeName =  marlin4.getText();
        Assert.assertEquals(bikeName, "Marlin 4 Gen 2");
    }
}
