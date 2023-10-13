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

    @Ignore
    @Test
    public void aboutUsTest(){
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        WebElement usernameInput = driver.findElement(By.xpath(" //a[@href=contains(text(), \"About Us\")]"));
        usernameInput.click();

        WebElement greetings = driver.findElement(By.xpath("//h1[@class=\"title\"]"));
        Assert.assertEquals(greetings.getText(), "ParaSoft Demo Website");
        driver.quit();
    }

    /**
     * DmitryS. Тесты
     */
    // region DmitryS. Добавляю в данный блок тесты.
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
    @Test (description = "позитивный кейс")
    public void testPositiveTest() throws InterruptedException {
        getDriver().get(variablesDmitryS.getUrl());
        WebElement fieldFirstName = getDriver().findElement(By.xpath("//input[@id = 'firstName']"));
        WebElement fieldLastName = getDriver().findElement(By.xpath("//input[@id = 'lastName']"));
        WebElement radioButtonGender = getDriver().findElement(By.xpath("//label[@for = 'gender-radio-1']"));
        WebElement fieldNumber = getDriver().findElement(By.xpath("//input[@id = 'userNumber']"));
        fieldFirstName.sendKeys(variablesDmitryS.FIRST_NAME);
        fieldLastName.sendKeys(variablesDmitryS.LAST_NAME);
        radioButtonGender.click();
        fieldNumber.sendKeys(variablesDmitryS.NUMBER);
        WebElement submitButton = getDriver().findElement(By.id("submit"));
        submitButton.submit();
        WebElement resultValueStudentName = getDriver().findElement(By.xpath("//tr/td[2]"));
        String textResultValueStudentName = resultValueStudentName.getText();
        Thread.sleep(5000);
        String answer = variablesDmitryS.FIRST_NAME + " " + variablesDmitryS.LAST_NAME;
        Assert.assertEquals(textResultValueStudentName, answer);
    }
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
        WebElement fieldHobbiesSport = getDriver().findElement(By.xpath("//label[@for='hobbies-checkbox-2']"));
        WebElement fieldCurrentAddress = getDriver().findElement(By.xpath("//textarea[@id = 'currentAddress']"));
        fieldFirstName.sendKeys(variablesDmitryS.FIRST_NAME);
        fieldLastName.sendKeys(variablesDmitryS.LAST_NAME);
        fieldEmail.sendKeys(variablesDmitryS.EMAIL);
        Thread.sleep(5000);
        radioButtonGender.click();
        fieldNumber.sendKeys(variablesDmitryS.NUMBER);
        fieldDateOfBirth.click();
        fieldDateOfBirth.clear();
        Thread.sleep(5000);
        fieldDateOfBirth.sendKeys("Aug 2023-11");
        Thread.sleep(5000);
        fieldSubjects.sendKeys("c");
        fieldSubjects.sendKeys(Keys.ENTER);
        fieldHobbiesSport.click();
        fieldCurrentAddress.sendKeys(variablesDmitryS.CURRENT_ADDRESS);

//        WebElement fieldSelectState = getDriver().findElement(By.xpath("//*[@id='state']/div/div[1]/div[1]"));
//        Thread.sleep(2000);
//        fieldSelectState.click();
//        fieldSelectState.sendKeys(Keys.ENTER);

//        WebElement fieldChoiceSelectState = driver.findElement(By.cssSelector("//*[@id='state']/div/div[1]/div[1]/*[contains(string(), 'Haryana')]"));
//        fieldChoiceSelectState.click();

        Thread.sleep(5000);
        WebElement submitButton = getDriver().findElement(By.id("submit"));
        submitButton.submit();
        WebElement resultValueStudentName = getDriver().findElement(By.xpath("//tr/td[2]"));
        String textResultValueStudentName = resultValueStudentName.getText();
        Assert.assertEquals(textResultValueStudentName, variablesDmitryS.FIRST_NAME + " " + variablesDmitryS.LAST_NAME);
    }
    // endregion

    // region AkiMiraTest
    @Test (description = "Test of Text-Box 'Name'")
    public void testTextBox () {

        getDriver().get("https://demoqa.com/text-box");

        String title = getDriver().getTitle();
        Assert.assertEquals("DEMOQA", title);

        WebElement textBox = getDriver().findElement(By.xpath("//*[@id=\"userName\"]"));
        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id=\"submit\"]"));

        textBox.sendKeys("Oleg");
        submitButton.click();

        WebElement message = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        String value = message.getText();
        Assert.assertEquals("Name:Oleg", value);

    }
    @Test (description = "Test of Text-Box 'Current Address'")
    public void testTextBoxCurrentAddress () {
        getDriver().get("https://demoqa.com/text-box");

        String title = getDriver().getTitle();
        Assert.assertEquals("DEMOQA", title);

        WebElement textBox = getDriver().findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id=\"submit\"]"));

        textBox.sendKeys("Russian Federation");
        submitButton.click();

        WebElement message = getDriver().findElement(By.cssSelector("#currentAddress.mb-1"));
        String value = message.getText();
        Assert.assertEquals("Current Address :Russian Federation", value);

    }
    // endregion
}
