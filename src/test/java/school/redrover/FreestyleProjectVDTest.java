package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectVDTest extends BaseTest {

    private static final String PROJECT_NAME = "NewFreestyleProject";

    private static final String SUBMIT_BUTTON = "//button[@name='Submit']";

    private static final String CONFIGURE_LINK = "//a[@href='/job/" + PROJECT_NAME + "/configure']";

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
}
