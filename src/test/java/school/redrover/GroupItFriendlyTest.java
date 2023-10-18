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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;


public class GroupItFriendlyTest extends BaseTest {

    @Ignore
    @Test
    public void testDemoQaOpenPage()  {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/");
        WebElement image = driver.findElement(By.xpath("//*[@id='app']/header/a/img"));
        image.click();
        Assert.assertEquals(image,image);
    }
    @Ignore
    @Test
    public void testDemoQaChangePage() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/");
        WebElement element = driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        element.click();
        WebElement text = driver.findElement(By.xpath("//*[.='Please select an item from left to start practice.']"));
        String value = text.getText();
        Assert.assertEquals(value, "Please select an item from left to start practice.");
    }
    @Ignore
    @Test
    public void testDemoQaTextBox() {
        getDriver().get("https://demoqa.com/elements");
        WebElement element = getDriver().findElement(By.xpath("//span[contains(text(),'Text Box')]"));
        element.click();
        WebElement fullNameField = getDriver().findElement(By.id("userName"));
        fullNameField.click();
        fullNameField.sendKeys("Adam Adams");
        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.click();
        fullNameField.sendKeys("adam@gmail.com");;
        WebElement submit = getDriver().findElement(By.id("submit"));
        submit.click();
        Assert.assertEquals(submit,submit);
    }
    @Ignore
    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://so-yummi-qa.netlify.app/register");
        String randomUsername = "Test" + UUID.randomUUID().toString().substring(0, 8);
        String randomEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.click();
        usernameInput.sendKeys(randomUsername);
        WebElement emailInput = driver.findElement(By.id("emailInput"));
        emailInput.click();
        emailInput.sendKeys(randomEmail);
        WebElement passwordInput = driver.findElement(By.id("passwordInput"));
        passwordInput.click();
        passwordInput.sendKeys("Test@123456");

        WebElement searchButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton2.click();

        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://so-yummi-qa.netlify.app/home";
        Assert.assertEquals(currentUrl, expectedUrl, "The current URL does not match the expected URL.");
    }

    @Ignore
    @Test
    public void ActionsWithCheckBoxTest(){

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/elements");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            WebElement checkbox = driver.findElement(By.xpath("//span[contains(text(), 'Check Box')]"));
            checkbox.click();

            WebElement checkboxIsSelected = driver.findElement(By.xpath("//span[@class=\"rct-checkbox\"]"));
            checkboxIsSelected.click();

            assertEquals(driver.findElement(By.xpath("//*[@id=\"result\"]")).getText(), "You have selected :\n" +
                    "home\n" +
                    "desktop\n" +
                    "notes\n" +
                    "commands\n" +
                    "documents\n" +
                    "workspace\n" +
                    "react\n" +
                    "angular\n" +
                    "veu\n" +
                    "office\n" +
                    "public\n" +
                    "private\n" +
                    "classified\n" +
                    "general\n" +
                    "downloads\n" +
                    "wordFile\n" +
                    "excelFile");
          } finally {
            driver.quit();
        }
    }

    @Ignore
    @Test
    public void BadRequestButtonTest() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/links");
        assertEquals(driver.findElement(By.id("bad-request")).getText(), "Bad Request");
    }
    @Ignore
    @Test
    public void SearchRecipe() {
        WebDriver driver = getDriver();
        driver.get("https://allusrecipe.com/turkey-delight/");

        WebElement boxSearch = driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/form/div/input"));
        WebElement clickSearch = driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/form/div/div/button"));

        boxSearch.sendKeys("lokum");
        clickSearch.click();

        WebElement title = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div/h1"));

        String actual =  title.getText();

        assertEquals(actual, "Lokum Recipes");
    }
    @Test
    public void CreateNewItem(){
        String randomUsername = "Test" + UUID.randomUUID().toString().substring(0, 8);

        WebElement newItem = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItem.click();
        WebElement inputField = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        inputField.click();
        inputField.sendKeys(randomUsername);
        WebElement freeStileProject = getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"));
        freeStileProject.click();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();
        WebElement dashBoard = getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a"));
        dashBoard.click();
        List <WebElement> list = getDriver().findElements(By.xpath("//*[@class=\"jenkins-table__link model-link inside\"]"));
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getText());
            if (list.get(i).getText().contains(randomUsername)){
                str=list.get(i).getText();
             break;
            }
        }
        Assert.assertEquals(str,randomUsername);
    }

    @Test
    public void testJenkinsLink() {
        String paragraphAboutJenkinsText = "The leading open source automation server which enables developers around the world to reliably build, test, and deploy their software.";

        WebDriver driver = getDriver();

        WebElement jenkinsLink = driver.findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));
        jenkinsLink.click();

        WebElement aboutJenkins = driver.findElement(By.xpath("//a[@class='jenkins-dropdown__item' and contains(text(), 'About Jenkins')]"));
        aboutJenkins.click();

        WebElement descriptionParagraph = driver.findElement(By.xpath("//p[@class='app-about-paragraph']"));
        Assert.assertEquals(descriptionParagraph.getText(), paragraphAboutJenkinsText);
    }

}
