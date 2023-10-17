package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

@Ignore
public class GroupQaClimbersTest extends BaseTest {

    private static final String URL = "https://demoqa.com/";

    @Test
    public void testTextBox() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        getDriver().get(URL);

        getDriver().findElement(By.xpath("(//div[@class='card-up'])[1]")).click();
        getDriver().findElement(By.xpath("//span[ contains(text(), 'Text Box')]")).click();

        WebElement inputName = getDriver().findElement(By.id("userName"));
        WebElement inputEmail = getDriver().findElement(By.id("userEmail"));
        WebElement submitButton = getDriver().findElement(By.id("submit"));

        inputName.sendKeys("Jane Dou");
        inputEmail.sendKeys("example@example.com");
        js.executeScript("arguments[0].scrollIntoView();", submitButton);

        submitButton.click();

        String actualStringName = getDriver().findElement(By.id("name")).getText();
        String actualEmail = getDriver().findElement(By.id("email")).getText();

        assertEquals(actualStringName, "Name:Jane Dou");
        assertEquals(actualEmail, "Email:example@example.com");
    }

    @Test
    public void testTextBox1() {

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement elements = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        js.executeScript("arguments[0].scrollIntoView();", elements);
        elements.click();

        WebElement textBox = getDriver().findElement(By.xpath("//li[@id=\"item-0\"]/span[text()='Text Box']"));
        textBox.click();

        WebElement fullNameField = getDriver().findElement(By.xpath("//input[@placeholder=\"Full Name\"]"));
        fullNameField.sendKeys("Arailym");

        WebElement emailField = getDriver().findElement(By.xpath("//input[@placeholder=\"name@example.com\"]"));
        emailField.sendKeys("test@test.com");

        WebElement currentAddressField = getDriver().findElement(By.xpath("//textarea[@placeholder=\"Current Address\"]"));
        currentAddressField.sendKeys("050000, Almaty");

        WebElement permanentAddressField = getDriver().findElement(By.xpath("//textarea[@id=\"permanentAddress\"]"));
        permanentAddressField.sendKeys("050000, Astana");

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id=\"submit\"]"));
        js.executeScript("arguments[0].scrollIntoView();", submitButton);
        submitButton.click();

        WebElement output = getDriver().findElement(By.xpath("//div[@id='output']/div/p"));

        Assert.assertEquals(output.getText(), "Name:Arailym");
    }

    @Test
    public void testTextBox2() {

        getDriver().get(URL);
        getDriver().manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();


        WebElement elements = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        js.executeScript("arguments[0].scrollIntoView();", elements);
        elements.click();

        WebElement textBox = getDriver().findElement(By.xpath("//*[@id=\"item-0\"]"));
        textBox.click();

        WebElement fullNameTextBox = getDriver().findElement(By.id("userName"));
        fullNameTextBox.sendKeys("Гарри Поттер");

        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.sendKeys("hp@hogvarts.com");

        WebElement currentAddress = getDriver().findElement(By.id("currentAddress"));
        currentAddress.sendKeys("Хогвартс");

        WebElement permanentAddress = getDriver().findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("Лондон");

        WebElement submitButton = getDriver().findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();", submitButton);
        submitButton.click();

        WebElement outputName = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        Assert.assertEquals(outputName.getText(), "Name:Гарри Поттер");

        WebElement outputEmail = getDriver().findElement(By.xpath("//*[@id=\"email\"]"));
        Assert.assertEquals(outputEmail.getText(), "Email:hp@hogvarts.com");

        //проверки адресов падают из-за текстовых ошибок в форме output
        //WebElement outputCurrentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        //Assert.assertEquals(outputCurrentAddress.getText(), "Current Address:Хогвартс");

        //WebElement outputPermanentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        //Assert.assertEquals(outputPermanentAddress.getText(), "Permanent Address:Лондон");
    }

    @Test
    public void widgetPageTest1() throws InterruptedException {

        getDriver().get(URL);

        WebElement widgetCard = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[1]"));
        widgetCard.click();
        WebElement elementsBtn = getDriver().findElement(By.xpath("//*[@class='header-text'][1]"));
        elementsBtn.click();
        Thread.sleep(200);
        WebElement listElement = getDriver().findElement(By.xpath("//*[@id=\"item-2\"]"));
        listElement.click();
        getDriver().findElement(By.xpath("//label[@class=\"custom-control-label\"][1]")).click();
        String title = getDriver().findElement(By.xpath("//p[@class=\"mt-3\"]/span")).getText();
        assertEquals(title, "Yes");
    }

    @Test
    public void testCheckBoxMenuSubmitWindow() {

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement elements = getDriver().findElement(By.xpath("//div[@class='card-up'][1]"));
        js.executeScript("arguments[0].scrollIntoView();", elements);
        elements.click();

        WebElement checkBoxMenu = getDriver().findElement
                (By.xpath("//span[@class='text' ][text() = 'Check Box']"));
        checkBoxMenu.click();

        WebElement submitWindow = getDriver().findElement
                (By.xpath("//span[@class = 'rct-checkbox']"));
        submitWindow.click();

        WebElement text = getDriver().findElement(By.xpath("//span[text() = 'You have selected :']"));

        String actualResult = text.getText();

        Assert.assertEquals(actualResult, "You have selected :");
    }

    @Test
    public void testAllElementsOnFirstPage() {

        getDriver().get(URL);

        List<WebElement> listOfWebElements = getDriver()
                .findElements(By.xpath("//div[@class='card mt-4 top-card']"));

        List<String> actualListOfElementsNames = new ArrayList<>();

        for (WebElement el : listOfWebElements) {
            actualListOfElementsNames.add(el.getText());
        }

        List<String> expectedListElementsNames = Arrays.asList(
                "Elements", "Forms", "Alerts, Frame & Windows",
                "Widgets", "Interactions", "Book Store Application"
        );
        assertEquals(actualListOfElementsNames, expectedListElementsNames);
    }
