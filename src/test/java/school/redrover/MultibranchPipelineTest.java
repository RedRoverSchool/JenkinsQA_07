package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {
    final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipelineName";
    final String MULTIBRANCH_PIPELINE_NEW_NAME = "MultibranchPipelineNewName";

    @Test
    public void testMultibranchPipelineCreationWithCreateAJob() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();

        String breadcrumbName = getDriver().findElement(
                By.xpath("//a[@class='model-link'][contains(@href, 'job')]")).getText();

        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NAME,
                breadcrumbName + " name doesn't match " + MULTIBRANCH_PIPELINE_NAME);
    }
    @Test
    public void testRenameMultibranchPipelineFromSidebarOnTheMultibranchPipelinePage() {

        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

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
}
