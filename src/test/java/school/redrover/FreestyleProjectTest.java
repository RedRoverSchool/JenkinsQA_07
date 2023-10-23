package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProjectTest extends BaseTest {

    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String JENKINS_ICON = "//img[@id='jenkins-head-icon']";

    public WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }

    @Test
    public void testBuildFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath(JENKINS_ICON)).click();

        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='job/" + PROJECT_NAME + "/build?delay=0sec']"))).click();

        WebElement jenkinsIcon = getDriver().findElement(By.xpath(JENKINS_ICON));
        new Actions(getDriver())
                .moveToElement(jenkinsIcon)
                .pause(2000)
                .click()
                .perform();


        String statusIcon = getDriver().findElement(By.id(String.format("job_%s", PROJECT_NAME))).findElement(
                        By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']"))
                .getAttribute("tooltip");

        Assert.assertEquals(statusIcon, "Success");
    }

}
