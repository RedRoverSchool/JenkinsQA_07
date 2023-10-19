package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    public void testDemoQaOpenPage() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/");
        WebElement image = driver.findElement(By.xpath("//*[@id='app']/header/a/img"));
        image.click();
        Assert.assertEquals(image, image);
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
        fullNameField.sendKeys("adam@gmail.com");
        ;
        WebElement submit = getDriver().findElement(By.id("submit"));
        submit.click();
        Assert.assertEquals(submit, submit);
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
    public void ActionsWithCheckBoxTest() {

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

    @Test
    public void removeItemTest() {
        WebDriver driver = getDriver();
        String randomUsername = "Test" + UUID.randomUUID().toString().substring(0, 8);
        //create item
        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(randomUsername);
        driver.findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();

        // get list all items in main page
        List <WebElement> listItems = getListElements("//*[@class=\"jenkins-table__link model-link inside\"]");

        //search for an added item and delete this
        Assert.assertTrue(isActualElement(listItems, randomUsername));
        if (isActualElement(listItems, randomUsername)) {
            driver.findElement(By.xpath("//*[@id=\"job_" + randomUsername + "\"]/td[3]/a")).click();
            driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")).click();
            // accept alert to delete
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        listItems = getListElements("//*[@class=\"jenkins-table__link model-link inside\"]");
        Assert.assertFalse(isActualElement(listItems, randomUsername));
    }
    // search item in list items
    private boolean isActualElement(List<WebElement> items, String expecting) {
        return items.stream().anyMatch(item -> item.getText().compareTo(expecting) == 0);
    }
    //get Web elements
    private List<WebElement> getListElements(String xpath) {
        return getDriver().findElements(By.xpath(xpath));
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
        Assert.assertEquals(str, randomUsername);
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

    @Test
    public void testRenameFreeStyleProject() {
        final String JOBNAME = "FreeStyleProjectJob";
        final String NEWJOBNAME = "FreeStyleProjectJobNew";

        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a"));
        newItemButton.click();

        WebElement inputJobNameField = getDriver().findElement(By.xpath("//*[@id='name']"));
        inputJobNameField.click();
        inputJobNameField.sendKeys(JOBNAME);

        WebElement freeStyleProject = getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']"));
        freeStyleProject.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement jenkinsHomeLink = getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']"));
        jenkinsHomeLink.click();

        WebElement createdJob = getDriver().findElement(By.xpath("//span[normalize-space()='FreeStyleProjectJob']"));
        createdJob.click();

        WebElement renameTask = getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]"));
        renameTask.click();

        WebElement newNameInputField = getDriver().findElement(By.xpath("//input[@checkdependson=\"newName\"]"));
        newNameInputField.clear();
        newNameInputField.sendKeys(NEWJOBNAME);

        WebElement confirmRenameButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        confirmRenameButton.click();

        String jobPageTitle = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText();

        Assert.assertTrue(jobPageTitle.contains(NEWJOBNAME));
    }

    @Test
    public void testVerifyEmptyItemNameWarningMessage() {

        final String WARNING_MESSAGE_TEXT_EXPECTED = "» This field cannot be empty, please enter a valid name";
        final String CSS_COLOR_WARNING_MESSAGE_EXPECTED = "rgba(255, 0, 0, 1)";

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        String warningMessageTextActual = getDriver().findElement(By.xpath("//div[@id=\"itemname-required\"]")).getText();
        String cssColorWarningMessageActual = getDriver().findElement(By.xpath("//div[@id=\"itemname-required\"]")).getCssValue("color");

        Assert.assertEquals(cssColorWarningMessageActual, CSS_COLOR_WARNING_MESSAGE_EXPECTED);
        Assert.assertEquals(warningMessageTextActual, WARNING_MESSAGE_TEXT_EXPECTED);
    }


    @Test
    public void testCreateNewItem() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();

        getDriver().findElement(By.xpath("//div/input[@class = 'jenkins-input']")).sendKeys("New Item Name1");
        getDriver().findElement(By.xpath("//*[@id = 'j-add-item-type-standalone-projects']/ul/li[1]/div[1]")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.xpath("//textarea[@class = 'jenkins-input   ']")).sendKeys("Description for New created item");
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        WebElement newItemNameIsExist = getDriver().findElement(By.xpath("//*/div/h1[contains(text(),'Project New Item Name1')]"));

       Assert.assertTrue(newItemNameIsExist.isDisplayed());

    }
}