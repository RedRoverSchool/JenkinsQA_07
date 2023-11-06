package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject27Test extends BaseTest {

    @Test

    public  void testCreateNN (){

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input[class = 'jenkins-input']")).sendKeys("FirstNNProjectName");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id ='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//img[@id ='jenkins-name-icon']")).click();
        getDriver().findElement(By.xpath("//td/a [@href= 'job/FirstNNProjectName/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel>h1")).getText(),"Project FirstNNProjectName");
    }
}
