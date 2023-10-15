package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PlusThreeTest extends BaseTest {

    public static final String USERNAME = "TestUser1";
    public static final String URL = "https://parabank.parasoft.com/parabank/register.htm";
    public static final String PASSWORD = "qwert12345";
    public static final String CITY= "LOS ANGELES";
    public static final String STATE ="California";
    public static final String URL_PARABANK = "https://parabank.parasoft.com/";
    public static final String CURRENT_ADDRESS = "USA";
    //---------------------------------------------------------------------------------------------------------------------------------
    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com/";

    public static class DataProviders {

        @DataProvider(name = "validData")
        public String[][] validCredentials() {
            return new String[][] {
                    { "standard_user", "secret_sauce" }
            };
        }

        @DataProvider(name = "invalidData")
        public String[][] inValidCredentials() {
            return new String[][] {
                    { "user", "user" }
            };
        }
    }

    private void login(String username, String password) {

        getDriver().get(SAUCEDEMO_URL);

        WebElement usernameInput = getDriver().findElement(By.id("user-name"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = getDriver().findElement(By.id("password"));
        passwordInput.sendKeys(password);

        WebElement loginButton = getDriver().findElement(By.id("login-button"));
        loginButton.click();
    }
    
    @Test(dataProviderClass = PlusThreeTest.DataProviders.class, dataProvider = "validData")
    public void TestAuthorizationPositive(String username, String password) {

        login(username, password);

        String currentURL = getDriver().getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html");

    }

    @Test(dataProviderClass = PlusThreeTest.DataProviders.class, dataProvider = "invalidData")
    public void TestAuthorizationNegative(String username, String password) {

        login(username, password);

        String actualResult =  getDriver().findElement(By.xpath("//h3[@data-test='error']")).getText();
        Assert.assertEquals(actualResult, "Epic sadface: Username and password do not match any user in this service");

    }

    @Test(description = "Add to cart via catalog", dataProviderClass = PlusThreeTest.DataProviders.class, dataProvider = "validData")
    public void TestAddCart(String username, String password) {

        login(username, password);

        WebElement itemButton = getDriver().findElement(By.xpath("//button[@id = 'add-to-cart-sauce-labs-backpack']"));
        itemButton.click();

        WebElement redIcon = getDriver().findElement(By.xpath("//span[@class = 'shopping_cart_badge']"));

        Assert.assertNotNull(redIcon, "Элемент redIcon не найден на странице");

    }

    @Test(description = "Remove from cart via basket", dataProviderClass = PlusThreeTest.DataProviders.class, dataProvider = "validData")
    public void TestRemoveCart(String username, String password) {

        login(username, password);

        WebElement itemButton = getDriver().findElement(By.xpath("//button[@id = 'add-to-cart-sauce-labs-backpack']"));
        itemButton.click();

        WebElement redIcon = getDriver().findElement(By.xpath("//span[@class = 'shopping_cart_badge']"));

        Assert.assertNotNull(redIcon, "Элемент redIcon не найден на странице");

    }

    //---------------------------------------------------------------------------------------------------------------------------------

    public void cleanDataBaseAndCloseBrow() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        WebElement adminPanel = driver.findElement(By.cssSelector(".leftmenu li:nth-child(6)"));
        adminPanel.click();

        WebElement cleanButton = driver.findElement(By.cssSelector("button[value='CLEAN']"));
        cleanButton.click();
        Assert.assertEquals("Database Cleaned", driver.findElement(By.cssSelector("div[id='rightPanel'] > p > b")).getText());

        driver.quit();
    }

    @Ignore
    @Test(description = "Создание/регистрация пользователя в банке")
    public void createUser() {
        WebDriver driver = new ChromeDriver();
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
    public void testForgotLoginTest() {

        getDriver().get(URL_PARABANK + "/index.htm");

        getDriver().findElement(By.xpath("//a[contains(.,\"Forgot login info?\")]")).click();

        WebElement titleForgotLogin = getDriver().findElement(By.xpath("//h1[@class=\"title\"]"));
        String resultTextTitle = titleForgotLogin.getText();
        Assert.assertEquals(resultTextTitle, "Customer Lookup");

        WebElement firstNameForgotLogin = getDriver().findElement(By.id("firstName"));
        firstNameForgotLogin.sendKeys("user");

        WebElement lastNameForgotLogin = getDriver().findElement(By.cssSelector("#lastName"));
        lastNameForgotLogin.sendKeys("User_user");

        WebElement addressForgotLogin = getDriver().findElement(By.id("address.street"));
        addressForgotLogin.sendKeys(CURRENT_ADDRESS);

        WebElement cityForgotLogin = getDriver().findElement(By.id("address.city"));
        cityForgotLogin.sendKeys(CITY);

        WebElement stateForgotLogin= getDriver().findElement(By.id("address.state"));
        stateForgotLogin.sendKeys(STATE);

        WebElement zipCodeForgotLogin = getDriver().findElement(By.id("address.zipCode"));
        zipCodeForgotLogin.sendKeys("123456");

        WebElement ssnForgotLogin = getDriver().findElement(By.id("ssn"))  ;
        ssnForgotLogin.sendKeys("123fff");

        WebElement submitForgotLogin = getDriver().findElement(By.xpath("//input[contains(@value,\"Find My Login Info\")]"));
        submitForgotLogin.click();

        WebElement titleError = getDriver().findElement(By.xpath("//p[contains(@class,\"error\")]"));
        String textError = titleError.getText();
        Assert.assertEquals(textError, "The customer information provided could not be found.");
    }
@Test (description = "Jenkins login First Test")
public void testLoginJenkinsKaramelev() {
        JenkinsUtils.login(getDriver());
        Assert.assertEquals
                (getDriver()
                        .findElement(By.xpath("//h1[contains(.,'Welcome to Jenkins!')]"))
                        .getText(),"Welcome to Jenkins!");
}

    @Ignore
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
    public void loginSwagLabs() {

        getDriver().get("https://www.saucedemo.com/");
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Swag Labs");

        getDriver().findElement(By.xpath(".//div/input[@id='user-name']")).sendKeys("standard_user");
        getDriver().findElement(By.xpath(".//div/input[@id='password']")).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath("//*[@id='login-button']")).click();
        String name = getDriver().findElement(By.xpath(".//div[text()='Swag Labs']")).getText();
        Assert.assertEquals(name, "Swag Labs");

    }

    @Test(description = "Jenkins first test")
    public void loginJenkinsVasilyiD() throws InterruptedException {

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public  void testContactUs() {

        getDriver().get(URL_PARABANK);

        WebElement contactUs = getDriver().findElement(By.xpath("//a[contains(text(), 'contact')]"));
        contactUs.click();

        WebElement title = getDriver().findElement(By.xpath("//*[@class='title']"));
        String resTitle = title.getText();
        Assert.assertEquals(resTitle, "Customer Care");

        WebElement nameField = getDriver().findElement(By.name("name"));
        WebElement emailField = getDriver().findElement(By.name("email"));
        WebElement phoneField = getDriver().findElement(By.name("phone"));
        WebElement messageField = getDriver().findElement(By.name("message"));
        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id='contactForm']//descendant::input[@class='button']"));

        nameField.sendKeys(USERNAME);
        emailField.sendKeys("example@example.com");
        phoneField.sendKeys("111111111");
        messageField.sendKeys("Text");

        submitButton.click();

        WebElement confirmationMessage = getDriver().findElement(By.xpath("//*[@id='rightPanel']/p[contains(text(),'Thank you')]"));
        Assert.assertEquals(confirmationMessage.getText(), "Thank you " + USERNAME);
    }

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void Trivio () {
        WebDriver driver = new ChromeDriver();
        driver.get("https://login.trivio.ru/");

        WebElement textBox1 = driver.findElement(By.xpath("//input[contains(@id,'login')]"));
        textBox1.sendKeys("demo");

        WebElement textBox2 = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        textBox2.sendKeys("demo");

        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button"));
        signInButton.click();
        driver.quit();
    }

    @Test
    public void testSignUpButton() {
        getDriver().get("https://bandcamp.com/");
        WebElement signUp = getDriver().findElement(By.className("all-signup-link"));
        signUp.click();
        List<WebElement> list = getDriver().findElements(By.className("signup-button"));
        Assert.assertEquals(list.size(), 3);
    }
@Ignore
    @Test
    void tripadvisorTest() {
        getDriver().get("https://www.tripadvisor.ru");

        getDriver().findElement(By.xpath("//a[@href='/Restaurants']")).click();

        String value = getDriver().findElement(By.className("lockup_header")).getText();
        Assert.assertEquals(value, "Найдите идеальный ресторан");

        getDriver().findElement(By.id("component_7")).click();
        getDriver().findElement(By.className("ctKgY")).click();
        getDriver().findElement(By.cssSelector("[placeholder='Город или название ресторана']"))
                .sendKeys("Москва");

        getDriver().findElement(By.xpath("//a[@href='/Restaurants-g298484-Moscow_Central_Russia.html']"))
                .click();

        WebDriverWait waitTitle = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        waitTitle.until(ExpectedConditions.visibilityOfElementLocated(By.id("HEADING")));

        String getTitle = getDriver().findElement(By.id("HEADING")).getText();
        Assert.assertEquals(getTitle, "Рестораны Москвы Moscow");
    }

    @Test (description = "Go to the section Manage Jenkins")
    public void testJenkinsManage(){
        JenkinsUtils.login(getDriver());

        WebElement manageJenkinsLink = getDriver().findElement(By.linkText("Manage Jenkins"));
        manageJenkinsLink.click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[text()='Manage Jenkins']")).getText(),
                "Manage Jenkins");
    }
}

