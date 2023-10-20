package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;

public class GroupJavaJitsuTest  extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String RENAME_PROJECT = "NewProject";
    private static final String FOLDER_NAME = "NewFolder";

    public void createFreestyleProject(String projectName) {

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(projectName);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
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

        createFreestyleProject(PROJECT_NAME);
        WebElement freestyleProjectName = getDriver().findElement(By.cssSelector("h1[class*='headline']"));
        Assert.assertEquals("Project " + PROJECT_NAME, freestyleProjectName.getText());
    }

    @Test
    public void testRenameFreestyleProject() {

        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("a[href*='rename']")).click();

        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(RENAME_PROJECT);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        WebElement renamedProjectName = getDriver().findElement(By.cssSelector("h1[class*='headline']"));
        Assert.assertEquals("Project " + RENAME_PROJECT, renamedProjectName.getText());
    }

    @Test
    public void testCreateFolder() {

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.cssSelector("li[class='com_cloudbees_hudson_plugins_folder_Folder'] span[class='label']")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div[id='main-panel'] h1")).getText(), FOLDER_NAME);
    }
}
