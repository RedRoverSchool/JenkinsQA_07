package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupJavaBustersTest {

    @Test
    public void testMovieSearch() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://letterboxd.com");

        WebElement searchField = driver.findElement(By.xpath("//input[@id='search-q']"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@class='action']"));

        searchField.sendKeys("Merry Christmas, Mr. Lawrence");
        submitButton.click();

        WebElement movie = driver.findElement(By.xpath("//span[@class='film-title-wrapper']/a[contains(@href, 'lawrence')]"));
        String value = movie.getText();
        Assert.assertEquals("Merry Christmas, Mr. Lawrence", value);

        driver.quit();
    }

    @Test
    public void testSignInWithEmptyFields() {

        String fieldValue = "";

        WebDriver driver = new ChromeDriver();
        driver.get("https://letterboxd.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement signInButton = driver.findElement(By.xpath("//a[@href='/sign-in/']"));
        signInButton.click();

        WebElement usernameForm = driver.findElement(By.xpath("//input[@id='username']"));
        WebElement passwordForm = driver.findElement(By.xpath("//input[@id='password']"));
        WebElement signInButtonForm = driver.findElement(By.xpath("//input[@class='button -action button-green']"));

        usernameForm.sendKeys(fieldValue);
        passwordForm.sendKeys(fieldValue);
        signInButtonForm.click();

        WebElement message = driver.findElement(By.xpath("//div[@class='errormessage']//p"));
        String value = message.getText();
        Assert.assertEquals("Your credentials don’t match. It’s probably attributable to human error.", value);

        driver.quit();
    }

    @Test
    public void testFillUserName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@value='submit']"));

        fieldUsername.sendKeys("Evgeniia");
        submitButton.click();

        WebElement message = driver.findElement(By.id("_valueusername"));
        String value = message.getText();
        assertEquals(value, "Evgeniia");

        driver.quit();
    }

    @Test
    public void testCancelFillingUserName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement fieldUsername = driver.findElement(By.name("username"));
        WebElement cancelButton = driver.findElement(By.xpath("//*[@value='cancel']"));

        fieldUsername.sendKeys("Evgeniia");
        cancelButton.click();

        String value = fieldUsername.getText();
        assertEquals(value, "");

        driver.quit();
    }

    @Test
    public void testAllFields() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        String mainLink = "https://www.selenium.dev/selenium/web/web-form.html";
        String indexLink = "https://www.selenium.dev/selenium/web/index.html";

        driver.get(mainLink);
        WebElement textInput = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        WebElement passwordInput = driver.findElement(By.xpath("//label[2]/input[1]"));
        WebElement textArea = driver.findElement(By.xpath("//label[3]/textarea[1]"));
        WebElement disableInput = driver.findElement(By.xpath("//label[4]/input[1]"));
        WebElement readOnlyField = driver.findElement(By.xpath("//label[5]/input[1]"));
        WebElement linkReturnToIndex = driver.findElement(By.xpath("//a[contains(text(),'Return to index')]"));

        textInput.sendKeys("test");
        passwordInput.sendKeys("12345678");
        textArea.sendKeys("This is a test text for text area");
        Assert.assertFalse(disableInput.isEnabled());
        Assert.assertTrue(true, readOnlyField.getAttribute("true"));

        linkReturnToIndex.click();
        Thread.sleep(2000);
        String currentLink = driver.getCurrentUrl();
        Assert.assertEquals(currentLink, indexLink);

        driver.get(mainLink);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdownSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='my-select']")));
        Select dropDownSelectedValue = new Select(dropdownSelect);
        dropDownSelectedValue.selectByValue("1");
        dropDownSelectedValue.selectByValue("2");
        dropDownSelectedValue.selectByValue("3");

        driver.quit();
    }

    @Test
    public void fillInFormTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationintesting.online/");

        WebElement nameField = driver.findElement(By.xpath("//input[@id='name']"));
        WebElement emailField = driver.findElement(By.xpath("//input[@id='email']"));
        WebElement phoneField = driver.findElement(By.xpath("//input[@id='phone']"));
        WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
        WebElement messageField = driver.findElement(By.xpath("//textarea[@id='description']"));

        nameField.sendKeys("Marta");
        emailField.sendKeys("fake@fakeemail.com");
        phoneField.sendKeys(" 012345678901");
        subjectField.sendKeys("Subject must be between 5 and 100 characters.");
        messageField.sendKeys("The Old Farmhouse, Shady Street, Newfordburyshire, NE1 410S");

        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submitContact']"));
        submitButton.click();
        Thread.sleep(5000);

        WebElement message = driver.findElement(By.xpath("//h2[normalize-space()='Thanks for getting in touch Marta!']"));
        String value = message.getText();
        assertEquals(value, "Thanks for getting in touch Marta!");
        driver.quit();
    }

    @Test
    public void testSearch() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.euronics.lv/");

        WebElement cookieButton = driver.findElement(By.id("cookie-accept-all-button"));
        cookieButton.click();

        WebElement searchWrapper = driver.findElement(By.xpath("//div[@class = 'search']"));
        searchWrapper.click();

        WebElement textBox = driver.findElement(By.className("autocomplete__input"));
        textBox.sendKeys("macbook");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.className("autocomplete__search-button"));
        searchButton.click();

        driver.manage().window().maximize();
        Thread.sleep(1000);

        WebElement message = driver.findElement(By.xpath("//h1[@class = 'category__header']"));
        String value = message.getText();
        Assert.assertEquals(value, "macbook");

        driver.quit();
    }
}
