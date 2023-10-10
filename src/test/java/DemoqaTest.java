import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


    public class DemoqaTest {
        @Test

        public void TextBox() {

            WebDriver driver = new ChromeDriver();
            driver.get("https://demoqa.com/text-box");


            String title = driver.getTitle();
            Assert.assertEquals( "DEMOQA",title);


            WebElement textBox = driver.findElement(By.xpath("//*[@id=\"item-0\"]"));
            WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
            textBox.sendKeys("");
            submitButton.click();

            WebElement fullName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
            fullName.sendKeys("Mari");

            WebElement Email = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
            Email.sendKeys("mari@gmail.com");

            WebElement currentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            currentAddress.sendKeys("Teilor");

            WebElement permanentAddress = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
            permanentAddress.sendKeys("Molodejnaia");



            WebElement messageN = driver.findElement(By.xpath("//*[@id=\"name\"]"));
            WebElement message = driver.findElement(By.xpath("//*[@id=\"email\"]"));
            String value = message.getText();

            Assert.assertEquals("Name:Mari", value);
            Assert.assertEquals("Email:mari@gmail.com",value);
            Assert.assertEquals("Current Address :Teilor",value);
            Assert.assertEquals("Permananet Address :Molodejnaia",value);

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
            currentAddress.sendKeys("Traian");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            WebElement permanentAddress = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
            permanentAddress.sendKeys("Pushkina");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
            submitButton.click();


            WebElement messageName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
            WebElement messageEmail = driver.findElement(By.className("//*[@id=\"email\"]"));
            WebElement messageCurrent = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            WebElement messagePermanent = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
            String value = message.getText();

            Assert.assertEquals("Name:Natalia", value);
            Assert.assertEquals("Email:natalia@gmail.com",value);
            Assert.assertEquals("Current Address :Traian",value);
            Assert.assertEquals("Permananet Address :Pushkina",value);


            driver.quit();
        }
    }





