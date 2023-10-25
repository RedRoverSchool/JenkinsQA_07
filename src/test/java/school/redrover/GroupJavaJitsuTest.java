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
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.interactions.Actions;

public class GroupJavaJitsuTest  extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String RENAME_PROJECT = "NewProject";
    private static final String FOLDER_NAME = "NewFolder";
    private static final String DESCRIPTION_TEXT = "NewDescription";

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.cssSelector("a[href = '/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(projectName);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type = 'submit']")).click();

        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
    }

    private void createMultipleFreestyleProjects(String multipleProjects) {
        String [] arrProjectNames = new String [5];
        for(int i =0; i < arrProjectNames.length;i++){
            arrProjectNames[i] = multipleProjects + i;
        }
        for(String name : arrProjectNames) {
            getDriver().findElement(By.cssSelector("a[href = '/view/all/newJob']")).click();
            getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(name);

            getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
            getDriver().findElement(By.cssSelector("button[type = 'submit']")).click();

            getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
            getDriver().findElement(By.cssSelector("li[class = 'jenkins-breadcrumbs__list-item']")).click();
        }
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
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), "Project NewFreestyleProject");
    }

    @Test
    public void testFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);
        WebElement freestyleProjectName = getDriver().findElement(By.cssSelector("h1[class*= 'headline']"));
        Assert.assertEquals("Project " + PROJECT_NAME, freestyleProjectName.getText());
    }

    @Test
    public void testRenameFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("a[href*= 'rename']")).click();

        getDriver().findElement(By.cssSelector("input[name = 'newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name = 'newName']")).sendKeys(RENAME_PROJECT);
        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();

        WebElement renamedProjectName = getDriver().findElement(By.cssSelector("h1[class*= 'headline']"));
        Assert.assertEquals("Project " + RENAME_PROJECT, renamedProjectName.getText());
    }

    @Test
    public void testCreateFolder() {
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.cssSelector(".com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div[id='main-panel'] h1")).getText(), FOLDER_NAME);
    }
    @Test
    public void testCreateDescriptionFreestyleProject() throws InterruptedException {
        testNewFreestyleProject();
        getDriver().findElement(By.cssSelector("#description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--primary")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), DESCRIPTION_TEXT);

    }

    @Test
    public void testDeleteFreestyleProject() throws InterruptedException{
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), "Project NewFreestyleProject");

        getDriver().findElement(By.xpath("//li/a[@class='model-link']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'NewFreestyleProject')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete Project')]")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(text(),'Welcome to Jenkins!')]")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testDisableFreestyleProject() throws InterruptedException {
        testNewFreestyleProject();

        getDriver().findElement(By.xpath("//button[contains(text(),'Disable Project')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@name='Submit']")).getText(), "Enable");
    }

    @Test
    public void testEnableFreestyleProject() throws InterruptedException {
        testNewFreestyleProject();

        getDriver().findElement(By.xpath("//button[contains(text(),'Disable Project')]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'Enable')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[contains(text(),'Disable Project')]")).getText(), "Disable Project");
    }
    @Test
    public void testAddDescriptionFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[href*= 'configure']")).click();
        getDriver().findElement(By.cssSelector("textarea[name = 'description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.cssSelector("button[class = 'jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), DESCRIPTION_TEXT);
    }
    @Test
    public void testDeleteFreestyleProjectFromTheListOfProject() {
        createMultipleFreestyleProjects(PROJECT_NAME);
        boolean isFreestyleProjectPresent = false;

        Actions mouseOver = new Actions(getDriver());

        WebElement dropDownMenue = getDriver().findElement(By.cssSelector("button[data-href*='FreestyleProject1']"));
        mouseOver.moveToElement(dropDownMenue).click().perform();

        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[href*='FreestyleProject1/doDelete']"))).click();

        getDriver().switchTo().alert().accept();

        List<WebElement> listOfProjectName = getDriver().findElements(
                By.cssSelector("a[class = 'jenkins-table__link model-link inside']"));
        for (WebElement webElement : listOfProjectName) {
            if (!webElement.getText().equals("FreestyleProject1"))
                if (webElement.getText().equals("FreestyleProject1")){
                isFreestyleProjectPresent = true;
                break;
            }
        }
        Assert.assertTrue(isFreestyleProjectPresent);
    }
}
