package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Ignore
public class GroupJavaExplorersTest {

    private static final String BASE_URL = "https://magento.softwaretestingboard.com/";

    @Test
    public void testSearchWatches() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

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
        driver.get(BASE_URL);

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
    public void testSignInNegative() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);
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
        Thread.sleep(1000);
        Assert.assertEquals(accFailText, "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
        driver.quit();
    }

    @Test
    public void testSearchOlivia() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

        WebElement textBox = driver.findElement(By.xpath("//input[@id='search']"));
        textBox.sendKeys("Olivia");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        Thread.sleep(3000);

        String title = driver.findElement(By.xpath("//h1")).getText();

        assertEquals(title, "Search results for: 'Olivia'");

        driver.quit();
    }
    @Test
    public void testInvalidLoginWithNonExistedUser() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        WebElement signInElement = driver.findElement(By.className("authorization-link"));
        signInElement.click();

        WebElement emailInputField = driver.findElement(By.xpath("//input[@name='login[username]']"));
        emailInputField.sendKeys("asd@gmail.com");
        WebElement passwordInputField = driver.findElement(By.xpath("//input[@name='login[password]']"));
        passwordInputField.sendKeys("TestRandomPassword");

        WebElement signInButton = driver.findElement(By.id("send2"));
        signInButton.click();

        WebElement notificationMessage = driver.findElement(By.xpath("//div[@role='alert']"));
        Thread.sleep(1000);
        String notificationMessageText = notificationMessage.getText();

        Assert.assertEquals("The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.", notificationMessageText);
        driver.quit();
    }

    @Test
    public void testAddToCart() {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(BASE_URL);
        wait.until(ExpectedConditions.
                elementToBeClickable(By.xpath("//div//a[@id='ui-id-3']/span[contains(text(),'New')]")));
        WebElement catalogueItem = driver.
                findElement(By.xpath("//div//img[@class='product-image-photo']"));
        catalogueItem.click();
        wait.until(ExpectedConditions.
                elementToBeClickable(By.xpath("//div//button[@id='product-addtocart-button']")));

        WebElement item = driver.
                findElement(By.xpath("//div//button[@id='product-addtocart-button']"));

        List<WebElement> sizes = driver.
                findElements(By.xpath("//div//div[@class='swatch-option text']"));
        sizes.get((int) (Math.random() * sizes.size())).click();

        List<WebElement> colors = driver.
                findElements(By.xpath("//div//div[@class='swatch-option color']"));
        colors.get((int) (Math.random() * colors.size())).click();

        WebElement input = driver.findElement(By.xpath("//div/input[@id='qty']"));
        input.clear();
        input.sendKeys("2");
        item.click();

        WebElement cart = driver.findElement(By.xpath("//div//a[@class='action showcart']"));
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//div//div[@data-ui-id='message-success']")));
        cart.click();

        WebElement itemInCart = driver.findElement(By.xpath("//div//span[@class='count']"));

        int actualResult = Integer.parseInt(itemInCart.getText());
        Assert.assertEquals(actualResult, 2);
        driver.quit();
    }

    @Test
    public void testImages() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(BASE_URL);
        wait.until(ExpectedConditions.
                elementToBeClickable(By.xpath("//div//a[@id='ui-id-3']/span[contains(text(),'New')]")));

        WebElement whatsNew = driver.
                findElement(By.xpath("//div//a[@id='ui-id-3']/span[contains(text(),'New')]"));
        whatsNew.click();
        List<WebElement> images = driver.
                findElements(By.xpath("//div//img[@class='product-image-photo']"));
        Assert.assertEquals(images.size(), 4);

        driver.quit();
    }

    @Test
    public void testTitle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);
        Thread.sleep(2000);
        WebElement whatsNew = driver
                .findElement(By.xpath("//span[text()=\"What's New\"]"));
        whatsNew.click();
        String header = driver.findElement(By.xpath("//h1")).getText();
        assertEquals(header, "What's New");

        driver.quit();
    }

}

