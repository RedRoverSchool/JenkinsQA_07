package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class AfanasiukTest extends BaseTest {

    @Test
    public void testSearch() throws InterruptedException {

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");


        WebElement createJob = getDriver().findElement(By.xpath("//a [@href='newJob']"));
        createJob.click();

        WebElement nameInput = getDriver().findElement(By.xpath("//input [@name='name']"));
        nameInput.click();
        nameInput.sendKeys("New Job");

        WebElement itemType = getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        itemType.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                "Project New Job");

        }
}

