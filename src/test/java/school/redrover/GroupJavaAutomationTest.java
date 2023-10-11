package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URI;
import java.time.Duration;

import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.testng.Assert.assertEquals;

@Ignore
public class GroupJavaAutomationTest {

    @Test
    public void herokuappHomePageTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        String title = driver.getTitle();
        assertEquals(title, "The Internet");
        driver.quit();
    }

    @Test
    public void herokuAppAddRemoveTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/");
        try {
            WebElement addRemove = driver.findElement(By.xpath("//a[@href='/add_remove_elements/']"));
            addRemove.click();
            WebElement addElement = driver.findElement(By.xpath("//*[@id='content']/div/button"));
            addElement.click();
            //explicit expectation - declaration, initialization
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //explicitly wait for the visibility of the button element
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"elements\"]/button[1]")));
            //clicking on the button, this action will only happen when the button element is visible on the page
            driver.findElement(By.xpath("//*[@id=\"elements\"]/button[1]")).click();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();

    }

    @Test
    public void testTextEditor() {
        final String expectedText = "My text\nsecond row";
        WebDriver driver = new ChromeDriver();
        Wait<WebDriver> wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://the-internet.herokuapp.com/tinymce");

        wait5.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.xpath("//iframe[contains(@title, 'Text Area')]"))));

        WebElement editor = wait5.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//body[@id = 'tinymce']"))));
        wait5.until(ExpectedConditions.textToBe(By.xpath("//body[@id = 'tinymce']"), "Your content goes here."));
        editor.clear();
        editor.sendKeys(expectedText);

        String actualText = editor.getText();

        Assert.assertEquals(actualText, expectedText);
        driver.quit();

    }

    @Test
    public void herokuAppABTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        WebElement buttonABTesting = driver.findElement(By.xpath("//a[@href='/abtest']"));
        buttonABTesting.click();
        WebElement textABTest = driver.findElement(By.xpath("//h3[text()='A/B Test Control']"));
        String abTestTitle = textABTest.getText();
        assertEquals(abTestTitle, "A/B Test Control");
        driver.quit();
    }

    @Test
    public void herokuAppCheckBoxTest() {
        WebDriver driver = new ChromeDriver();
        SoftAssert softAssert = new SoftAssert();
        driver.get("https://the-internet.herokuapp.com/");
        WebElement buttonCheckBox = driver.findElement(By.xpath("//a[@href='/checkboxes']"));
        buttonCheckBox.click();
        WebElement textCheckBox = driver.findElement(By.xpath("//h3[text()='Checkboxes']"));
        String titleCheckBox = textCheckBox.getText();
        softAssert.assertEquals(titleCheckBox, "Checkboxes");
        List<WebElement> listCheckbox = driver.findElements(By.xpath("//input[@type='checkbox']"));
        softAssert.assertEquals(listCheckbox.size(), 2);
        softAssert.assertAll();
        driver.quit();
    }

    @Test
    public void testEntryAd() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/");

        WebElement entryAd = driver.findElement(By.xpath("//a[@href='/entry_ad']"));
        entryAd.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        WebElement pClose = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]"));
        pClose.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(pClose));

        Assert.assertFalse(pClose.isDisplayed());

        driver.quit();
    }

    @Test
    public void testAddElement() {
        List<String> expectedButtonsName = new ArrayList<>(List.of("Add Element","Delete"));

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        Wait<WebDriver> wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addButton = wait5.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text() = 'Add Element']")));
        addButton.click();

        List<WebElement> buttons = wait5.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//button"))));
        List<String> actualButtonsName = new ArrayList<>();
        for(WebElement button : buttons){
            actualButtonsName.add(button.getText());
        }

        Assert.assertEquals(actualButtonsName, expectedButtonsName);

        driver.quit();
    }

    @Test
    public void testBasicAuth() {
        WebDriver driver = new ChromeDriver();
        Wait<WebDriver> wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains("the-internet.herokuapp.com");
        ((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("admin", "admin"));
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        String authMessage = wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p"))).getText();

        Assert.assertEquals(authMessage, "Congratulations! You must have the proper credentials.");

        driver.quit();
    }

    @Test
    public void loginSuccessfulTest() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.manage().window().maximize();
        WebElement elementLogin = webDriver.findElement(By.xpath("//a[@href='/login']"));
        elementLogin.click();
        WebElement inputName = webDriver.findElement(By.xpath("//input[@name='username']"));
        WebElement inputPassword = webDriver.findElement(By.xpath("//input[@name='password']"));
        inputName.sendKeys("tomsmith");
        inputPassword.sendKeys("SuperSecretPassword!");
        WebElement buttonSubmit = webDriver.findElement(By.xpath("//button[@type='submit']"));
        buttonSubmit.click();
        WebElement messageTitle = webDriver.findElement(By.xpath("//div[@class='flash success']"));
        Assert.assertEquals(messageTitle
                        .getText()
                        .replaceAll("[×\n]", ""),
                "You logged into a secure area!");
        WebElement logout = webDriver.findElement(By.xpath("//*[@href='/logout']"));
        logout.click();
        webDriver.quit();
    }

    @Test
    public void loginEmptyNameTest() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.manage().window().maximize();
        WebElement elementLogin = webDriver.findElement(By.xpath("//a[@href='/login']"));
        elementLogin.click();
        WebElement inputPassword = webDriver.findElement(By.xpath("//input[@name='password']"));
        inputPassword.sendKeys("SuperSecretPassword!");
        WebElement buttonSubmit = webDriver.findElement(By.xpath("//button[@type='submit']"));
        buttonSubmit.click();
        WebElement messageTitle = webDriver.findElement(By.xpath("//div[@class='flash error']"));
        Assert.assertEquals(messageTitle
                        .getText()
                        .replaceAll("[×\n]", ""),
                "Your username is invalid!");
        webDriver.quit();
    }
    @Test
    public void testBasicAuthWithoutAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        String authMessage = driver.findElement(By.xpath("//h3/following-sibling::p")).getText();
        Assert.assertTrue(authMessage.contains("Congratulations"));
        driver.quit();
    }


    @Test
    public void checkBoxTest(){
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.manage().window().maximize();
        WebElement elementCheckBoxes = webDriver.findElement(By.xpath("//a[@href='/checkboxes']"));
        elementCheckBoxes.click();
        List<WebElement> elementFormCheckBoxes;
        WebElement checkBox1 = webDriver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkBox2 = webDriver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));
        elementFormCheckBoxes = List.of(checkBox1, checkBox2);
        for (WebElement item: elementFormCheckBoxes){
            if (!item.isSelected()){
                item.click();
            }
        }
        Assert.assertEquals(List.of(checkBox1.isSelected(),checkBox2.isSelected()), List.of(true,true));
        webDriver.quit();
    }
    @Test
    public void downloadFile(){
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.manage().window().maximize();
        String pathToSave = "C:\\Users\\48573\\Downloads\\";

        WebElement elementFileDownload = webDriver.findElement(By.xpath("//a[@href='/download']"));
        elementFileDownload.click();

        WebElement firstFile = webDriver.findElement(By.xpath("//div[@id='content']/div/a[1]"));
        firstFile.click();

        String nameFile = firstFile.getText();

        File file = new File(pathToSave + nameFile);

        boolean downloadPass =  file.exists() && !file.isDirectory();
        Assert.assertTrue(downloadPass);

        webDriver.quit();
    }

    @Test
    public void testBrokenImage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");
        Wait<WebDriver> wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img")));

        List<WebElement> images = driver.findElements(By.xpath("//img"));
        List<String> brokenImages = new ArrayList<>();
        for(WebElement image : images) {
            if (image.getAttribute("naturalWidth").equals("0")) {
                brokenImages.add(image.getAttribute("src"));
            }
        }

        Assert.assertTrue(brokenImages.size()==0, "List of broken images:" + brokenImages);
        driver.quit();
    }
}

