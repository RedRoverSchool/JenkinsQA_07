package school.redrover.runner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.beans.Visibility;
import java.time.Duration;

public class Pipeline8Test extends BaseTest {

    private void createNewPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("(//*[@class = 'task-icon-link']) [1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("(//*[.='Pipeline'])[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDeletePipeline() throws InterruptedException {
        String pipeLineName = "New pipeline";
        createNewPipeline(pipeLineName);

        getDriver().findElement(By.xpath("(//*[@class= 'model-link'])[2]")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("(//a[.='" + pipeLineName + "'])[1]"))).perform();
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-dropdown']//button[2]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[.='Welcome to Jenkins!']")).getText().equals("Welcome to Jenkins!"));
    }
}

