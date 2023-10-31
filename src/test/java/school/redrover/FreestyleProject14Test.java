package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject14Test extends BaseTest {

    @Test

    public void testCreate () {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class= 'jenkins-input']")).sendKeys("FreestyleProject1");
        getDriver().findElement(By.xpath("//li[@class= 'hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id= 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name= 'Submit']")).click();

        getDriver().findElement(By.xpath("//li/a[@class= 'model-link']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject1/']")).click();

        Assert.assertEquals
                (getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                        "Project FreestyleProject1");



    }
}
