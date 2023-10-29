package school.redrover;
import org.openqa.selenium.By;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Pipeline7Test extends BaseTest {
    @Test
    public void testCreatePipeline() {
        final String pipelineName = "FirstPipeline";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']//li[2]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();
        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + pipelineName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1")).getText(),
                "Pipeline " + pipelineName);

    }

}

