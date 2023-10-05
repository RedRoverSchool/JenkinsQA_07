
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

    public class PlusThreeTest {
        public static final String USERNAME = "TestUser1";
        @Test(description = "Создание/регистрация пользователя в банке")
        public void createUser() {

            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://parabank.parasoft.com/parabank/register.htm");

            WebElement firstName = driver.findElement(By.id("customer.firstName"));
            WebElement lastName = driver.findElement(By.id("customer.lastName"));
            WebElement address = driver.findElement(By.id("customer.address.street"));
            WebElement city = driver.findElement(By.id("customer.address.city"));
            WebElement state = driver.findElement(By.id("customer.address.state"));
            WebElement zipCode = driver.findElement(By.id("customer.address.zipCode"));
            WebElement userName = driver.findElement(By.id("customer.username"));
            WebElement password = driver.findElement(By.id("customer.password"));
            WebElement prepaerPass = driver.findElement(By.id("repeatedPassword"));
            WebElement ssn = driver.findElement(By.id("customer.ssn"));
            WebElement register = driver.findElement(By.cssSelector("[value='Register']"));

            firstName.sendKeys("Test");
            lastName.sendKeys("Test");
            address.sendKeys("SPB");
            city.sendKeys("SPB");
            state.sendKeys("LO");
            zipCode.sendKeys("123-415");
            userName.sendKeys(USERNAME);
            password.sendKeys("qwert12345");
            prepaerPass.sendKeys("qwert12345");
            ssn.sendKeys("123456");
            register.submit();

            WebElement title = driver.findElement(By.xpath("//div[@id='rightPanel']/h1"));
            String resTitle = title.getText();
            WebElement result = driver.findElement(By.xpath("//div[@id='rightPanel']/p"));
            String resText = result.getText();

            Assert.assertEquals(resTitle,"Welcome " + USERNAME);
            Assert.assertEquals(resText, "Your account was created successfully. You are now logged in.");

            driver.quit();
        }
    }
