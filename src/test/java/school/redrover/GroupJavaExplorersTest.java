package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupJavaExplorersTest {
    @Test
    public void testSearchWatches() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");

        Thread.sleep(1000);

        WebElement gear = driver.findElement(By.xpath("//a[@id='ui-id-6']/span[2]"));
        gear.click();

        WebElement watches = driver.
                findElement(By.xpath("//*[@id='maincontent']/div[4]/div[2]/div[2]/div/ul/li[3]/a"));
        watches.click();

        WebElement clamberWatch = driver.
                findElement(By.xpath("//*[@id='maincontent']/div[3]/div[1]/div[3]/ol/li[2]/div/div/strong/a"));
        clamberWatch.click();

        WebElement text = driver.findElement(By.xpath("//*[@class='base']"));
        String value = text.getText();

        Assert.assertEquals(value, "Clamber Watch");

        driver.quit();
    }

    @Test
    public void testLoginWithIncorrectData() throws InterruptedException {
        String email = "asdfg@mail.ru";
        String password = "12345";
        String message = "The account sign-in was incorrect or your account is disabled temporarily." +
                " Please wait and try again later.";

        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");

        Thread.sleep(1000);

        WebElement loginIn = driver.
                findElement(By.xpath("//header/div[1]/div/ul/li[2]/a"));
        loginIn.click();

        WebElement textBoxEmail = driver.findElement(By.id("email"));
        textBoxEmail.sendKeys(email);

        WebElement textBoxPassword = driver.findElement(By.id("pass"));
        textBoxPassword.sendKeys(password);

        WebElement submitButton = driver.
                findElement(By.xpath("//fieldset/div[4]/div[1]/button"));
        submitButton.click();

        Thread.sleep(1000);

        String value = driver.
                findElement(By.xpath("//div[contains(text(), 'The account sign-in')]")).
                getText();

        Assert.assertTrue(value.contains(message));

        driver.quit();
    }
    @Test
    public void testSignInNegative() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Home Page");
        WebElement signIn = driver.findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/a"));
        signIn.click();
        WebElement signInto = driver.findElement(By.xpath("//*[@id='send2']/span"));
        signInto.click();
        WebElement field = driver.findElement(By.xpath("//*[@id='email-error']"));
        String failText = field.getText();
        Assert.assertEquals(failText, "This is a required field.");
        WebElement email = driver.findElement(By.xpath("//*[@id='email']"));
        email.sendKeys("abcd@gmail.com");
        WebElement password = driver.findElement(By.xpath("//*[@id='pass']"));
        password.sendKeys("1234");
        signInto.click();
        WebElement accIncorrect = driver.findElement(By.xpath("//*[@id='maincontent']/div[2]/div[2]/div/div/div"));
        String accFailText = accIncorrect.getText();
        Assert.assertEquals(accFailText, "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
        driver.quit();
    }

    @Test
    public void testSearchOlivia() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com");

        WebElement textBox = driver.findElement(By.xpath("//input[@id='search']"));
        textBox.sendKeys("Olivia");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        Thread.sleep(3000);

        String title = driver.findElement(By.xpath("//h1")).getText();

        assertEquals(title, "Search results for: 'Olivia'");

        driver.quit();
    }
}

