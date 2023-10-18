package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class GroupJavaJitsuTest  extends BaseTest {
    @Ignore
    @Test
    public void testGetTile() {
        getDriver().get("https://www.saucedemo.com");

        String title = getDriver().getTitle();
        Assert.assertEquals("Swag Labs", title);
    }

    @Test
    public void testFirst() throws InterruptedException {

       WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
       newItem.click();
       WebElement itemName = getDriver().findElement(By.id("name"));
       itemName.sendKeys("NewProject3");
       WebElement pipeLine = getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']"));
       pipeLine.click();
       WebElement button = getDriver().findElement(By.id("ok-button"));
       button.click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[normalize-space()='Configure']")).getText(),
                "Configure");
    }

    @Test
    public void testNewFreestyleProject() throws InterruptedException {

        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();
        WebElement itemName = getDriver().findElement(By.id("name"));
        itemName.sendKeys("NewFreestyleProject");
        WebElement freestyleProject = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleProject.click();
        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));
        buttonOk.click();
        WebElement buttonSave = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        buttonSave.click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), "Project NewFreestyleProject");

    }

    @Test
    public void testFreestyleProject() {
        String projectName = "FreestyleProject";

        WebElement newItem = getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']"));
        newItem.click();

        WebElement itemName = getDriver().findElement(By.cssSelector("input.jenkins-input"));
        itemName.sendKeys(projectName);
        WebElement freestyleProject = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleProject.click();

        WebElement okButton = getDriver().findElement(By.cssSelector("button[type='submit']"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.cssSelector("button[name='Submit']"));
        saveButton.click();

        WebElement freestyleProjectName = getDriver().findElement(By.cssSelector("h1[class*='headline']"));
        Assert.assertEquals("Project " + projectName, freestyleProjectName.getText());
    }
    @Ignore
    @Test
    public void testEndToEnd() {
        getDriver().get("https://www.saucedemo.com/");
        WebElement userName = getDriver().findElement(By.cssSelector("input[placeholder='Username']"));
        userName.sendKeys("standard_user");

        WebElement password = getDriver().findElement(By.cssSelector("input[placeholder='Password']"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = getDriver().findElement(By.cssSelector("input[class*='submit']"));
        loginButton.click();

        WebElement addBackPack = getDriver().findElement(By.cssSelector("button[data-test*='backpack']"));
        addBackPack.click();

        WebElement addBikeLight = getDriver().findElement(By.cssSelector("button[data-test*='bike']"));
        addBikeLight.click();

        WebElement shoppingCart = getDriver().findElement(By.cssSelector("a[class*='cart']"));
        shoppingCart.click();

        WebElement checkOut = getDriver().findElement(By.cssSelector("button[data-test='checkout']"));
        checkOut.click();

        WebElement firstName = getDriver().findElement(By.cssSelector("input[data-test='firstName']"));
        firstName.sendKeys("Alex");

        WebElement lastName = getDriver().findElement(By.cssSelector("input[data-test='lastName']"));
        lastName.sendKeys("Smith");

        WebElement zipCode = getDriver().findElement(By.cssSelector("input[data-test='postalCode']"));
        zipCode.sendKeys("10101");

        WebElement continueButton = getDriver().findElement(By.cssSelector("input[data-test='continue']"));
        continueButton.click();

        WebElement finish = getDriver().findElement(By.cssSelector("button[data-test='finish']"));
        finish.click();

        WebElement orderConfirmation = getDriver().findElement(By.cssSelector("h2[class='complete-header']"));
        Assert.assertEquals("Thank you for your order!", orderConfirmation.getText());

    }
}