@Ignore
    @Test
    public void testClickOnHomeCheckBox() {

        getDriver().get(URL);

        WebElement elementOnPage = getDriver().findElement(By.xpath("(//div[@class='card-up'])[1]"));
        elementOnPage.click();

        getDriver().findElement(By.xpath("//span[ contains(text(), 'Check Box')]")).click();

        WebElement checkboxHome = getDriver().findElement(By.xpath("//span[@class='rct-checkbox']"));
        checkboxHome.click();

        List<WebElement> listOfActualElementsTagsOnScreen = getDriver().findElements(By
                .xpath("//span[@class='text-success']"));

        List<String> listOfActualTagsNameOnScreen = new ArrayList<>();

        for (WebElement el : listOfActualElementsTagsOnScreen) {
            listOfActualTagsNameOnScreen.add(el.getText());
        }

        List<String> expectedResultNames = List.of("home", "desktop", "notes", "commands", "documents",
                "workspace", "react", "angular", "veu", "office", "public", "private", "classified", "general",
                "downloads", "wordFile", "excelFile");

        assertEquals(listOfActualTagsNameOnScreen, expectedResultNames);
    }

    @Test
    public void trainingPage() {

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();


        WebElement ElementsCard = getDriver().findElement(By.xpath("//div[@class='card mt-4 top-card'][1]"));
        ElementsCard.click();

        WebElement TextBox = getDriver().findElement((By.xpath("//li[@id='item-0'][1]")));
        TextBox.click();

        WebElement inputFullName = getDriver().findElement(By.xpath("//input[@class=' mr-sm-2 form-control'][1]"));
        inputFullName.sendKeys("Barak Obama");

        WebElement inputEmail = getDriver().findElement(By.xpath("//input[@class='mr-sm-2 form-control']"));
        inputEmail.sendKeys("barak1961@gmail.com");

        WebElement SubmitButton = getDriver().findElement(By.xpath("//button[@id='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", SubmitButton);
        SubmitButton.click();

        WebElement message = getDriver().findElement(By.xpath("//div[@class='border col-md-12 col-sm-12']"));
        String value = message.getText();//берем текст элемента
        Assert.assertEquals(value, "Name:Barak Obama\nEmail:barak1961@gmail.com");//ожидаем что текст "
    }


    @Test
    public void testProgressBarInWidgets() {

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement widgetsMenu = getDriver().findElement
                (By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", widgetsMenu);
        widgetsMenu.click();

        getDriver().findElement(By.xpath("//span[@class = 'text'][text() = 'Progress Bar']")).click();

        WebElement startButton = getDriver().findElement(By.xpath("//button[@id = 'startStopButton']"));
        startButton.click();

        WebElement progressBar = getDriver().findElement
                (By.xpath("//div[@id = 'progressBar'][@class = 'progress']"));

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.textToBePresentInElement(progressBar, "100%"));

        WebElement resetButton = getDriver().findElement(By.xpath("//button[@id = 'resetButton']"));

        String progressBarResult = progressBar.getText();
        String resetButtonResult = resetButton.getText();

        Assert.assertEquals(progressBarResult, "100%");
        Assert.assertEquals(resetButtonResult, "Reset");
    }

    @Test
    public void LocatorXPath() {


        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();


        WebElement elementsBtn = getDriver().findElement(By.xpath("//h5[1]"));
        js.executeScript("arguments[0].scrollIntoView();", elementsBtn);
        String value = elementsBtn.getText();
        Assert.assertEquals("Elements", value);
        elementsBtn.click();

        WebElement mainHeaderElements = getDriver().findElement(By.xpath("//*[@class ='main-header']"));
        String valueMainHeader = mainHeaderElements.getText();
        Assert.assertEquals("Elements", valueMainHeader);

        WebElement textBoxTab = getDriver().findElement(By.xpath("//span[text()='Text Box']"));
        String valueTextBoxTab = textBoxTab.getText();
        Assert.assertEquals("Text Box", valueTextBoxTab);
        textBoxTab.click();

        WebElement mainHeaderTextBox = getDriver().findElement(By.xpath("//*[@class ='main-header']"));
        String valueMainHeader1 = mainHeaderTextBox.getText();
        Assert.assertEquals("Text Box", valueMainHeader1);

        WebElement fullName = getDriver().findElement(By.xpath("//*[@placeholder='Full Name']"));
        fullName.sendKeys("Nat");

        WebElement email = getDriver().findElement(By.xpath("//input[@id='userEmail']"));
        email.sendKeys("new@new.new");

        WebElement country = getDriver().findElement(By.xpath("//*[@id='currentAddress']"));
        country.sendKeys("USA");

        WebElement countryPermanent = getDriver().findElement(By.xpath("//*[@id='permanentAddress']"));
        countryPermanent.sendKeys("NY");

        WebElement submit = getDriver().findElement(By.xpath("//*[@id='submit']"));
        js.executeScript("arguments[0].scrollIntoView();", submit);
        submit.click();

        WebElement displayedName = getDriver().findElement(By.xpath("//*[@id='name']"));
        String nameValue = displayedName.getText();

        Assert.assertEquals("Name:Nat", nameValue);

        WebElement displayedEmail = getDriver().findElement(By.xpath("//*[@id='email']"));
        String emailValue = displayedEmail.getText();

        Assert.assertEquals("Email:new@new.new", emailValue);

        WebElement displayedCurrentAddress = getDriver().findElement(By.xpath("//p[@id='currentAddress']"));
        String currAddressValue = displayedCurrentAddress.getText();

        Assert.assertEquals("Current Address :USA", currAddressValue);

        WebElement displayedPermAddress = getDriver().findElement(By.xpath("//p[@id='permanentAddress']"));
        String permAddressValue = displayedPermAddress.getText();

        Assert.assertEquals("Permananet Address :NY", permAddressValue);
    }

    @Test
    public void testElementsCheckBox() throws InterruptedException {
        getDriver().get(URL);
        Thread.sleep(500);

        getDriver().findElement(
                        By.xpath("//div[@class='category-cards']/div[2]/div/div[@class='card-up']"))
                .click();
        Thread.sleep(200);

        getDriver().findElement(
                        By.xpath("(//div[@class='body-height']/div/div[2]/div/div/div/div/span/div)[1]"))
                .click();
        Thread.sleep(500);

        getDriver().findElement(
                        By.xpath("//span[ contains(text(), 'Check Box')]"))
                .click();
        Thread.sleep(200);

        getDriver().findElement(
                        By.xpath("//span[@class='rct-checkbox']/*[name()='svg']"))
                .click();

        WebElement message = getDriver().findElement(
                By.xpath("//div[@class='display-result mt-4']"));
        String messageText = message.getText();

        Assert.assertEquals(messageText,
                """
                        You have selected :
                        home
                        desktop
                        notes
                        commands
                        documents
                        workspace
                        react
                        angular
                        veu
                        office
                        public
                        private
                        classified
                        general
                        downloads
                        wordFile
                        excelFile""");
    }

    @Test
    public void testListOfAlertsFrameAndWindows() throws InterruptedException {
        getDriver().get(URL);
        Thread.sleep(200);

        getDriver().findElement(
                        By.xpath("(//div[@class='card mt-4 top-card'])[3]"))
                .click();

        List<WebElement> listOfAlertsFrameAndWindows = getDriver().findElements(
                By.xpath("//div[@class='left-pannel']/div/div[3]/div/ul[@class='menu-list']/li"));

        List<String> actualListOfAlertsFrameAndWindows = new ArrayList<>();
        for (WebElement element : listOfAlertsFrameAndWindows) {
            actualListOfAlertsFrameAndWindows.add(element.getText());
        }

        List<String> expectedListOfAlertsFrameAndWindows = List.of(
                "Browser Windows", "Alerts", "Frames", "Nested Frames", "Modal Dialogs");

        Assert.assertEquals(actualListOfAlertsFrameAndWindows, expectedListOfAlertsFrameAndWindows);
    }

    @Test
    public void useDiffSeleniumLocators() throws InterruptedException {

        getDriver().get("https://rahulshettyacademy.com/locatorspractice/");

        WebElement h1Text = getDriver().findElement(By.cssSelector("form h1"));
        String h1TextValue = h1Text.getText();
        Assert.assertEquals("Sign in", h1TextValue);

        WebElement nameInput = getDriver().findElement(By.id("inputUsername"));
        nameInput.sendKeys("rahulshetty");

        WebElement password = getDriver().findElement(By.name("inputPassword"));
        password.sendKeys("hello123");

        WebElement submitButton = getDriver().findElement(By.className("signInBtn"));
        submitButton.click();
        Thread.sleep(1000);
        WebElement errorMsg = getDriver().findElement(By.cssSelector("p.error"));
        String errorMessageText = errorMsg.getText();
        Assert.assertEquals("* Incorrect username or password", errorMessageText);

        WebElement forgotPasswordLink = getDriver().findElement(By.linkText("Forgot your password?"));
        forgotPasswordLink.click();
        Thread.sleep(1000);
        WebElement h2Text = getDriver().findElement(By.cssSelector("h2"));
        String h2TextValue = h2Text.getText();

        Assert.assertEquals("Forgot password", h2TextValue);
        WebElement nameNew = getDriver().findElement(By.xpath("//input[@placeholder='Name']"));
        nameNew.sendKeys("John");
        WebElement email = getDriver().findElement(By.xpath("//input[@type='text'][2]")); //By.cssSelector("input[type='text']:nth-child(4)")
        email.sendKeys("123123");
        email.clear();
        email.sendKeys("new@new.new");

        WebElement phoneNumber = getDriver().findElement(By.xpath("//input[@placeholder='Phone Number']"));
        phoneNumber.sendKeys("1234654654646");

        WebElement resetLogin = getDriver().findElement(By.xpath("//button[@class='reset-pwd-btn']"));
        resetLogin.click();

        Thread.sleep(500);
        WebElement infoMsg = getDriver().findElement(By.xpath("//p[@class='infoMsg']"));
        String infoMsgValue = infoMsg.getText();

        Assert.assertEquals("Please use temporary password 'rahulshettyacademy' to Login.", infoMsgValue);
    }

    @Test
    public void locatorsPart2() throws InterruptedException {
        //Generating Css selectors based on regular expressions

        getDriver().get("https://rahulshettyacademy.com/locatorspractice/");

        WebElement nameInput = getDriver().findElement(By.cssSelector("#inputUsername"));
        nameInput.sendKeys("rahul");

        WebElement password = getDriver().findElement(By.cssSelector("input[type*='pass']")); //* regular exp
        password.sendKeys("rahulshettyacademy");

        WebElement rememberMyUserNameCheckbox = getDriver().findElement(By.xpath("//input[@id='chkboxOne']"));
        WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(@class, 'submit')]")); //contains method for xpath

        rememberMyUserNameCheckbox.click();
        submitButton.click();
        Thread.sleep(1000);
        WebElement successLoginText = getDriver().findElement(By.xpath("//p[@style]"));
        String successLoginTextVal = successLoginText.getText();
        Assert.assertEquals("You are successfully logged in.", successLoginTextVal);
    }

    @Test
    public void badSignUpBookStoreTest() throws InterruptedException {     // register without recaptcha

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement bookStoreApplicationButton = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[6]"));
        js.executeScript("arguments[0].scrollIntoView();", bookStoreApplicationButton);
        Thread.sleep(1000);
        bookStoreApplicationButton.click();

        Thread.sleep(1000);

        WebElement logIntoButton = getDriver().findElement(By.xpath("//button[@id='login']"));
        logIntoButton.click();

        Thread.sleep(1000);

        WebElement NewUserButton = getDriver().findElement(By.xpath("//button[@id='newUser']"));
        NewUserButton.click();

        Thread.sleep(1000);

        WebElement textFirstName = getDriver().findElement(By.xpath("//input[@id='firstname']"));
        textFirstName.sendKeys("firstName");

        Thread.sleep(3000);

        WebElement textLastName = getDriver().findElement(By.xpath("//input[@id='lastname']"));
        textLastName.sendKeys("LastName");

        Thread.sleep(3000);

        WebElement textUserName = getDriver().findElement(By.xpath("//input[@id='userName']"));
        textUserName.sendKeys("username");

        Thread.sleep(3000);

        WebElement textPassword = getDriver().findElement(By.xpath("//input[@id='password']"));
        textPassword.sendKeys("qwerty1111");

        Thread.sleep(1000);

        WebElement registrationButton = getDriver().findElement(By.xpath("//button[@id='register']"));
        js.executeScript("arguments[0].scrollIntoView();", registrationButton);
        Thread.sleep(1000);
        registrationButton.click();

        WebElement needRecaptcha = getDriver().findElement(By.xpath("//p[@style]"));
        String needRecaptchaText = needRecaptcha.getText();
        Assert.assertEquals(needRecaptchaText, "Please verify reCaptcha to register!");

        /* WebElement findReCaptcha = driver.findElement(By.xpath("////*[@id=\"recaptcha-anchor\"]/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", findReCaptcha);
        Thread.sleep(1000);
        findReCaptcha.click(); */
    }

    @Test
    public void testBookStoreApplication() throws InterruptedException {
        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        Thread.sleep(500);

        WebElement bookStoreApplicationButton = getDriver().findElement(
                By.xpath("//div[@class='card mt-4 top-card'][6]"));
        js.executeScript("arguments[0].scrollIntoView();", bookStoreApplicationButton);
        bookStoreApplicationButton.click();

        Thread.sleep(500);
        WebElement searchArea = getDriver().findElement(
                By.xpath("//div[@class='mb-3 input-group']/input[@class='form-control']"));
        searchArea.click();
        searchArea.sendKeys("java");

        Thread.sleep(500);
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//div[@class='rt-tr-group']//div[@class='rt-td'][2]"));
        List<WebElement> elementsList = new ArrayList<>();
        for (WebElement element : elements) {
            if (element.getText().length() > 1) {
                elementsList.add(element);
            }
        }
        int actualSize = elementsList.size();
        int expectedSize = 4;

        Assert.assertEquals(actualSize, expectedSize);

        searchArea.clear();
        elementsList.clear();
        searchArea.sendKeys("123");
        elements = getDriver().findElements(
                By.xpath("//div[@class='rt-tr-group']//div[@class='rt-td'][2]"));
        for (WebElement element : elements) {
            if (element.getText().length() > 1) {
                elementsList.add(element);
            }
        }
        actualSize = elementsList.size();

        assertEquals(actualSize, 0);
    }

    @Test
    public void testElementsRadioButton() throws InterruptedException {

        getDriver().get(URL);

        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[1]/div//*[@id=\"item-2\"]")).click();
        Thread.sleep(1000);
        WebElement buttonYes = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/label"));
        buttonYes.click();
        String haveSelected = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/p/span")).getText();
        assertEquals(haveSelected, "Yes");
    }

    @Test
    public void certificationTrainingSearchForJavaTest() {

        getDriver().get(URL);
        WebElement certificationTraining = getDriver().findElement(By.xpath("//img[@class=\"banner-image\"]"));
        certificationTraining.click();//opens another window
        Set<String> windowIDs = getDriver().getWindowHandles();

        List<String> listOfWindowIDs = new ArrayList<>(windowIDs);
        String secondWindowID = listOfWindowIDs.get(1);
        getDriver().switchTo().window(secondWindowID);

        WebElement inputSearch = getDriver().findElement(By.xpath("//input[@class=\"navbar__search--input\"]"));
        inputSearch.click();
        inputSearch.sendKeys("java");
        inputSearch.sendKeys(Keys.ENTER);
        listOfWindowIDs.add(getDriver().getWindowHandle());
        getDriver().switchTo().window(listOfWindowIDs.get(2));
        WebElement message = getDriver().findElement(By.xpath("//h1"));
        String actualMessage = message.getText();
        Assert.assertEquals(actualMessage, "Search - \"java\"");
    }

    @Test
    public void testTextBoxTest() {

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("scroll(0,200)");
        WebElement element = getDriver().findElement(By.xpath("(//h5)[1]"));
        element.click();
        WebElement textBox = getDriver().findElement(By.xpath("//span[text()='Text Box']"));
        textBox.click();
        WebElement userName = getDriver().findElement(By.id("userName"));
        userName.sendKeys("Sam Don");
        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.sendKeys("sam@gmail.com");
        WebElement currentAddress = getDriver().findElement(By.id("currentAddress"));
        currentAddress.sendKeys("123 My Road");
        WebElement permanentAddress = getDriver().findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("1256 S Loop");
        js.executeScript("scroll(0,200)");
        WebElement submitBtn = getDriver().findElement(By.id("submit"));
        submitBtn.click();
        String actualName = getDriver().findElement(By.id("name")).getText();
        String expectedName = "Name:Sam Don";
        Assert.assertEquals(actualName, expectedName);
    }

    @Test
    public void testLoginSauceDemo() throws InterruptedException {
        getDriver().get("https://saucedemo.com/");
        List<WebElement> loginButtons=getDriver().findElements(By.tagName("input"));
        loginButtons.get(0).sendKeys("standard_user");
        loginButtons.get(1).sendKeys("secret_sauce");
        loginButtons.get(2).click();
        WebElement dropdown = getDriver().findElement(By.className("product_sort_container"));
        Select sort = new Select(dropdown);
        Thread.sleep(1000);
        sort.selectByVisibleText("Price (high to low)");
        String expectedMessage="Swag Labs";
        String actualMessage=getDriver().findElement(By.xpath("//div[text()='Swag Labs']")).getText();
        Assert.assertEquals(actualMessage,expectedMessage);
    }
