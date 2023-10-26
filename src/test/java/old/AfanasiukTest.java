package old;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class AfanasiukTest extends BaseTest {

    @Test
    public void testCreateFreeStyleProject() {

        getDriver().findElement(By.xpath("//a [@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input [@name='name']")).sendKeys("Freestyle Project");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                "Project Freestyle Project");
    }

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a/span[1]")).click();
        getDriver().findElement(By.xpath("//input [@name='name']")).sendKeys("Pipeline");
                getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                "Pipeline Pipeline");
    }
}

