import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

public class DemoqaTest {
    @Test

    public void TextBoxName() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");


        String title = driver.getTitle();
        Assert.assertEquals( "DEMOQA",title);


        WebElement textBoxName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));

        textBoxName.sendKeys("Nadejda");
        submitButton.click();

        /*WebElement fullName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        fullName.sendKeys("Mari");
        WebElement Email = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
        Email.sendKeys("mari@gmail.com");
        WebElement currentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        currentAddress.sendKeys("Teilor");
        WebElement permanentAddress = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
        permanentAddress.sendKeys("Molodejnaia");*/

        WebElement messageName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
        String value = messageName.getText();
        Assert.assertEquals("Name:Nadejda", value);


        driver.quit();
    }

        @Test

        public void TextBox1 () {

            WebDriver driver = new ChromeDriver();
            driver.get("https://demoqa.com/text-box");



            WebElement fullName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
            fullName.sendKeys("Natalia");


            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));


            WebElement Email = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
            Email.sendKeys("natalia@gmail.com");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            WebElement currentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            currentAddress.sendKeys("Sciastlivaia");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            WebElement permanentAddress = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
            permanentAddress.sendKeys("Udacia");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
            submitButton.click();


            WebElement messageName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
            String value = messageName.getText();
            Assert.assertEquals("Name:Natalia", value);

            WebElement messageEmail = driver.findElement(By.className("//*[@id=\"email\"]"));
            String valueE = messageEmail.getText();
            Assert.assertEquals("Email:natalia@gmail.com",value);

            WebElement messageCurrent = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            String valueMC= messageCurrent.getText();
            Assert.assertEquals("Current Address :Sciastlivaia", value);


            WebElement messagePM= driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
            String valueMP = messagePM.getText();
            Assert.assertEquals("Permananet Address :Udacia",value);




            driver.quit();
        }
    }









