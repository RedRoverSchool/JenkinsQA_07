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
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class GroupJavaExplorersTest extends BaseTest {

    private static final String BASE_URL = "https://magento.softwaretestingboard.com/";

    @Test
    public void testSearchWatches() {
        getDriver().get(BASE_URL);

        WebElement gear = getDriver().findElement(By.xpath("//a[@id='ui-id-6']/span[2]"));
        gear.click();

        WebElement watches = getDriver()
                .findElement(By.xpath("//*[@id='maincontent']/div[4]/div[2]/div[2]/div/ul/li[3]/a"));
        watches.click();

         WebElement clamberWatch = getDriver()
                .findElement(By.xpath("//*[@id='maincontent']/div[3]/div[1]/div[3]/ol/li[2]/div/div/strong/a"));
        clamberWatch.click();

        WebElement text = getDriver().findElement(By.xpath("//*[@class='base']"));
        String value = text.getText();

        Assert.assertEquals(value, "Clamber Watch");
    }

    @Test
    public void testLoginWithIncorrectData() {
        String email = "asdfg@mail.ru";
        String password = "12345";
        String message = "The account sign-in was incorrect or your account is disabled temporarily." +
                " Please wait and try again later.";

        getDriver().get(BASE_URL);

        WebElement loginIn = getDriver().findElement(By.xpath("//header/div[1]/div/ul/li[2]/a"));
        loginIn.click();

        WebElement textBoxEmail = getDriver().findElement(By.id("email"));
        textBoxEmail.sendKeys(email);

        WebElement textBoxPassword = getDriver().findElement(By.id("pass"));
        textBoxPassword.sendKeys(password);

        WebElement submitButton = getDriver().findElement(By.xpath("//fieldset/div[4]/div[1]/button"));
        submitButton.click();

        String value = getDriver()
                .findElement(By.xpath("//div[contains(text(), 'The account sign-in')]"))
                .getText();

        Assert.assertTrue(value.contains(message));
    }

    @Test
    public void testSignInNegative() throws InterruptedException {
        getDriver().get(BASE_URL);
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Home Page");
        WebElement signIn = getDriver().findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/a"));
        signIn.click();
        WebElement signInto = getDriver().findElement(By.xpath("//*[@id='send2']/span"));
        signInto.click();
        Thread.sleep(1000);
        WebElement field = getDriver().findElement(By.xpath("//*[@id='email-error']"));
        String failText = field.getText();
        Assert.assertEquals(failText, "This is a required field.");
        WebElement email = getDriver().findElement(By.xpath("//*[@id='email']"));
        email.sendKeys("abcd@gmail.com");
        WebElement password = getDriver().findElement(By.xpath("//*[@id='pass']"));
        password.sendKeys("1234");
        signInto.click();
        Thread.sleep(1000);
        WebElement accIncorrect = getDriver().findElement(By.xpath("//*[@id='maincontent']/div[2]/div[2]/div/div/div"));
        String accFailText = accIncorrect.getText();
        Assert.assertEquals(accFailText, "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");

    }

    @Test
    public void testSearchOlivia(){
        getDriver().get(BASE_URL);

        WebElement textBox = getDriver().findElement(By.xpath("//input[@id='search']"));
        textBox.sendKeys("Olivia");

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        String title = getDriver().findElement(By.xpath("//h1")).getText();

        assertEquals(title, "Search results for: 'Olivia'");
    }

    @Ignore
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

        getDriver().get(BASE_URL);
        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(10));

        WebElement catalogueItem = getDriver().
                findElement(By.xpath("//div//img[@class='product-image-photo']"));
        catalogueItem.click();

        WebElement item = getDriver().
                findElement(By.xpath("//div//button[@id='product-addtocart-button']"));

        List<WebElement> sizes = getDriver().
                findElements(By.xpath("//div//div[@class='swatch-option text']"));
        sizes.get((int) (Math.random() * sizes.size())).click();

        List<WebElement> colors = getDriver().
                findElements(By.xpath("//div//div[@class='swatch-option color']"));
        colors.get((int) (Math.random() * colors.size())).click();

        WebElement input = getDriver().findElement(By.xpath("//div/input[@id='qty']"));
        input.clear();
        input.sendKeys("2");
        item.click();

        WebElement cart = getDriver().findElement(By.xpath("//div//a[@class='action showcart']"));
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//div//div[@data-ui-id='message-success']")));
        cart.click();

        WebElement itemInCart = getDriver().findElement(By.xpath("//div//span[@class='count']"));

        int actualResult = Integer.parseInt(itemInCart.getText());
        Assert.assertEquals(actualResult, 2);
    }

    @Test
    public void testImages() {

        getDriver().get(BASE_URL);
        WebElement whatsNew = getDriver().
                findElement(By.xpath("//div//a[@id='ui-id-3']/span[contains(text(),'New')]"));
        whatsNew.click();
        List<WebElement> images = getDriver().
                findElements(By.xpath("//div//img[@class='product-image-photo']"));
        Assert.assertEquals(images.size(), 4);
    }

    @Ignore
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

    @Test
    public void testCreateNewFolder() {
        JenkinsUtils.login(getDriver());

        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();

        WebElement textBox = getDriver().findElement(By.id("name"));
        textBox.sendKeys("Folder1");

        WebElement folder = getDriver()
                .findElement(By.xpath("//div[@id='j-add-item-type-nested-projects']/ul/li[1]"));
        folder.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        String title = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(title, "Folder1");
    }

    @Test
    public void testCreateNewJob() {

        String expectedText = "This view has no jobs associated with it. You can either add " +
                "some existing jobs to this view or create a new job in this view.";

        JenkinsUtils.login(getDriver());

        WebElement newView = getDriver().findElement(By.xpath("//div//a[@title='New View']"));
        newView.click();
        WebElement viewName = getDriver().findElement(By.xpath("//div//input[@id='name']"));
        viewName.sendKeys("MyView2");
        WebElement viewTypeChckbx = getDriver().findElement(By.xpath("//div//label[@for='hudson.model.ListView']"));
        viewTypeChckbx.click();
        WebElement buttonSubmit = getDriver().findElement(By.xpath("//div//button[@name='Submit']"));
        buttonSubmit.click();
        WebElement buttonSubmitView = getDriver().findElement(By.xpath("//div//button[@name='Submit']"));
        buttonSubmitView.click();
        String actualText = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText();

        Assert.assertTrue(actualText.contains(expectedText));
        }

    @Test
    public void testTitl()  {

        getDriver().get(BASE_URL);

        WebElement whatsNew = getDriver()
                .findElement(By.xpath("//span[text()=\"Women\"]"));
        whatsNew.click();
        String header = getDriver().findElement(By.xpath("//h1")).getText();
        assertEquals(header, "Women");

    }
}

