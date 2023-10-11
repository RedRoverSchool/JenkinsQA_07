package school.redrover;

import org.openqa.selenium.By;
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
        private static final String url = "https://demoqa.com/automation-practice-form";
        private static final String firstName = "Oleg";
        private static final String lastName = "Komarov";
        private static final  String number = "89991114488";

        private static String getUrl() {
            return url;
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
    @Ignore
    @Test (description = "проверка содержания хидера")
    public void textHeaderForm() {
        WebDriver driver = new ChromeDriver();
        driver.get(variablesDmitryS.getUrl());
        WebElement mainHeaderForm = driver.findElement(By.xpath("//div[@class = 'main-header']"));
        String textMainHeaderForm = mainHeaderForm.getText();
        Assert.assertEquals(textMainHeaderForm, "Practice Form");
        WebElement titleRegistrationForm = driver.findElement(By.xpath("//h5"));
        String textTitleRegistrationForm = titleRegistrationForm.getText();
        Assert.assertEquals(textTitleRegistrationForm, "Student Registration Form");
        driver.quit();
    }

    @Ignore
    @Test (description = "проверка заполнения полей")
    public void positiveTest() {
        WebDriver driver = new ChromeDriver();
        driver.get(variablesDmitryS.getUrl());
        WebElement fieldFirstName = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        WebElement fieldLastName = driver.findElement(By.xpath("//input[@id = 'lastName']"));
        WebElement radioButtonGender = driver.findElement(By.xpath("//label[@for = 'gender-radio-1']"));
        WebElement fieldNumber = driver.findElement(By.xpath("//input[@id = 'userNumber']"));
        fieldFirstName.sendKeys(variablesDmitryS.firstName);
        fieldLastName.sendKeys(variablesDmitryS.lastName);
        radioButtonGender.click();
        fieldNumber.sendKeys(variablesDmitryS.number);
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.submit();
        WebElement resultValueStudentName = driver.findElement(By.xpath("//tr/td[2]"));
        String textResultValueStudentName = resultValueStudentName.getText();
        Assert.assertEquals(textResultValueStudentName, variablesDmitryS.firstName + " " + variablesDmitryS.lastName);
        driver.quit();
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

    @Ignore
    @Test
    public void testTextBoxName() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");

        String title = driver.getTitle();
        Assert.assertEquals( "DEMOQA",title);


        WebElement textBoxName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));

        textBoxName.sendKeys("Nadejda");
        submitButton.click();

        /*WebElement fullName = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        fullName.sendKeys("Mari");
        WebElement Email = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
        Email.sendKeys("mari@gmail.com");
        WebElement currentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        currentAddress.sendKeys("Teilor");
        WebElement permanentAddress = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
        permanentAddress.sendKeys("Molodejnaia");*/

        WebElement messageName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
        String value = messageName.getText();
        Assert.assertEquals("Name:Nadejda", value);


        driver.quit();
    }

    @Ignore
    @Test

    public void testTextBoxN () {

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

        WebElement messageEmail = driver.findElement(By.className("//*[@id=\"email\"]"));
        String valueE = messageEmail.getText();
        Assert.assertEquals("Email:natalia@gmail.com",value);

        WebElement messageCurrent = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        String valueMC= messageCurrent.getText();
        Assert.assertEquals("Current Address :Sciastlivaia", value);


        WebElement messagePM= driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
        String valueMP = messagePM.getText();
        Assert.assertEquals("Permananet Address :Udacia",value);


        driver.quit();
    }
}
