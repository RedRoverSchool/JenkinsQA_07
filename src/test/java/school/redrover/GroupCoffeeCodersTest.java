package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;



@Ignore
public class GroupCoffeeCodersTest {
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

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
        WebDriver driver = new ChromeDriver();
        driver.get("https://apteka.ru/");

        WebElement button = driver.findElement(By
                .xpath("//*[@href ='/svg/sprite.3442412f5a404aaae343385556021881.svg#close' ]"));
        button.click();
        WebElement textBox = driver.findElement(By.xpath("//input[@type='search']"));
        textBox.sendKeys("анальгин");

        WebElement buttonSearch = driver.findElement(By.xpath("//*[text()='Искать']"));
        buttonSearch.click();

        Thread.sleep(2000);

        WebElement title = driver.findElement(By.xpath("//span[@class='SearchResultTitle__found']"));
        String value = title.getText();
        Assert.assertEquals(value,"Результаты по запросу «анальгин» 15 товаров");

        driver.quit();

    }
}
