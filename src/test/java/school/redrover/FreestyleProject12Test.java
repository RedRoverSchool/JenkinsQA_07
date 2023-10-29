package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject12Test extends BaseTest {


    @Test
    public void testCreateFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
        getDriver().findElement(By.xpath("//table//a[@href='job/FreestyleProject/']")).click();
        WebElement disableButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        boolean disableButtonIsDisplayed = disableButton.isDisplayed();
        Assert.assertTrue(disableButtonIsDisplayed,"We are now on Configuration screen");
    }
}
