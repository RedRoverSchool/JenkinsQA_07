package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline2Test extends BaseTest {

    @Test
    public void testMultibranchNameDisplayBreadcrumbTrail() {

        final String pipelineName = "Multi";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[contains(text(),'Multibranch Pipeline')]")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String pipelineNameExpected =  getDriver().findElement(By.xpath("//a[contains(text(),'" + pipelineName + "')]")).getText();
        Assert.assertEquals(pipelineNameExpected, pipelineName);

    }


}
