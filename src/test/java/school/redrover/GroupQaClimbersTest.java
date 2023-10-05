package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupQaClimbersTest {

    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.get("https://demoqa.com");
            driver.manage().window().maximize();

            driver.findElement(By.xpath("//div[@class='card-up'][1]")).click();
            driver.findElement(By.xpath("//span[ contains(text(), 'Text Box')]")).click();

            WebElement inputName = driver.findElement(By.id("userName"));
            WebElement inputEmail = driver.findElement(By.id("userEmail"));
            WebElement submitButton = driver.findElement(By.id("submit"));

            inputName.sendKeys("Jane Dou");
            inputEmail.sendKeys("example@example.com");
            js.executeScript("arguments[0].scrollIntoView();", submitButton);
            submitButton.click();

            String actualStringName = driver.findElement(By.id("name")).getText();
            String actualEmail = driver.findElement(By.id("email")).getText();

            assertEquals(actualStringName, "Name:Jane Dou");
            assertEquals(actualEmail, "Email:example@example.com");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testTextBox1(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        WebElement elements = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        elements.click();

        WebElement textBox = driver.findElement(By.xpath("//li[@id=\"item-0\"]/span[text()='Text Box']"));
        textBox.click();

        WebElement fullNameField = driver.findElement(By.xpath("//input[@placeholder=\"Full Name\"]"));
        fullNameField.sendKeys("Arailym");

        WebElement emailField = driver.findElement(By.xpath("//input[@placeholder=\"name@example.com\"]"));
        emailField.sendKeys("test@test.com");

        WebElement currentAddressField = driver.findElement(By.xpath("//textarea[@placeholder=\"Current Address\"]"));
        currentAddressField.sendKeys("050000, Almaty");

        WebElement permanentAddressField = driver.findElement(By.xpath("//textarea[@id=\"permanentAddress\"]"));
        permanentAddressField.sendKeys("050000, Astana");

        WebElement submitButton = driver.findElement(By.xpath("//button[@id=\"submit\"]"));
        submitButton.click();

        WebElement output = driver.findElement(By.xpath("//div[@id='output']/div/p"));

        Assert.assertEquals(output.getText(), "Name:Arailym");

        driver.quit();
    }
}
