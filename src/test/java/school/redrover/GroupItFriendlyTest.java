package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class GroupItFriendlyTest {

    @Test
    public void testDemoQaOpenPage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement image = driver.findElement(By.xpath("//*[@id=\"app\"]/header/a/img"));
            image.click();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDemoQaChangePage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement element = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
            element.click();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement text = driver.findElement(By.xpath("//*[.='Please select an item from left to start practice.']"));
            String value = text.getText();
            Assert.assertEquals(value, "Please select an item from left to start practice.");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDemoQaTextBox() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/elements");
            WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Text Box')]"));
            element.click();
            WebElement fullNameField = driver.findElement(By.xpath("//*[@id='userName']"));
            fullNameField.click();
            fullNameField.sendKeys("Adam Adams");
        } finally {
          driver.quit();
        }
    }

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://so-yummi-qa.netlify.app/register");
        String randomUsername = "Test" + UUID.randomUUID().toString().substring(0, 8);
        String randomEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";


        Thread.sleep(1000);
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.click();
        usernameInput.sendKeys(randomUsername);
        WebElement emailInput = driver.findElement(By.id("emailInput"));
        emailInput.click();
        emailInput.sendKeys(randomEmail);
        WebElement passwordInput = driver.findElement(By.id("passwordInput"));
        passwordInput.click();
        passwordInput.sendKeys("Test@123456");


        WebElement searchButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton2.click();

        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://so-yummi-qa.netlify.app/home";
        Assert.assertEquals(currentUrl, expectedUrl, "The current URL does not match the expected URL.");

        driver.quit();
    }

    @Test
    public void DemoQATextBoxTest() {
          WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().window().maximize();
            JavascriptExecutor jsx = (JavascriptExecutor)driver;
            jsx.executeScript("window.scrollBy(0,450)", "");

            WebElement elements = driver.findElement(By.xpath("//div[@class='category-cards']//div[1]//div[1]//div[2]//*[name()='svg']"));
            elements.click();

            WebElement textBox = driver.findElement(By.xpath("//span[normalize-space()='Text Box']"));
            textBox.click();

            WebElement inputFullName = driver.findElement(By.xpath("//input[@placeholder=\"Full Name\"]"));
            inputFullName.sendKeys("Natalia V");

            WebElement inputEmail = driver.findElement(By.xpath("//input[@id=\"userEmail\"]"));
            inputEmail.sendKeys("mail@gmail.com");

            WebElement inputCurrentAddress = driver.findElement(By.xpath("//textarea[@placeholder=\"Current Address\"]"));
            inputCurrentAddress.sendKeys("Slo");

            WebElement inputPermanentAddress = driver.findElement(By.xpath("//textarea[@id=\"permanentAddress\"]"));
            inputPermanentAddress.sendKeys("Ukr");

            WebElement submitBTN = driver.findElement(By.xpath("//button[@id='submit']"));
            jsx.executeScript("window.scrollBy(0,450)", "");
            submitBTN.click();
            assertEquals(driver.findElement(By.xpath("//div[@id=\"output\"]//p[@id=\"name\"]")).getText(), "Name:Natalia V");
            assertEquals(driver.findElement(By.xpath("//div[@id=\"output\"]//p[@id=\"email\"]")).getText(), "Email:mail@gmail.com");
            assertEquals(driver.findElement(By.xpath("//div[@id=\"output\"]//p[@id=\"currentAddress\"]")).getText(), "Current Address :Slo");
            assertEquals(driver.findElement(By.xpath("//div[@id=\"output\"]//p[@id=\"permanentAddress\"]")).getText(), "Permananet Address :Ukr");

        } finally {
            driver.quit();
        }
    }

    @Test
    public void DemoQACheckBoxTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().window().maximize();
            JavascriptExecutor jsx = (JavascriptExecutor)driver;
            jsx.executeScript("window.scrollBy(0,450)", "");

            WebElement elements = driver.findElement(By.xpath("//div[@class='category-cards']//div[1]//div[1]//div[2]//*[name()='svg']"));
            elements.click();

            WebElement checkBoxElement = driver.findElement(By.xpath("//span[normalize-space()='Check Box']"));
            checkBoxElement.click();

            WebElement checkBox = driver.findElement(By.xpath("//span[@class=\"rct-checkbox\"]"));
            checkBox.click();

            assertEquals(driver.findElement(By.xpath("//div[@id=\"result\"]/span[1]")).getText(), "You have selected :");

        } finally {
            driver.quit();
        }
    }

    @Test
    public void DemoQARadioButtonTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");
            driver.manage().window().maximize();
            JavascriptExecutor jsx = (JavascriptExecutor)driver;
            jsx.executeScript("window.scrollBy(0,450)", "");

            WebElement elements = driver.findElement(By.xpath("//div[@class='category-cards']//div[1]//div[1]//div[2]//*[name()='svg']"));
            elements.click();

            WebElement radioButtonElement = driver.findElement(By.xpath("//span[normalize-space()='Radio Button']"));
            radioButtonElement.click();

            WebElement yesButton = driver.findElement(By.xpath("//div[@class=\"custom-control custom-radio custom-control-inline\"]/label[@for=\"yesRadio\"]"));
            yesButton.click();

            assertEquals(driver.findElement(By.xpath("//p[@class=\"mt-3\"]/span")).getText(), "Yes");

            WebElement impressiveButton = driver.findElement(By.xpath("//div[@class=\"custom-control custom-radio custom-control-inline\"]/label[@for=\"impressiveRadio\"]"));
            impressiveButton.click();

            assertEquals(driver.findElement(By.xpath("//p[@class=\"mt-3\"]/span")).getText(), "Impressive");

        } finally {
            driver.quit();
        }
    }

}
