package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProjectVDTest extends BaseTest {

    private static final String PROJECT_NAME = "NewFreestyleProject";

    private static final String SUBMIT_BUTTON = "//button[@name='Submit']";

    private static final String CONFIGURE_LINK = "//a[@href='/job/" + PROJECT_NAME + "/configure']";

    private final static String JENKINS_ICON = "//img[@id='jenkins-head-icon']";

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testAddLinkToGitHubInGitHubProjectSection() {
        final String sourseCodeManagement = "//button[@data-section-id='source-code-management']";
        final String inputUrlField = "//input[@name='_.url']";

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath(sourseCodeManagement)).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        getDriver().findElement(By.xpath("//label[@for='radio-block-1']")).click();

        getDriver().findElement(By.xpath(inputUrlField)).sendKeys("https://github.com/RedRoverSchool/JenkinsQA_07");
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        getDriver().findElement(By.xpath(CONFIGURE_LINK)).click();
        getDriver().findElement(By.xpath(sourseCodeManagement)).click();
        js.executeScript("window.scrollBy(0,600)");

        Assert.assertEquals(getDriver().findElement(By.xpath(inputUrlField)).getAttribute("value"), "https://github.com/RedRoverSchool/JenkinsQA_07");
    }

    @Test
    public void testCheckDiscardOldBuildsCheckbox() {
        final String inputDaysToKeepBuildsField = "//input[@name='_.daysToKeepStr']";
        final String inputMaxNumberOfBuildsToKeepField = "//input[@name='_.numToKeepStr']";

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//label[normalize-space()='Discard old builds']")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,300)");

        getDriver().findElement(By.xpath(inputDaysToKeepBuildsField)).sendKeys("2");
        getDriver().findElement(By.xpath(inputMaxNumberOfBuildsToKeepField)).sendKeys("3");
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(CONFIGURE_LINK)).click();
        js.executeScript("window.scrollBy(0,300)");

        Assert.assertEquals(getDriver().findElement(By.xpath(inputDaysToKeepBuildsField)).getAttribute("value"), "2");
        Assert.assertEquals(getDriver().findElement(By.xpath(inputMaxNumberOfBuildsToKeepField)).getAttribute("value"), "3");
    }

    @Test
    public void testCheckThrottleBuildsCheckbox() {

        final String numberOfBuilds = "//input[@name='_.count']";
        final String timePeriod = "//select[@name='_.durationName']";

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//label[normalize-space()='Throttle builds']")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");

        getDriver().findElement(By.xpath(numberOfBuilds)).clear();
        getDriver().findElement(By.xpath(numberOfBuilds)).sendKeys("4");
        getDriver().findElement(By.xpath(timePeriod)).click();
        getDriver().findElement(By.xpath("//option[@value='day']")).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(CONFIGURE_LINK)).click();
        js.executeScript("window.scrollBy(0,600)");

        Assert.assertEquals(getDriver().findElement(By.xpath(numberOfBuilds)).getAttribute("value"), "4");
        Assert.assertEquals(getDriver().findElement(By.xpath(timePeriod)).getAttribute("value"), "day");
    }

    @Test
    public void testSelectExecuteConcurrentBuilds() {

        final String checkBoxXpath = "//div[@class='form-container tr']";

        createFreeStyleProject(PROJECT_NAME);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,300)");
        int quantityOfElementsBeforeClicking = getDriver().findElements(By.xpath(checkBoxXpath)).size();

        getDriver().findElement(By.xpath("//label[normalize-space()='Execute concurrent builds if necessary']")).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(CONFIGURE_LINK)).click();
        js.executeScript("window.scrollBy(0,300)");

        int quantityOfElementsAfterClicking = getDriver().findElements(By.xpath(checkBoxXpath)).size();

        Assert.assertEquals(quantityOfElementsAfterClicking, quantityOfElementsBeforeClicking + 1);
    }

    @Test
    public void testIsWorkspaceCreated() {

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath(JENKINS_ICON)).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PROJECT_NAME + "/ws/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Error: no workspace");

        getDriver().findElement(By.xpath(JENKINS_ICON)).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PROJECT_NAME + "/build?delay=0sec']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PROJECT_NAME + "/ws/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Workspace of " + PROJECT_NAME + " on Built-In Node");
    }
}
