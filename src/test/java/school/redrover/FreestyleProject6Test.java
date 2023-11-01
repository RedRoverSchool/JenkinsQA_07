package school.redrover;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject6Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject5";

    private void createProject(String PROJECT_NAME) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
    }

    @Test
    public void testCreate() {
        String projectName = PROJECT_NAME;

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[normalize-space()='Project FreestyleProject5']")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testAddDescription() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("Here are the project description!");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[normalize-space()='Here are the project description!']")).getText(),
                "Here are the project description!");
    }


    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[starts-with(@href,'/view/all/newJob') and contains (@class,'task-link')]")).click();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testDeleteFreestyleProject() {
        final String projectName = "Starlight";
        createFreestyleProject(projectName);
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.xpath("//a[@class='task-link  confirmation-link']")).click();
        getDriver().switchTo().alert().accept();
        boolean isElementPresent = isElementPresent(getDriver(), By.xpath("//a[starts-with(@href,'job/" + projectName + "/') and contains(@class,'jenkins-table__link')]"));

        Assert.assertFalse(isElementPresent);

    }
    @Test
    public void testDiscardOldBuildsCheckbox() {
        final String projectName = "Starlight";
        final String configureLink = "//a[@href='/job/" + projectName + "/configure']";
        final String daysToKeepBuildsField = "//input[@name = '_.daysToKeepStr']";
        final String daysToKeepBuildsFieldValue = "5";
        final String maxOfBuildsToKeepField = "//input[@name = '_.numToKeepStr']";
        final String maxOfBuildsToKeepFieldValue = "7";

        createFreestyleProject(projectName);
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.xpath(configureLink)).click();
        getDriver().findElement(By.xpath("//label[normalize-space()='Discard old builds']")).click();
        getDriver().findElement(By.xpath(daysToKeepBuildsField)).sendKeys(daysToKeepBuildsFieldValue);
        getDriver().findElement(By.xpath(maxOfBuildsToKeepField)).sendKeys(maxOfBuildsToKeepFieldValue);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath(configureLink)).click();
        boolean isCheckBoxElementPresent = isElementPresent(getDriver(), By.xpath("//div[@class='rowvg-start tr']"));

        Assert.assertTrue(isCheckBoxElementPresent,"Check-box is not selected");
        Assert.assertEquals(getDriver().findElement(By.xpath(daysToKeepBuildsField)).getAttribute("value"),daysToKeepBuildsFieldValue);
        Assert.assertEquals(getDriver().findElement(By.xpath(maxOfBuildsToKeepField)).getAttribute("value"),maxOfBuildsToKeepFieldValue);
    }
}