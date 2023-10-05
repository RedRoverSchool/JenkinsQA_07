import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;



public class GroupCoffeeCodersTest {
    public static final String username = "admin";
    public static final String password = "admin";

    @Test
    public void testUserRegistration() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.demoblaze.com/");
        try {
            WebElement submitButton = driver.findElement(By.xpath("//*[@id='signin2']"));
            submitButton.click();
            Thread.sleep(2000);

            WebElement usernameField = driver.findElement(By.xpath("//input[@id='sign-username']"));
            usernameField.sendKeys(username);

            WebElement passwordField = driver.findElement(By.xpath("//input[@id='sign-password']"));
            passwordField.sendKeys(password);

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
            usernameField.sendKeys(username);

            WebElement passwordField = driver.findElement(By.xpath("//input[@id='loginpassword']"));
            passwordField.sendKeys(password);

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Log in')]"));
            loginButton.click();
            Thread.sleep(2000);

            WebElement message = driver.findElement(By.xpath("//a[@id='nameofuser']"));
            String actualText = message.getText();
            String expectedText = "Welcome " + username;
            Assert.assertEquals(actualText, expectedText);


        } finally {
            driver.quit();
        }
    }
}