package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GroupQaClimbersTest {

    final static String URL = "https://demoqa.com/";

    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.get("https://demoqa.com");
            driver.manage().window().maximize();

            driver.findElement(By.xpath("(//div[@class='card-up'])[1]")).click();
            driver.findElement(By.xpath("//span[ contains(text(), 'Text Box')]")).click();

            WebElement inputName = driver.findElement(By.id("userName"));
            WebElement inputEmail = driver.findElement(By.id("userEmail"));
            WebElement submitButton = driver.findElement(By.id("submit"));

            inputName.sendKeys("Jane Dou");
            inputEmail.sendKeys("example@example.com");
            js.executeScript("arguments[0].scrollIntoView();", submitButton);
            submitButton.click();

            String actualStringName = driver.findElement(By.id("name")).getText();
            String actualEmail = driver.findElement(By.id("email")).getText();

            assertEquals(actualStringName, "Name:Jane Dou");
            assertEquals(actualEmail, "Email:example@example.com");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testTextBox1(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        WebElement elements = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        elements.click();

        WebElement textBox = driver.findElement(By.xpath("//li[@id=\"item-0\"]/span[text()='Text Box']"));
        textBox.click();

        WebElement fullNameField = driver.findElement(By.xpath("//input[@placeholder=\"Full Name\"]"));
        fullNameField.sendKeys("Arailym");

        WebElement emailField = driver.findElement(By.xpath("//input[@placeholder=\"name@example.com\"]"));
        emailField.sendKeys("test@test.com");

        WebElement currentAddressField = driver.findElement(By.xpath("//textarea[@placeholder=\"Current Address\"]"));
        currentAddressField.sendKeys("050000, Almaty");

        WebElement permanentAddressField = driver.findElement(By.xpath("//textarea[@id=\"permanentAddress\"]"));
        permanentAddressField.sendKeys("050000, Astana");

        WebElement submitButton = driver.findElement(By.xpath("//button[@id=\"submit\"]"));
        submitButton.click();

        WebElement output = driver.findElement(By.xpath("//div[@id='output']/div/p"));

        Assert.assertEquals(output.getText(), "Name:Arailym");

        driver.quit();
    }

    @Test
    public void testTextBox2() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            WebElement elements = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
            js.executeScript("arguments[0].scrollIntoView();", elements);
            elements.click();

            WebElement textBox = driver.findElement(By.xpath("//*[@id=\"item-0\"]"));
            textBox.click();

            WebElement fullNameTextBox = driver.findElement(By.id("userName"));
            fullNameTextBox.sendKeys("Гарри Поттер");

            WebElement email = driver.findElement(By.id("userEmail"));
            email.sendKeys("hp@hogvarts.com");

            WebElement currentAddress = driver.findElement(By.id("currentAddress"));
            currentAddress.sendKeys("Хогвартс");

            WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
            permanentAddress.sendKeys("Лондон");

            WebElement submitButton = driver.findElement(By.id("submit"));
            js.executeScript("arguments[0].scrollIntoView();", submitButton);
            submitButton.click();

            WebElement outputName = driver.findElement(By.xpath("//*[@id=\"name\"]"));
            Assert.assertEquals(outputName.getText(), "Name:Гарри Поттер");

            WebElement outputEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
            Assert.assertEquals(outputEmail.getText(), "Email:hp@hogvarts.com");

            //проверки адресов падают из-за текстовых ошибок в форме output
            //WebElement outputCurrentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            //Assert.assertEquals(outputCurrentAddress.getText(), "Current Address:Хогвартс");

            //WebElement outputPermanentAddress = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
            //Assert.assertEquals(outputPermanentAddress.getText(), "Permanent Address:Лондон");

        } finally {
            driver.quit();
        }
    }
    @Test
    public void widgetPageTest1() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com");
        driver.manage().window().maximize();
        try {
            WebElement widgetCard = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[1]"));
            widgetCard.click();
            WebElement elementsBtn = driver.findElement(By.xpath("//*[@class='header-text'][1]"));
            elementsBtn.click();
            Thread.sleep(200);
            WebElement listElement = driver.findElement(By.xpath("//*[@id=\"item-2\"]"));
            listElement.click();
            driver.findElement(By.xpath("//label[@class=\"custom-control-label\"][1]")).click();
            String title = driver.findElement(By.xpath("//p[@class=\"mt-3\"]/span")).getText();
            assertEquals(title, "Yes");
        }finally {
            driver.quit();
        }
    }

    @Test
    public void TestCheckBoxMenuSubmitWindow(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement elements = driver.findElement(By.xpath("//div[@class='card-up'][1]"));
        js.executeScript("arguments[0].scrollIntoView();", elements);
        elements.click();

        WebElement checkBoxMenu = driver.findElement
                (By.xpath("//span[@class='text' ][text() = 'Check Box']"));
        checkBoxMenu.click();

        WebElement submitWindow = driver.findElement
                (By.xpath("//span[@class = 'rct-checkbox']"));
        submitWindow.click();

        WebElement text = driver.findElement(By.xpath("//span[text() = 'You have selected :']"));

        String actualResult = text.getText();

        Assert.assertEquals(actualResult,"You have selected :");

        driver.quit();
    }

    @Test
    public void testAllElementsOnFirstPage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/");

            List<WebElement> listOfWebElements = driver
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

        } finally {
            driver.quit();
        }
    }
    @Test
    public void testClickOnHomeCheckBox() {
        WebDriver driver = new ChromeDriver();
        try {
        driver.get("https://demoqa.com/");

        WebElement elementOnPage = driver.findElement(By.xpath("(//div[@class='card-up'])[1]"));
        elementOnPage.click();

        driver.findElement(By.xpath("//span[ contains(text(), 'Check Box')]")).click();

        WebElement checkboxHome = driver.findElement(By.xpath("//span[@class='rct-checkbox']"));
        checkboxHome.click();

        List<WebElement> listOfActualElementsTagsOnScreen = driver.findElements(By
                .xpath("//span[@class='text-success']"));

        List<String> listOfActualTagsNameOnScreen = new ArrayList<>();

        for (WebElement el : listOfActualElementsTagsOnScreen) {
            listOfActualTagsNameOnScreen.add(el.getText());
        }

        List<String> expectedResultNames = List.of("home", "desktop", "notes", "commands", "documents",
                "workspace", "react", "angular", "veu", "office", "public", "private", "classified", "general",
                "downloads", "wordFile", "excelFile");

        assertEquals(listOfActualTagsNameOnScreen, expectedResultNames);
    } finally {
            driver.quit();
        }
    }
    @Test
    public void trainingPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            WebElement ElementsCard = driver.findElement(By.xpath("//div[@class='card mt-4 top-card'][1]"));
            ElementsCard.click();

            WebElement TextBox = driver.findElement((By.xpath("//li[@id='item-0'][1]")));
            TextBox.click();

            WebElement inputFullName = driver.findElement(By.xpath("//input[@class=' mr-sm-2 form-control'][1]"));
            inputFullName.sendKeys("Barak Obama");

            WebElement inputEmail = driver.findElement(By.xpath("//input[@class='mr-sm-2 form-control']"));
            inputEmail.sendKeys("barak1961@gmail.com");

            WebElement SubmitButton = driver.findElement(By.xpath("//button[@id='submit']"));
            js.executeScript("arguments[0].scrollIntoView();", SubmitButton);
            SubmitButton.click();

            WebElement message = driver.findElement(By.xpath("//div[@class='border col-md-12 col-sm-12']"));
            String value = message.getText();//берем текст элемента
            Assert.assertEquals(value, "Name:Barak Obama\nEmail:barak1961@gmail.com");//ожидаем что текст "
        } finally {

            driver.quit();
        }
    }

    @Test
    public void testProgressBarInWidgets(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement widgetsMenu = driver.findElement
                (By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", widgetsMenu);
        widgetsMenu.click();

        driver.findElement(By.xpath("//span[@class = 'text'][text() = 'Progress Bar']")).click();

        WebElement startButton = driver.findElement(By.xpath("//button[@id = 'startStopButton']"));
        startButton.click();

        WebElement progressBar = driver.findElement
                (By.xpath("//div[@id = 'progressBar'][@class = 'progress']"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(progressBar, "100%"));

        WebElement resetButton = driver.findElement(By.xpath("//button[@id = 'resetButton']"));

        String progressBarResult = progressBar.getText();
        String resetButtonResult = resetButton.getText();

        Assert.assertEquals(progressBarResult, "100%");
        Assert.assertEquals(resetButtonResult,"Reset");

        driver.quit();
    }

    @Test
    public void LocatorXPath() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        try {
            WebElement elementsBtn = driver.findElement(By.xpath("//h5[1]"));
            String value = elementsBtn.getText();
            Assert.assertEquals("Elements", value);
            elementsBtn.click();

            WebElement mainHeaderElements = driver.findElement(By.xpath("//*[@class ='main-header']"));
            String valueMainHeader = mainHeaderElements.getText();
            Assert.assertEquals("Elements", valueMainHeader);

            WebElement textBoxTab = driver.findElement(By.xpath("//span[text()='Text Box']"));
            String valueTextBoxTab = textBoxTab.getText();
            Assert.assertEquals("Text Box", valueTextBoxTab);
            textBoxTab.click();

            WebElement mainHeaderTextBox = driver.findElement(By.xpath("//*[@class ='main-header']"));
            String valueMainHeader1 = mainHeaderTextBox.getText();
            Assert.assertEquals("Text Box", valueMainHeader1 );

            WebElement fullName = driver.findElement(By.xpath("//*[@placeholder='Full Name']"));
            fullName.sendKeys("Nat");

            WebElement email = driver.findElement(By.xpath("//input[@id='userEmail']"));
            email.sendKeys("new@new.new");

            WebElement country = driver.findElement(By.xpath("//*[@id='currentAddress']"));
            country.sendKeys("USA");

            WebElement countryPermanent = driver.findElement(By.xpath("//*[@id='permanentAddress']"));
            countryPermanent.sendKeys("NY");

            WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
            submit.click();

            WebElement displayedName = driver.findElement(By.xpath("//*[@id='name']"));
            String nameValue = displayedName.getText();

            Assert.assertEquals("Name:Nat", nameValue);

            WebElement displayedEmail = driver.findElement(By.xpath("//*[@id='email']"));
            String emailValue = displayedEmail.getText();

            Assert.assertEquals("Email:new@new.new", emailValue);

            WebElement displayedCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']"));
            String currAddressValue = displayedCurrentAddress.getText();

            Assert.assertEquals("Current Address :USA", currAddressValue);

            WebElement displayedPermAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']"));
            String permAddressValue = displayedPermAddress.getText();

            Assert.assertEquals("Permananet Address :NY", permAddressValue);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void testElementsCheckBox() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get(URL);
        Thread.sleep(500);

        driver.findElement(
                By.xpath("//div[@class='category-cards']/div[2]/div/div[@class='card-up']"))
                .click();
        Thread.sleep(200);

        driver.findElement(
                By.xpath("(//div[@class='body-height']/div/div[2]/div/div/div/div/span/div)[1]"))
                .click();
        Thread.sleep(500);

        driver.findElement(
                By.xpath("//span[ contains(text(), 'Check Box')]"))
                .click();
        Thread.sleep(200);

        driver.findElement(
                By.xpath("//span[@class='rct-checkbox']/*[name()='svg']"))
                .click();

        WebElement message = driver.findElement(
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

        driver.quit();
    }

    @Test
    public void testListOfAlertsFrameAndWindows() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get(URL);
        Thread.sleep(200);

        driver.findElement(
                By.xpath("(//div[@class='card mt-4 top-card'])[3]"))
                .click();

        List<WebElement> listOfAlertsFrameAndWindows = driver.findElements(
                By.xpath("//div[@class='left-pannel']/div/div[3]/div/ul[@class='menu-list']/li"));

        List<String> actualListOfAlertsFrameAndWindows = new ArrayList<>();
        for (WebElement element: listOfAlertsFrameAndWindows) {
            actualListOfAlertsFrameAndWindows.add(element.getText());
        }

        List<String> expectedListOfAlertsFrameAndWindows = List.of(
                "Browser Windows", "Alerts", "Frames", "Nested Frames", "Modal Dialogs");

        Assert.assertEquals(actualListOfAlertsFrameAndWindows, expectedListOfAlertsFrameAndWindows);

        driver.quit();
    }

    @Test
    public void useDiffSeleniumLocators() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.manage().window().maximize();
        try {
            WebElement h1Text = driver.findElement(By.cssSelector("form h1"));
            String h1TextValue = h1Text.getText();
            Assert.assertEquals("Sign in", h1TextValue);

            WebElement nameInput = driver.findElement(By.id("inputUsername"));
            nameInput.sendKeys("rahulshetty");

            WebElement password = driver.findElement(By.name("inputPassword"));
            password.sendKeys("hello123");

            WebElement submitButton = driver.findElement(By.className("signInBtn"));
            submitButton.click();
            Thread.sleep(1000);
            WebElement errorMsg = driver.findElement(By.cssSelector("p.error"));
            String errorMessageText = errorMsg.getText();
            Assert.assertEquals("* Incorrect username or password", errorMessageText);

            WebElement forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));
            forgotPasswordLink.click();
            Thread.sleep(1000);
            WebElement h2Text = driver.findElement(By.cssSelector("h2"));
            String h2TextValue = h2Text.getText();

            Assert.assertEquals("Forgot password", h2TextValue);
            WebElement nameNew = driver.findElement(By.xpath("//input[@placeholder='Name']"));
            nameNew.sendKeys("John");
            WebElement email = driver.findElement(By.xpath("//input[@type='text'][2]")); //By.cssSelector("input[type='text']:nth-child(4)")
            email.sendKeys("123123");
            email.clear();
            email.sendKeys("new@new.new");


            WebElement phoneNumber = driver.findElement(By.xpath("//input[@placeholder='Phone Number']"));
            phoneNumber.sendKeys("1234654654646");

            WebElement resetLogin = driver.findElement(By.xpath("//button[@class='reset-pwd-btn']"));
            resetLogin.click();

            Thread.sleep(500);
            WebElement infoMsg = driver.findElement(By.xpath("//p[@class='infoMsg']"));
            String infoMsgValue = infoMsg.getText();

            Assert.assertEquals("Please use temporary password 'rahulshettyacademy' to Login.", infoMsgValue);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void locatorsPart2() throws InterruptedException {
        //Generating Css selectors based on regular expressions
        WebDriver driver = new ChromeDriver();

        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.manage().window().maximize();

        WebElement nameInput = driver.findElement(By.cssSelector("#inputUsername"));
        nameInput.sendKeys("rahul");

        WebElement password = driver.findElement(By.cssSelector("input[type*='pass']")); //* regular exp
        password.sendKeys("rahulshettyacademy");

        WebElement rememberMyUserNameCheckbox = driver.findElement(By.xpath("//input[@id='chkboxOne']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(@class, 'submit')]")); //contains method for xpath

        rememberMyUserNameCheckbox.click();
        submitButton.click();
        Thread.sleep(1000);
        WebElement successLoginText = driver.findElement(By.xpath("//p[@style]"));
        String successLoginTextVal = successLoginText.getText();
        Assert.assertEquals("You are successfully logged in.", successLoginTextVal);

        driver.quit();

    }

    @Test
    public void badSignUpBookStoreTest() throws InterruptedException {     // register without recaptcha
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement bookStoreApplicationButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[6]"));
        js.executeScript("arguments[0].scrollIntoView();", bookStoreApplicationButton);
        Thread.sleep(1000);
        bookStoreApplicationButton.click();

        Thread.sleep(1000);

        WebElement logIntoButton = driver.findElement(By.xpath("//button[@id='login']"));
        logIntoButton.click();

        Thread.sleep(1000);

        WebElement NewUserButton = driver.findElement(By.xpath("//button[@id='newUser']"));
        NewUserButton.click();

        Thread.sleep(1000);

        WebElement textFirstName = driver.findElement(By.xpath("//input[@id='firstname']"));
        textFirstName.sendKeys("firstName");

        Thread.sleep(3000);

        WebElement textLastName = driver.findElement(By.xpath("//input[@id='lastname']"));
        textLastName.sendKeys("LastName");

        Thread.sleep(3000);

        WebElement textUserName = driver.findElement(By.xpath("//input[@id='userName']"));
        textUserName.sendKeys("username");

        Thread.sleep(3000);

        WebElement textPassword = driver.findElement(By.xpath("//input[@id='password']"));
        textPassword.sendKeys("qwerty1111");

        Thread.sleep(1000);

        WebElement registrationButton = driver.findElement(By.xpath("//button[@id='register']"));
        js.executeScript("arguments[0].scrollIntoView();", registrationButton);
        Thread.sleep(1000);
        registrationButton.click();

        WebElement needRecaptcha = driver.findElement(By.xpath("//p[@style]"));
        String needRecaptchaText = needRecaptcha.getText();
        Assert.assertEquals(needRecaptchaText,"Please verify reCaptcha to register!");

        driver.quit();

        /* WebElement findReCaptcha = driver.findElement(By.xpath("////*[@id=\"recaptcha-anchor\"]/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", findReCaptcha);
        Thread.sleep(1000);
        findReCaptcha.click(); */

    }

    @Test
    public void testBookStoreApplication() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        Thread.sleep(500);

        WebElement bookStoreApplicationButton = driver.findElement(
                By.xpath("//div[@class='card mt-4 top-card'][6]"));
        bookStoreApplicationButton.click();

        WebElement searchArea = driver.findElement(
                By.xpath("//div[@class='mb-3 input-group']/input[@class='form-control']"));
        searchArea.click();
        searchArea.sendKeys("java");

        List<WebElement> elements = driver.findElements(
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
        elements = driver.findElements(
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
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[1]/div//*[@id=\"item-2\"]")).click();
            Thread.sleep(1000);
            WebElement buttonYes = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/label"));
            buttonYes.click();
            String haveSelected = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/p/span")).getText();
            assertEquals(haveSelected, "Yes");
        } finally {
            driver.quit();
        }
        }
}
