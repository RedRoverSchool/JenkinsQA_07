package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;

public class FreestyleProject1Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject";
    @Test
    public void testCreateFreestyle1Project() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        String actualName = getDriver()
                .findElement(By.xpath("//*[@id='job_FreestyleProject']/td[3]")).getText();
        Assert.assertEquals(actualName, PROJECT_NAME);
    }
}
