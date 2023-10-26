package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline2Test extends BaseTest {
    @Test
    public void testMultibranchNameDisplay() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("Multi");
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        String pipelineName =  getDriver().findElement(By.xpath("//a[@href='/job/Multi/' and @class='model-link']")).getText();
        Assert.assertEquals(pipelineName, "Multi");
        String pipelineStatusName = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(pipelineStatusName, "Multi");
        getDriver().findElement(By.xpath("//img[@alt='Jenkins']")).click();
        String pipelineDashboardName = getDriver().findElement(By.xpath("//span[contains(text(), 'Multi')]")).getText();
        Assert.assertEquals(pipelineDashboardName, "Multi");

    }


}
