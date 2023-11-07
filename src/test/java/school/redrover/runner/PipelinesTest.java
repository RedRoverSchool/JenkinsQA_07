package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class PipelinesTest extends BaseTest{



    private void createNewPipeline(String pipelineName){
        getDriver().findElement(By.xpath("(//*[@class = 'task-icon-link']) [1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("(//*[.='Pipeline'])[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertTrue(getDriver().getCurrentUrl().equals("http://localhost:8080/job/New%20pipeline/"));

    }

    @Test
    public void testDeletePipeline() throws InterruptedException {
        String pipeLineName="New pipeline";
        createNewPipeline(pipeLineName);
        getDriver().findElement(By.xpath("(//*[@class= 'model-link'])[2]")).click();
        Actions actions=new Actions(getDriver());
        actions.moveToElement((getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")))).perform();
//        WebDriverWait wait=new WebDriverWait(getDriver(),Duration.ofSeconds(2));
//        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.className("jenkins-menu-dropdown-chevron"))));
//
//        getDriver().findElement(By.className("jenkins-menu-dropdown-chevron")).click();

        //*[@class='jenkins-table__link model-link inside model-link--open']
        JavascriptExecutor executor=(JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside model-link--open']")));
        getDriver().findElement(By.xpath("//*[@class='jenkins-dropdown']//button[2]")).click();

    }
}
