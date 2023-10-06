package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}

