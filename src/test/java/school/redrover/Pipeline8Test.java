package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class Pipeline8Test extends BaseTest {

    private void createNewPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("(//*[@class = 'task-icon-link']) [1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("(//*[.='Pipeline'])[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDeletePipeline() {
        String pipeLineName = "New pipeline";
        createNewPipeline(pipeLineName);

        getDriver().findElement(By.xpath("(//*[@class= 'model-link'])[2]")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("(//a[ . = '" + pipeLineName + "'])[1]"))).perform();
        getDriver().findElement(By.xpath("//*[@id='projectstatus']//td//button")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-dropdown']//button[2]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[.='Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");
    }
}



