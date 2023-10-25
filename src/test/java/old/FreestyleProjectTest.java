package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
@Ignore
public class FreestyleProjectTest extends BaseTest {

    private final static String PROJECT_NAME = "FreestyleProject";

    private final static String NEW_PROJECT_NAME = "NewFreestyleProject";

    private final static String JENKINS_ICON = "//img[@id='jenkins-head-icon']";

    private final static String SUBMIT_BUTTON = "//button[@name='Submit']";

    private final static String RENAME_INPUT_FIELD = "//input[@name='newName']";

    private WebDriverWait getWait2() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }

    private WebDriverWait getWait5() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

    private void createFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(JENKINS_ICON)).click();
    }

    @Test
    public void testBuildFreestyleProject() {
        createFreestyleProject();

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='job/" + PROJECT_NAME + "/build?delay=0sec']"))).click();

        WebElement jenkinsIcon = getDriver().findElement(By.xpath(JENKINS_ICON));
        new Actions(getDriver())
                .moveToElement(jenkinsIcon)
                .pause(2000)
                .click()
                .perform();

        String statusIcon = getDriver().findElement(By.id(String.format("job_%s", PROJECT_NAME))).findElement(
                        By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']"))
                .getAttribute("tooltip");

        Assert.assertEquals(statusIcon, "Success");
    }

    @Test
    public void testRenameFreestyleProject() {
        createFreestyleProject();

        getDriver().findElement(By.xpath("//span[normalize-space()='" + PROJECT_NAME + "']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                (By.xpath("//a[@href = '/job/" + PROJECT_NAME + "/confirm-rename']")))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(RENAME_INPUT_FIELD))).clear();
        getDriver().findElement(By.xpath(RENAME_INPUT_FIELD)).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(JENKINS_ICON)).click();

        WebElement newProject = getWait2().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[normalize-space()='" + NEW_PROJECT_NAME + "']")));

        Assert.assertTrue(newProject.isDisplayed(), "Project was not renamed");
    }

}
