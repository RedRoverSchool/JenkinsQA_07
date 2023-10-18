package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


@Ignore
public class GroupCoffeeCodersTest extends BaseTest {
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    @Ignore
    @Test
    public void testUserRegistration() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.demoblaze.com/");
        try {
            WebElement submitButton = driver.findElement(By.xpath("//*[@id='signin2']"));
            submitButton.click();
            Thread.sleep(2000);

            WebElement usernameField = driver.findElement(By.xpath("//input[@id='sign-username']"));
            usernameField.sendKeys(USERNAME);

            WebElement passwordField = driver.findElement(By.xpath("//input[@id='sign-password']"));
            passwordField.sendKeys(PASSWORD);

            WebElement signUpButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign up')]"));
            signUpButton.click();

        } finally {
            driver.quit();
        }
    }

    @Ignore
    @Test
    public void testUserLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.demoblaze.com/");
        try {
            WebElement submitButton = driver.findElement(By.xpath("//*[@id='login2']"));
            submitButton.click();
            Thread.sleep(2000);
            WebElement usernameField = driver.findElement(By.xpath("//input[@id='loginusername']"));
            usernameField.sendKeys(USERNAME);

            WebElement passwordField = driver.findElement(By.xpath("//input[@id='loginpassword']"));
            passwordField.sendKeys(PASSWORD);

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Log in')]"));
            loginButton.click();
            Thread.sleep(2000);

            WebElement message = driver.findElement(By.xpath("//a[@id='nameofuser']"));
            String actualText = message.getText();
            String expectedText = "Welcome " + USERNAME;
            Assert.assertEquals(actualText, expectedText);


        } finally {
            driver.quit();
        }
    }

    @Test
    public void testApteka() throws InterruptedException {
        getDriver().get("https://apteka.ru/");

        WebElement button = getDriver().findElement(By
                .xpath("//*[@href ='/svg/sprite.3442412f5a404aaae343385556021881.svg#close' ]"));
        button.click();
        WebElement textBox = getDriver().findElement(By.xpath("//input[@type='search']"));
        textBox.sendKeys("анальгин");

        WebElement buttonSearch = getDriver().findElement(By.xpath("//*[text()='Искать']"));
        buttonSearch.click();

        Thread.sleep(2000);

        WebElement title = getDriver().findElement(By.xpath("//span[@class='SearchResultTitle__found']"));
        String value = title.getText();
        Assert.assertEquals(value,"Результаты по запросу «анальгин» 16 товаров");

    }


    @Ignore
    @Test
    public void testSearch1()  {
        getDriver().get("https://www.labirint.ru/");
        WebElement searchBook = getDriver().findElement(By.className("b-header-b-search-e-input"));
        searchBook.sendKeys("война  и  мир");
        WebElement searchButton = getDriver().findElement(By.className("b-header-b-search-e-btn"));
        searchButton.click();
        WebElement FirstBook = getDriver().findElement(By.xpath("//*[@id=\"rubric-tab\"]/div[3]/section/div/div[1]/a[1]"));
        String value = FirstBook.getText();
        Assert.assertEquals(value, "Война и мир. В 4-х томах.");
    }







    @Test
    public void  testSorting () throws InterruptedException {
        getDriver().get("https://www.labirint.ru/");
        WebElement searchBook = getDriver().findElement(By.className("b-header-b-search-e-input"));
        searchBook.sendKeys("война  и  мир");
        WebElement searchButton = getDriver().findElement(By.className("b-header-b-search-e-btn"));
        searchButton.click();
        WebElement sorting = getDriver().findElement(By.xpath("//*[@id=\"catalog-navigation\"]/form/div[1]/div[1]/div/div/span[7]/span/span/span[1]/span"));
        sorting.click();
        WebElement LowPrice = getDriver().findElement(By.xpath("//*[@id=\"catalog-navigation\"]/form/div[1]/div[1]/div/div/span[7]/span/span/span[2]/ul/li[5]/a"));
        LowPrice.click();
        Thread.sleep(2000);
        WebElement CheapestBook = getDriver().findElement(By.xpath("//*[@id=\"rubric-tab\"]/div[3]/section/div/div[1]/div[2]/div[1]"));
        String value = CheapestBook.getText();
        Assert.assertEquals(value, "73 ₽");

    }


}
