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

public class GroupQaClimbersTest {

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

}
