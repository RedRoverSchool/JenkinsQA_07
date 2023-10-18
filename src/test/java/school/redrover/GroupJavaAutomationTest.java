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
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import static org.testng.Assert.assertEquals;


@Ignore
public class GroupJavaAutomationTest extends BaseTest {
    private static final String HEROKUAPP = "https://the-internet.herokuapp.com/";
    @Test
    public void testHerokuAppHomePage() {
        getDriver().get(HEROKUAPP);
        String title = getDriver().getTitle();
        assertEquals(title, "The Internet");
    }
    @Ignore
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
    @Ignore
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
    public void testHerokuAppAB() {
        getDriver().get(HEROKUAPP);
        WebElement buttonABTesting = getDriver().findElement(By.xpath("//a[@href='/abtest']"));
        buttonABTesting.click();
        WebElement textABTest = getDriver().findElement(By.xpath("//h3[contains(text(),'A/B Test')]"));
        assertEquals(textABTest.getText().substring(0,8), "A/B Test");
    }

    @Test
    public void testHerokuAppCheckBoxSizeTwo() {
        getDriver().get(HEROKUAPP);
        SoftAssert softAssert = new SoftAssert();
        WebElement buttonCheckBox = getDriver().findElement(By.xpath("//a[@href='/checkboxes']"));
        buttonCheckBox.click();
        WebElement textCheckBox = getDriver().findElement(By.xpath("//h3[text()='Checkboxes']"));
        String titleCheckBox = textCheckBox.getText();
        softAssert.assertEquals(titleCheckBox, "Checkboxes");
        List<WebElement> listCheckbox = getDriver().findElements(By.xpath("//input[@type='checkbox']"));
        softAssert.assertEquals(listCheckbox.size(), 2);
        softAssert.assertAll();

    }

    @Ignore
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
    @Ignore
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
    public void testLoginSuccessful() {
        getDriver().get(HEROKUAPP);
        WebElement elementLogin = getDriver().findElement(By.xpath("//a[@href='/login']"));
        elementLogin.click();
        WebElement inputName = getDriver().findElement(By.xpath("//input[@name='username']"));
        WebElement inputPassword = getDriver().findElement(By.xpath("//input[@name='password']"));
        inputName.sendKeys("tomsmith");
        inputPassword.sendKeys("SuperSecretPassword!");
        WebElement buttonSubmit = getDriver().findElement(By.xpath("//button[@type='submit']"));
        buttonSubmit.click();
        WebElement messageTitle = getDriver().findElement(By.xpath("//div[@class='flash success']"));
        Assert.assertEquals(messageTitle
                        .getText()
                        .replaceAll("[×\n]", ""),
                "You logged into a secure area!");
        WebElement logout = getDriver().findElement(By.xpath("//*[@href='/logout']"));
        logout.click();

    }

    @Test
    public void testLoginEmptyName() {
        getDriver().get(HEROKUAPP);
        WebElement elementLogin = getDriver().findElement(By.xpath("//a[@href='/login']"));
        elementLogin.click();
        WebElement inputPassword = getDriver().findElement(By.xpath("//input[@name='password']"));
        inputPassword.sendKeys("SuperSecretPassword!");
        WebElement buttonSubmit = getDriver().findElement(By.xpath("//button[@type='submit']"));
        buttonSubmit.click();
        WebElement messageTitle = getDriver().findElement(By.xpath("//div[@class='flash error']"));
        Assert.assertEquals(messageTitle
                        .getText()
                        .replaceAll("[×\n]", ""),
                "Your username is invalid!");
    }

    @Test
    public void testBasicAuthWithoutAlert() {
       getDriver().get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        String authMessage = getDriver().findElement(By.xpath("//h3/following-sibling::p")).getText();
        Assert.assertTrue(authMessage.contains("Congratulations"));
    }


    @Test
    public void checkBoxTest(){

        getDriver().get(HEROKUAPP);

        WebElement elementCheckBoxes = getDriver().findElement(By.xpath("//a[@href='/checkboxes']"));
        elementCheckBoxes.click();

        List<WebElement> elementFormCheckBoxes;
        WebElement checkBox1 = getDriver().findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkBox2 = getDriver().findElement(By.xpath("//form[@id='checkboxes']/input[2]"));
        elementFormCheckBoxes = List.of(checkBox1, checkBox2);
        for (WebElement item: elementFormCheckBoxes){
            if (!item.isSelected()){
                item.click();
            }
        }
        Assert.assertEquals(List.of(checkBox1.isSelected(),checkBox2.isSelected()), List.of(true,true));

    }

    @Ignore
    @Test
    public void downloadFile(){

        getDriver().get(HEROKUAPP);

        String pathToSave = "C:\\Users\\48573\\Downloads\\";

        WebElement elementFileDownload = getDriver().findElement(By.xpath("//a[@href='/download']"));
        elementFileDownload.click();

        WebElement firstFile = getDriver().findElement(By.xpath("//div[@id='content']/div/a[1]"));
        firstFile.click();

        String nameFile = firstFile.getText();

        File file = new File(pathToSave + nameFile);

        boolean downloadPass =  file.exists() && !file.isDirectory();
        Assert.assertTrue(downloadPass);

    }
    @Ignore
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

    @Test
    public void testJenkinsHomePageAndJenkinsVersion()  {

        String title = getDriver().getTitle();
        Assert.assertEquals(title,"Dashboard [Jenkins]");

        WebElement versionJenkinsButton = getDriver().findElement
        (By.xpath("//button[@type='button']"));
        String versionJenkins = versionJenkinsButton.getText();
        Assert.assertEquals(versionJenkins,"Jenkins 2.414.2");
    }
}

