package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

    @Test
    public void testMultibranchPipelineCreationWithCreateAJob() {

        final String MULTIBRANCH_PIPELINE_NAME = "RedRover_7 MP";

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
}
