package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject7Test extends BaseTest {

    @Test
    public void testCreate() {
        final String projectName = "FreeStyleProject";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id= 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name= 'Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id  = 'jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class = 'job-index-headline page-headline']")).getText(),
                "Project " + projectName);
    }
}
