package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";

    private void createMultibranchPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    private void createMultibranchPipelineWithCreateAJob() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Ignore
    @Test
    public void testMultibranchPipelineCreationWithCreateAJob() {

        createMultibranchPipelineWithCreateAJob();

        String breadcrumbName = getDriver().findElement(
                By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).getText();

        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NAME,
                breadcrumbName + " name doesn't match " + MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testRenameMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + MULTIBRANCH_PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//h1")).getText().contains(MULTIBRANCH_PIPELINE_NEW_NAME));

        String breadcrumbName = getDriver().findElement(
                By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).getText();
        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NEW_NAME,
                breadcrumbName + MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Ignore
    @Test
    public void testErrorMessageRenameWithDotAtTheEnd() {

        final String ERROR_MESSAGE = "A name cannot end with ‘.’";

        createMultibranchPipelineWithCreateAJob();

        getDriver().findElement(By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys(".");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Ignore
    @Test
    public void testErrorMessageRenameWithLessThanSign() {

        final String ERROR_MESSAGE = "‘&lt;’ is an unsafe character";

        createMultibranchPipelineWithCreateAJob();

        getDriver().findElement(By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.SHIFT + ",");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Ignore
    @Test
    public void testErrorMessageRenameWithTwoUnsafeCharacters() {

        final String ERROR_MESSAGE = "‘#’ is an unsafe character";

        createMultibranchPipelineWithCreateAJob();

        getDriver().findElement(By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).click();
        getDriver().findElement(
                By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).sendKeys("#" + Keys.SHIFT + ".");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("p")).getText(), ERROR_MESSAGE,
                "There is no message " + ERROR_MESSAGE);
    }

    @Ignore
    @Test
    public void testAllTaskTextInSidebar() {
        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside']")).click();

        List<String> taskText = List.of(
                "Status",
                "Configure",
                "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events",
                "Delete Multibranch Pipeline",
                "People",
                "Build History",
                "Rename",
                "Pipeline Syntax",
                "Credentials");

        int a = 1;
        for (String expectedText : taskText) {
            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + a++ + "]")).getText(),
                    expectedText);
        }
    }

    @Test
    public void testDeleteMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);
        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NEW_NAME);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + MULTIBRANCH_PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/delete']")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertNotEquals(
                getDriver().findElement(By.xpath("//td//a[@href]/span")).getText(),
                MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultiConfigurationPipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        WebElement nameField = getDriver().findElement(By.xpath("//input[@name='name']"));
        nameField.clear();
        nameField.sendKeys("MyMultiConfigurationPipeline");

        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline'] ")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='job/MyMultiConfigurationPipeline/']")).isDisplayed());
    }

    @Ignore
    @Test
    public void testRenameMultibranchDropdownDashboard() {
        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        WebElement elementToHover = getDriver().findElement(By.xpath("//a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHover).perform();
        elementToHover.click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[3]/a/span")).getText(), MULTIBRANCH_PIPELINE_NEW_NAME,
                MULTIBRANCH_PIPELINE_NAME + "is not equal" + MULTIBRANCH_PIPELINE_NEW_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationPipeline")
    public void testEnableByDefault() {
        getDriver().findElement(By.xpath("//a[@href='job/MyMultiConfigurationPipeline/']")).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//*[@id='toggle-switch-enable-disable-project']/label")).getText(), "Enabled");
    }

    @Test
    public void testSeeAAlertAfterDisableMultibranchPipeline() {
        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("a[href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.cssSelector("button[formNoValidate]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("form[method='post']")).getText().
                        contains("This Multibranch Pipeline is currently disabled"),
                "Incorrect or missing text");
    }
}
