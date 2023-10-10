package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import static org.testng.Assert.assertEquals;

public class PlusThreeTest {

    public static final String USERNAME = "TestUser1";
    public static final String URL = "https://parabank.parasoft.com/parabank/register.htm";
    public static final String PASSWORD = "qwert12345";
    public static final String FULL_NAME = "Akiko";
    public static final String EMAIL = "Akiko@gmail.com";
    public static final String CURRENT_ADDRESS = "USA";
    public static final String PERMANENT_ADDRESS = "USA1";
    public static final String CITY= "LOS ANGELES";
    public static final String STATE ="California";
    public static final String URL_PARABANK = "https://parabank.parasoft.com/";
    ChromeDriver driver;

    public void cleanDataBaseAndCloseBrow() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        WebElement adminPanel = driver.findElement(By.cssSelector(".leftmenu li:nth-child(6)"));
        adminPanel.click();

        WebElement cleanButton = driver.findElement(By.cssSelector("button[value='CLEAN']"));
        cleanButton.click();
        Assert.assertEquals("Database Cleaned", driver.findElement(By.cssSelector("div[id='rightPanel'] > p > b")).getText());

        driver.quit();
    }

    @Test
    public void testSearch() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://demoqa.com/text-box");

        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys(FULL_NAME);

        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys(EMAIL);

        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        currentAddress.sendKeys(CURRENT_ADDRESS);

        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys(PERMANENT_ADDRESS);

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        WebElement nameR = driver.findElement(By.id("name"));
        String value = nameR.getText();
        Assert.assertEquals("Name:" + FULL_NAME, value);

        WebElement emailR = driver.findElement(By.id("email"));
        String value1 = emailR.getText();
        Assert.assertEquals("Email:" + EMAIL, value1);

        WebElement currentAddreesR = driver.findElement(By.xpath("//*[@class=\"border col-md-12 col-sm-12\"]/p[3]"));
        String value2 = currentAddreesR.getText();
        Assert.assertEquals("Current Address :" + CURRENT_ADDRESS, value2);

        WebElement permanentAddressR = driver.findElement(By.xpath("//*[@class=\"border col-md-12 col-sm-12\"]/p[4]"));
        String value3 = permanentAddressR.getText();
        Assert.assertEquals("Permananet Address :" + PERMANENT_ADDRESS, value3);

        driver.quit();
    }

    @Test(description = "Создание/регистрация пользователя в банке")
    public void createUser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        WebElement firstName = driver.findElement(By.id("customer.firstName"));
        firstName.sendKeys("Test");

        WebElement lastName = driver.findElement(By.id("customer.lastName"));
        lastName.sendKeys("Test");

        WebElement address = driver.findElement(By.id("customer.address.street"));
        address.sendKeys("SPB");

        WebElement city = driver.findElement(By.id("customer.address.city"));
        city.sendKeys("SPB");

        WebElement state = driver.findElement(By.id("customer.address.state"));
        state.sendKeys("LO");

        WebElement zipCode = driver.findElement(By.id("customer.address.zipCode"));
        zipCode.sendKeys("123-415");

        WebElement userName = driver.findElement(By.id("customer.username"));
        userName.sendKeys(USERNAME);

        WebElement password = driver.findElement(By.id("customer.password"));
        password.sendKeys(PASSWORD);

        WebElement repeatPass = driver.findElement(By.id("repeatedPassword"));
        repeatPass.sendKeys(PASSWORD);

        WebElement ssn = driver.findElement(By.id("customer.ssn"));
        ssn.sendKeys("123456");

        WebElement register = driver.findElement(By.cssSelector("[value='Register']"));
        register.submit();

        WebElement title = driver.findElement(By.xpath("//div[@id='rightPanel']/h1"));
        String resTitle = title.getText();
        Assert.assertEquals(resTitle, "Welcome " + USERNAME);

        WebElement result = driver.findElement(By.xpath("//div[@id='rightPanel']/p"));
        String resText = result.getText();
        Assert.assertEquals(resText, "Your account was created successfully. You are now logged in.");

        cleanDataBaseAndCloseBrow();
    }

    @Test
    public static void forgotLoginTest () {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        driver.findElement(By.xpath("//a[contains(.,\"Forgot login info?\")]")).click();

        WebElement titleForgotLogin = driver.findElement(By.xpath("//h1[@class=\"title\"]"));
        String resultTextTitle = titleForgotLogin.getText();
        Assert.assertEquals(resultTextTitle, "Customer Lookup");

        WebElement firstNameForgotLogin = driver.findElement(By.id("firstName"));
        firstNameForgotLogin.sendKeys("user");

        WebElement lastNameForgotLogin = driver.findElement(By.cssSelector("#lastName"));
        lastNameForgotLogin.sendKeys("User_user");

        WebElement addressForgotLogin = driver.findElement(By.id("address.street"));
        addressForgotLogin.sendKeys(CURRENT_ADDRESS);

        WebElement cityForgotLogin = driver.findElement(By.id("address.city"));
        cityForgotLogin.sendKeys(CITY);

        WebElement stateForgotLogin= driver.findElement(By.id("address.state"));
        stateForgotLogin.sendKeys(STATE);

        WebElement zipCodeForgotLogin = driver.findElement(By.id("address.zipCode"));
        zipCodeForgotLogin.sendKeys("123456");

        WebElement ssnForgotLogin = driver.findElement(By.id("ssn"))  ;
        ssnForgotLogin.sendKeys("123fff");

        WebElement submitForgotLogin = driver.findElement(By.xpath("//input[contains(@value,\"Find My Login Info\")]"));
        submitForgotLogin.click();

        WebElement titleError = driver.findElement(By.xpath("//p[contains(@class,\"error\")]"));
        String textError = titleError.getText();
        Assert.assertEquals(textError, "The customer information provided could not be found.");

        driver.quit();
    }

    @Test
    public static void testSearchDuck() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://duckduckgo.com/");

        driver.findElement(By.xpath("//input[@id='searchbox_input']"))
                .sendKeys("Selenium");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.xpath("//button[@aria-label = 'Search']"))
                .click();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        try {
            WebElement title = driver.findElement(By.xpath("//span[@class ='module__title__link']"));
            String value = title.getText();
            assertEquals(value, "Selenium");
        } catch (NoSuchFrameException e) {
            System.out.println("My_Frame not found: " + e.getMessage());
        }

        driver.quit();
    }

    @Test(description = "Swag labs login")
    public void loginSwagLabs() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Swag Labs");

        WebElement loginField = driver.findElement(By.xpath(".//div/input[@id='user-name']"));
        WebElement passwordField = driver.findElement(By.xpath(".//div/input[@id='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));

        loginField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        Thread.sleep(1000);

        WebElement marketLogo = driver.findElement(By.xpath(".//div[text()='Swag Labs']"));

        String name = marketLogo.getText();
        Assert.assertEquals(name, "Swag Labs");

        driver.quit();
    }

    @Test
    public  void contactUs() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL_PARABANK);

        WebElement contactUs = driver.findElement(By.xpath("//a[contains(text(), 'contact')]"));
        contactUs.click();
        WebElement title = driver.findElement(By.xpath("//*[@class='title']"));
        String resTitle = title.getText();
        Assert.assertEquals(resTitle, "Customer Care");

        WebElement nameField = driver.findElement(By.name("name"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement phoneField = driver.findElement(By.name("phone"));
        WebElement messageField = driver.findElement(By.name("message"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id='contactForm']//descendant::input[@class='button']"));

        nameField.sendKeys(USERNAME);
        emailField.sendKeys("example@example.com");
        phoneField.sendKeys("111111111");
        messageField.sendKeys("Text");

        submitButton.click();

        WebElement confirmationMessage = driver.findElement(By.xpath("//*[@id='rightPanel']/p[contains(text(),'Thank you')]"));
        Assert.assertEquals(confirmationMessage.getText(), "Thank you " + USERNAME);
        driver.quit();
    }
    @Test
    public void testTemperatureInFahrenheit() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String url = "https://openweathermap.org/";
        String fTempSymbol = "°F";

        driver.get(url);

        WebElement menuImperial = driver.findElement(
                By.xpath("//div[@class = 'switch-container']/div[@class='option']/following-sibling::div")
        );
        menuImperial.click();

        WebElement tempF = driver.findElement(
                By.xpath("//div[@class='current-temp']/span")
        );
        String tempInF = tempF.getText();

        Assert.assertTrue(tempInF.contains(fTempSymbol));

        driver.quit();
    }

    @Test
    public void DemoqaTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement textBox = driver.findElement(By.id("firstName"));
        textBox.sendKeys("Vova");

        WebElement textBox2 = driver.findElement(By.id("lastName"));
        textBox2.sendKeys("Petrov");

        WebElement tel = driver.findElement(By.id("userNumber"));
        tel.sendKeys("8800222552");

        WebElement pol = driver.findElement(By.className("custom-control-label"));
        pol.click();

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        WebElement proverka = driver.findElement(By.id("example-modal-sizes-title-lg"));
        String value = proverka.getText();
        Assert.assertEquals(value, "Thanks for submitting the form");

        driver.quit();
    }

    @Test
    public void DemoTest() {

    }



}

