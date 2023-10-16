package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.util.List;


public class GroupHighwayToAqaTest extends BaseTest {

    @Ignore
    @Test
    public void testInvalidCreds() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demo.prestashop.com/#/en/front");

        Thread.sleep(11000);

        driver.switchTo().frame("framelive");

        WebElement signInLink = driver.findElement(By.xpath("//span[text()='Sign in']"));

        signInLink.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@autocomplete='email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@aria-label='Password input']"));
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));

        emailInput.sendKeys("test@test.com");
        passwordInput.sendKeys("Qwerty123$");
        submitBtn.click();

        WebElement alertMessage = driver.findElement(By.className("alert-danger"));

        Assert.assertEquals(alertMessage.getText(), "Authentication failed.");

        driver.quit();
    }

    @Ignore
    @Test
    public void testCreateAcc() throws InterruptedException {
        String firstName = "firstName";
        String lastName = "LastName";
        String password = "Qwe123!@#ASD";
        String email = "test@mail.com";

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.prestashop.com/#/en/front");

        driver.switchTo().frame("framelive");

        WebElement signInLink = driver.findElement(By.xpath("//span[text()='Sign in']"));
        signInLink.click();

        WebElement createAccLink = driver.findElement(By.xpath("//a[contains(.,'No account? Create one here')]"));
        createAccLink.click();

        WebElement genderMr = driver.findElement(By.xpath("//label[contains(.,'Mrs.')]"));
        genderMr.click();

        WebElement firstnameInput = driver.findElement(By.xpath("//input[@id='field-firstname']"));
        WebElement lastnameInput = driver.findElement(By.xpath("//input[@id='field-lastname']"));
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='field-email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='field-password']"));
        WebElement ifReceiveOffers = driver.findElement(By.xpath("//input[@name='optin']"));
        WebElement ifAgreeTerms = driver.findElement(By.xpath("//input[@name='psgdpr']"));
        WebElement ifGetNewsLetters = driver.findElement(By.xpath("//input[@name='newsletter']"));
        WebElement ifCustomer_privacy = driver.findElement(By.xpath("//input[@name='customer_privacy']"));
        WebElement saveBtn = driver.findElement(By.cssSelector(".form-control-submit"));

        firstnameInput.sendKeys(firstName);
        lastnameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        ifReceiveOffers.click();
        ifAgreeTerms.click();
        ifGetNewsLetters.click();
        ifCustomer_privacy.click();
        saveBtn.click();

        WebElement ifRegistered = driver.findElement(By.cssSelector(".account > .hidden-sm-down"));

        Assert.assertEquals(ifRegistered.getText(), firstName + " " + lastName);

        driver.quit();
    }

    @Ignore
    @Test
    public void testLogin() {

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1[text()='Welcome to Jenkins!']")).getText(),
                "Welcome to Jenkins!"
        );
    }

    @Test
    public void testEmptyProjectName() {

        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateNewFreestyleProject() {

        JenkinsUtils.login(getDriver());

        final String projectName = "HighwayNew";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Project %s", projectName));
    }

    @Test
    public void testRenamePipelineProject() throws InterruptedException {
        JenkinsUtils.login(getDriver());

        final String projectName = "HighwayNewPipeline";
        final String newProjectName = "HighwayNewPipeline_NewName";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[.='Pipeline']")).click();

        WebElement okBtnIsEnabled = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        Assert.assertTrue(okBtnIsEnabled.isEnabled());
        okBtnIsEnabled.click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[contains(.,'Rename')]")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newProjectName);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebElement newTitle = getDriver().findElement(By.cssSelector(".job-index-headline"));
        assert newTitle.getText().contains("Pipeline " + newProjectName);

        getDriver().findElement(By.xpath("//span[text()='Delete Pipeline']")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    @Test
    public void testSetFolderDisplayNameAndDescription() {

        JenkinsUtils.login(getDriver());

        final String folderName = String.format("Some test folder name %3d", (int)(Math.random()*1000));
        final String folderDisplayName = "Some test folder display name";
        final String folderDescription = "Some test folder description";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(folderDisplayName);
        getDriver().findElement(By.name("_.description")).sendKeys(folderDescription);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                folderDisplayName);
        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(),
                folderDescription);
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText()
                .contains(String.format("Folder name: %s", folderName)));
    }

    @Test
    public void testSideBarOnMainPage() {

        JenkinsUtils.login(getDriver());

        List<WebElement> sideBarItems = getDriver().findElements(By.xpath("//div[@id = 'tasks']//div[@class = 'task ']"));

        String[] sideBarTitles = new String[]{"New Item", "People", "Build History", "Manage Jenkins", "My Views"};

        Assert.assertEquals(sideBarTitles.length, sideBarItems.size());

        for (int i = 0; i < sideBarTitles.length; i++) {
            Assert.assertEquals(sideBarItems.get(i).getText(), sideBarTitles[i]);
        }
    }
}