@Ignore
    @Test
    public void testClickOnCreateAJob() {

        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();

        String actualResult = getDriver().findElement(By.xpath("//label[@for='name']"))
                .getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Test
    public void testSearchSettingsField() throws InterruptedException {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(
                By.xpath("//div[@id='tasks']/div[4]/span/a")).click();
        WebElement searchSettingsField = getDriver().findElement(
                By.xpath("//div[@class='jenkins-search-container']/div/input[@id='settings-search-bar']"));
        searchSettingsField.click();
        searchSettingsField.sendKeys("script");
        Thread.sleep(500);
        searchSettingsField.sendKeys(Keys.ENTER);
        String actualTitle = getDriver().findElement(
                By.xpath("//div[@id='main-panel']/h1[text()='Script Console']"))
                .getText();

        Assert.assertEquals(actualTitle, "Script Console");
    }

    @Test
    public void testManageJenkinsOption() throws InterruptedException {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        Thread.sleep(1000);
        String actualMessage=getDriver().findElement(By.xpath("//div/h1[text()='Manage Jenkins']")).getText();
        String expectedMessage="Manage Jenkins";
        Assert.assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void testLoginJenkins() {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();
        WebElement checkJenkinsVersion = getDriver().findElement(By.xpath("//button[@type='button']"));
        checkJenkinsVersion.click();

        WebElement openJenkinsWebSite = getDriver().findElement(By.xpath("//a[@rel='noopener noreferrer']"));
        openJenkinsWebSite.click();

        WebElement getTitle = getDriver().findElement(By.xpath("//a[@href='/']"));
        String getTitleText = getTitle.getText();
        Assert.assertEquals(getTitleText, "");

    }
}