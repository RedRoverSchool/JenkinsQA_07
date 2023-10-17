package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;
import java.util.Random;

public class GroupIntroVertsQaTest extends BaseTest {
    static Random random = new Random();
    static String URL = "https://parabank.parasoft.com/parabank/index.htm";
    static int n = random.nextInt(1000);
    public static final String USER_NAME = String.valueOf(n);
    public static final String PASSWORD = "54321";

    static class variablesDmitryS{
        private static final String URL = "https://demoqa.com/automation-practice-form";
        private static final String FIRST_NAME = "Oleg";
        private static final String LAST_NAME = "Komarov";
        private static final String EMAIL = "testmail@yandex.ru";
        private static final  String NUMBER = "89991114488";
        private static final String CURRENT_ADDRESS = "3 метра над уровнем неба";

        private static String getUrl() {
            return URL;
        }

    }
    @Ignore
    @Test
    public void testRegistr(){
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        WebElement register = driver.findElement(By.xpath("//*[@id='loginPanel']/p[2]/a"));
        register.click();

        WebElement firstName = driver.findElement(By.id("customer.firstName"));
        firstName.sendKeys("Ulyana");

        WebElement lastName = driver.findElement(By.id("customer.lastName"));
        lastName.sendKeys("Ver");

        WebElement adress = driver.findElement(By.id("customer.address.street"));
        adress.sendKeys("ABC");

        WebElement city = driver.findElement(By.id("customer.address.city"));
        city.sendKeys("Saint-P");

        WebElement state = driver.findElement(By.id("customer.address.state"));
        state.sendKeys("LO");

        WebElement zipCode = driver.findElement(By.id("customer.address.zipCode"));
        zipCode.sendKeys("190000");

        WebElement phone = driver.findElement(By.id("customer.phoneNumber"));
        phone.sendKeys("89567394");

        WebElement ssn = driver.findElement(By.id("customer.ssn"));
        ssn.sendKeys("13");

        WebElement userName = driver.findElement(By.id("customer.username"));
        userName.sendKeys(USER_NAME);

        WebElement password = driver.findElement(By.id("customer.password"));
        password.sendKeys(PASSWORD);

        WebElement confirm = driver.findElement(By.id("repeatedPassword"));
        confirm.sendKeys(PASSWORD);

        WebElement buttonRegister = driver
                .findElement(By.xpath("//*[@id='customerForm']/table/tbody/tr[13]/td[2]/input"));
        buttonRegister.click();

        WebElement welcome = driver.findElement(By.xpath("//*[@id='rightPanel']/h1"));

        Assert.assertEquals(welcome.getText(),"Welcome " + USER_NAME);

        driver.quit();
    }

    @Ignore
    @Test
    public void testCorrectLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        WebElement login = driver.findElement(By.name("username"));
        login.sendKeys(USER_NAME);

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(PASSWORD);

        Thread.sleep(3000);

        WebElement button = driver.findElement(By.xpath("//*[@id='loginPanel']/form/div[3]/input"));
        button.click();

        WebElement page = driver.findElement(By.className("title"));
        String value = page.getText();

        Assert.assertEquals(value, "Accounts Overview");

