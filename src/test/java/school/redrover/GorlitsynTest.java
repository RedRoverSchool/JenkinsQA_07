package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;

public class GorlitsynTest extends BaseTest {

    private WebDriverWait getWait2() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }

    private WebDriverWait getWait5() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

    public  void createFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href= '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("NewTestProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.name("Submit"));
        getDriver().findElement(By.xpath("//img[@id='jenkins-head-icon']")).click();
    }

    @Test
    public void testFreestyleProjectCreation() {
        createFreestyleProject();
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id=\"job_NewTestProject\"]")).getText(), "NewTestProject\n" +
                "N/A N/A N/A");
    }
    @Test
    public void testFreestyleProjectDelete() {
        createFreestyleProject();
        getDriver().findElement(By.xpath("//a[@href='job/NewTestProject/']")).click();
        getDriver().findElement(By.xpath("//a[@data-message=\"Delete the Project ‘NewTestProject’?\"]")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElements(By.xpath("e"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[text()='Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");


    }




}

