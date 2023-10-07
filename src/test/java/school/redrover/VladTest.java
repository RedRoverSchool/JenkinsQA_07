package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VladTest {
    String pass = "_.3JsTMMvjtqzAa";
    String email = "newtestd0tc0m@gmail.com";
    String site = "https://www.ministryoftesting.com/";

@Test
    public void testLogin() {

    WebDriver driver = new ChromeDriver();

    try {
        driver.get(site);
        driver.manage().window().maximize();

        WebElement language = driver.findElement(By.xpath("//*[@id=\"nav-sign-in\"]"));
        language.click();

        WebElement textEmail = driver.findElement(By.xpath("//*[@id=\"user_login\"]"));
        textEmail.sendKeys(email);

        WebElement textPass = driver.findElement(By.xpath("//*[@id=\"user_password\"]"));
        textPass.sendKeys(pass);

        WebElement textLogin = driver.findElement(By.xpath("//*[@id=\"new_user\"]/div[3]/input"));
        textLogin.click();

        WebElement pngAccount = driver.findElement(By.xpath("//*[@id=\"profileDropdown\"]/img"));
        pngAccount.click();

        WebElement textDashboard = driver.findElement(By.xpath("//*[@id=\"myMoT\"]/span"));
        textDashboard.click();

        WebElement textExpected = driver.findElement(By.xpath("/html/body/main/div[3]/section[2]/div/div/div[2]/p"));

        String value = textExpected.getText();
        Assert.assertEquals(value, "Please use the navigation to find the existing sections of your My MoT pages.");
    } finally {
        driver.quit();
    }
}
}
