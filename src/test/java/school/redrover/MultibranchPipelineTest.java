package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";

    private void createMultibranchPipeline(String MULTIBRANCH_PIPELINE_NAME) {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    private void createMultibranchPipelineWithCreateAJob() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
    }

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

        getDriver().findElement(By.xpath("//span[contains(text(),'"+ MULTIBRANCH_PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//div[@class ='setting-main']/input")).sendKeys(MULTIBRANCH_PIPELINE_NEW_NAME);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//h1")).getText().contains(MULTIBRANCH_PIPELINE_NEW_NAME));

        String breadcrumbName = getDriver().findElement(
                By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).getText();
        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NEW_NAME,
                breadcrumbName +  MULTIBRANCH_PIPELINE_NEW_NAME);
    }

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
                "Pipeline Syntax" ,
                "Credentials");

        int a = 1;
        for (String expectedText: taskText) {
            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + a++ + "]")).getText(),
                    expectedText);
        }
    }
}