        driver.quit();
    }

    @Test
    public void testAboutUs(){
        getDriver().get(URL);

        WebElement usernameInput = getDriver().findElement(By.xpath(" //a[@href=contains(text(), 'About Us')]"));
        usernameInput.click();

        WebElement greetings = getDriver().findElement(By.xpath("//h1[@class='title']"));
        Assert.assertEquals(greetings.getText(), "ParaSoft Demo Website");
    }

    @Test
    public void testSwagLabsEmptyInputsAuthorization(){
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs");

        WebElement loginButton = getDriver().findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        WebElement errorMessage = getDriver().findElement(By.xpath("//div[@class='error-message-container error']"));
        Assert.assertEquals(errorMessage.getText(),"Epic sadface: Username is required");
    }

    @Test
    public void testSwagLabsStandartAuthorization(){
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs");

        WebElement username = getDriver().findElement(By.xpath("//input[@id='user-name']"));
        username.sendKeys("standard_user");

        WebElement password = getDriver().findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = getDriver().findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        WebElement profileTitle = getDriver().findElement(By.xpath("//div[@class='header_secondary_container']/child::span[@class='title']"));
        Assert.assertEquals(profileTitle.getText(), "Products");
    }

    @Test
    public void testSwagLabsAddToCart(){
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath("//input[@id='login-button']")).click();

        WebElement backPack = getDriver().findElement(By.xpath("//div[text()='Sauce Labs Backpack']/parent::*"));
        backPack.click();
        WebElement addToCart = getDriver().findElement(By.xpath("//button[text()='Add to cart']"));
        addToCart.click();

        WebElement cart = getDriver().findElement(By.xpath("//span[@class='shopping_cart_badge']"));
        Assert.assertEquals(cart.getText(),"1");
    }

    /**
     * DmitryS. Тесты
     */
    // region DmitryS. Добавляю в данный блок тесты.
    @Ignore
    @Test (description = "проверка содержания хидера")
    public void testTextHeaderForm() {
        getDriver().get(variablesDmitryS.getUrl());
        WebElement mainHeaderForm = getDriver().findElement(By.xpath("//div[@class = 'main-header']"));
        String textMainHeaderForm = mainHeaderForm.getText();
        Assert.assertEquals(textMainHeaderForm, "Practice Form");
        WebElement titleRegistrationForm = getDriver().findElement(By.xpath("//h5"));
        String textTitleRegistrationForm = titleRegistrationForm.getText();
        Assert.assertEquals(textTitleRegistrationForm, "Student Registration Form");
    }

    @Ignore
    @Test (description = "позитивный кейс, заполнение всех полей")
    public void testPositiveTestAllParameters() throws InterruptedException {
        getDriver().get(variablesDmitryS.getUrl());
        WebElement fieldFirstName = getDriver().findElement(By.xpath("//input[@id = 'firstName']"));
        WebElement fieldLastName = getDriver().findElement(By.xpath("//input[@id = 'lastName']"));
        WebElement fieldEmail = getDriver().findElement(By.xpath("//input[@id='userEmail']"));
        WebElement radioButtonGender = getDriver().findElement(By.xpath("//label[@for = 'gender-radio-1']"));
        WebElement fieldNumber = getDriver().findElement(By.xpath("//input[@id = 'userNumber']"));
        WebElement fieldDateOfBirth = getDriver().findElement(By.xpath("//input[@id = 'dateOfBirthInput']"));
        WebElement fieldSubjects = getDriver().findElement(By.xpath("//*[@id='subjectsInput']"));
//        WebElement fieldHobbiesSport = getDriver().findElement(By.xpath("//label[@for = 'hobbies-checkbox-2']"));
        WebElement fieldCurrentAddress = getDriver().findElement(By.xpath("//textarea[@id = 'currentAddress']"));
        fieldFirstName.sendKeys(variablesDmitryS.FIRST_NAME);
        fieldLastName.sendKeys(variablesDmitryS.LAST_NAME);
        fieldEmail.sendKeys(variablesDmitryS.EMAIL);
        radioButtonGender.click();
        fieldNumber.sendKeys(variablesDmitryS.NUMBER);
        fieldDateOfBirth.click();
        fieldDateOfBirth.clear();
        Thread.sleep(5000);
        fieldDateOfBirth.sendKeys("Aug 2023-11");
        Thread.sleep(5000);
        fieldSubjects.sendKeys("c");
        fieldSubjects.sendKeys(Keys.ENTER);
//        fieldHobbiesSport.click();
        fieldCurrentAddress.sendKeys(variablesDmitryS.CURRENT_ADDRESS);
        Thread.sleep(5000);
        WebElement submitButton = getDriver().findElement(By.id("submit"));
        submitButton.submit();
        WebElement resultValueStudentName = getDriver().findElement(By.xpath("//tr/td[2]"));
        String textResultValueStudentName = resultValueStudentName.getText();
        Assert.assertEquals(textResultValueStudentName, variablesDmitryS.FIRST_NAME + " " + variablesDmitryS.LAST_NAME);
    }
    // endregion

    // region AkiMiraTest

    @Test (description = "Test of Login to Swag Labs")
    public void testLoginSwagLabs () {
        getDriver().get("https://www.saucedemo.com");

        String title = getDriver().getTitle();
        Assert.assertEquals("Swag Labs", title);

        WebElement textBoxName = getDriver().findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement textBoxPassword = getDriver().findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id=\"login-button\"]"));

        textBoxName.sendKeys("standard_user");
        textBoxPassword.sendKeys("secret_sauce");
        submitButton.click();

        WebElement text = getDriver().findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
        String value = text.getText();
        Assert.assertEquals(value, "Products");

    }

    @Test (description = "Test of Jenkins Search field")
    public void testJenkinsSearchField () {
        JenkinsUtils.login(getDriver());
        WebElement searchField = getDriver().findElement(By.id("search-box"));
        searchField.sendKeys("admin");
        searchField.sendKeys(Keys.ENTER);
        WebElement searchResults = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]"));
        Assert.assertTrue(searchResults.isDisplayed());
        String value = searchResults.getText();
        Assert.assertTrue(value.contains("admin"));
    }
    // endregion

    @Ignore
    @Test

    public void testTextBoxNN () {


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

        WebElement messageEmail = driver.findElement(By.cssSelector("#email.mb-1"));
        String valueE = messageEmail.getText();
        Assert.assertEquals("Email:natalia@gmail.com",valueE);

        WebElement messageCurrent = driver.findElement(By.cssSelector("#currentAddress.mb-1"));
        String valueMC= messageCurrent.getText();
        Assert.assertEquals("Current Address :Sciastlivaia", valueMC);

        WebElement messagePM= driver.findElement(By.cssSelector("#permanentAddress.mb-1"));
        String valueMP = messagePM.getText();
        Assert.assertEquals("Permananet Address :Udacia",valueMP);

        driver.quit();
    }

    //AnnaByliginaTest
    @Ignore
    @Test

    public void testTextBox1() {
        WebDriver driver = new ChromeDriver();
        driver.get(" https://demoqa.com/text-box");
        String title = driver.getTitle();
        Assert.assertEquals("DEMOQA", title);

        WebElement textBox = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
        textBox.sendKeys("Краснодар, ул.Тихая, д.454");
        submitButton.click();
        WebElement message = driver.findElement(By.cssSelector("#currentAddress.mb-1"));
        String value = message.getText();
        Assert.assertEquals("Current Address :Краснодар, ул.Тихая, д.454", value);
        driver.quit();
    }
}
