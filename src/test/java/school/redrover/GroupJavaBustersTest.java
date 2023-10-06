package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupJavaBustersTest {

    @Test
    public void testMovieSearch() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://letterboxd.com");

        WebElement searchField = driver.findElement(By.xpath("//input[@id='search-q']"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@class='action']"));

        searchField.sendKeys("Merry Christmas, Mr. Lawrence");
        submitButton.click();

        WebElement movie = driver.findElement(By.xpath("//span[@class='film-title-wrapper']/a[contains(@href, 'lawrence')]"));
        String value = movie.getText();
        Assert.assertEquals("Merry Christmas, Mr. Lawrence", value);

        driver.quit();
    }

    @Test
    public void testSignInWithEmptyFields() {

        String fieldValue = "";

        WebDriver driver = new ChromeDriver();
        driver.get("https://letterboxd.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement signInButton = driver.findElement(By.xpath("//a[@href='/sign-in/']"));
        signInButton.click();

        WebElement usernameForm = driver.findElement(By.xpath("//input[@id='username']"));
        WebElement passwordForm = driver.findElement(By.xpath("//input[@id='password']"));
        WebElement signInButtonForm = driver.findElement(By.xpath("//input[@class='button -action button-green']"));

        usernameForm.sendKeys(fieldValue);
        passwordForm.sendKeys(fieldValue);
        signInButtonForm.click();

        WebElement message = driver.findElement(By.xpath("//div[@class='errormessage']//p"));
        String value = message.getText();
        Assert.assertEquals("Your credentials don’t match. It’s probably attributable to human error.", value);

        driver.quit();
    }

    @Test
    public void testFillUserName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@value='submit']"));

        fieldUsername.sendKeys("Evgeniia");
        submitButton.click();

        WebElement message = driver.findElement(By.id("_valueusername"));
        String value = message.getText();
        assertEquals(value,"Evgeniia");

        driver.quit();
    }

    @Test
    public void testCancelFillingUserName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = driver.findElement(By.name("username"));
        WebElement cancelButton = driver.findElement(By.xpath("//*[@value='cancel']"));

        fieldUsername.sendKeys("Evgeniia");
        cancelButton.click();

        String value = fieldUsername.getText();
        assertEquals(value,"");

        driver.quit();
    }

}
