package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipline27Test extends BaseTest {
    private final String piplineName = "Pipline test name";

    @Test
    public void testCreatePiplineWithValidName() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(piplineName);

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//*[@id='job_Pipline test name']/td[3]/a")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//h1[@class= 'job-index-headline page-headline']")).getText(),
                "Pipeline " + piplineName);
    }
}
