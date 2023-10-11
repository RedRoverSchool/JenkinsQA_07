package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.dom.model.ShadowRootType;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class GroupHighwayToAqaTest {

    @Test
    public void testInvalidCreds() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demo.prestashop.com/#/en/front");

        Thread.sleep(11000);

        driver.switchTo().frame("framelive");

        WebElement signInLink = driver.findElement(By.xpath("//span[text()='Sign in']"));

        signInLink.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@autocomplete='email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@aria-label='Password input']"));
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));

        emailInput.sendKeys("test@test.com");
        passwordInput.sendKeys("Qwerty123$");
        submitBtn.click();

        WebElement alertMessage = driver.findElement(By.className("alert-danger"));

        Assert.assertEquals(alertMessage.getText(), "Authentication failed.");

        driver.quit();
    }

    @Test
    public void testCreateAcc() throws InterruptedException {
        String firstName = "firstName";
        String lastName = "LastName";
        String password = "Qwe123!@#ASD";
        String email = "test@mail.com";

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.prestashop.com/#/en/front");

        driver.switchTo().frame("framelive");

        WebElement signInLink = driver.findElement(By.xpath("//span[text()='Sign in']"));
        signInLink.click();

        WebElement createAccLink = driver.findElement(By.xpath("//a[contains(.,'No account? Create one here')]"));
        createAccLink.click();

        WebElement genderMr = driver.findElement(By.xpath("//label[contains(.,'Mrs.')]"));
        genderMr.click();

        WebElement firstnameInput = driver.findElement(By.xpath("//input[@id='field-firstname']"));
        WebElement lastnameInput = driver.findElement(By.xpath("//input[@id='field-lastname']"));
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='field-email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='field-password']"));
        WebElement ifReceiveOffers = driver.findElement(By.xpath("//input[@name='optin']"));
        WebElement ifAgreeTerms = driver.findElement(By.xpath("//input[@name='psgdpr']"));
        WebElement ifGetNewsLetters = driver.findElement(By.xpath("//input[@name='newsletter']"));
        WebElement ifCustomer_privacy = driver.findElement(By.xpath("//input[@name='customer_privacy']"));
        WebElement saveBtn = driver.findElement(By.cssSelector(".form-control-submit"));

        firstnameInput.sendKeys(firstName);
        lastnameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        ifReceiveOffers.click();
        ifAgreeTerms.click();
        ifGetNewsLetters.click();
        ifCustomer_privacy.click();
        saveBtn.click();

        WebElement ifRegistered = driver.findElement(By.cssSelector(".account > .hidden-sm-down"));

        Assert.assertEquals(ifRegistered.getText(), firstName + " " + lastName);

        driver.quit();
    }

}
