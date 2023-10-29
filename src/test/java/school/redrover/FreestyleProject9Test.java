package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class FreestyleProject9Test extends BaseTest {

    @Test
    public void testCreat() {
        /*
        1.Create a freestyle project with valid name
        2.See created project on a Dashboard
        3.Can go to created project configuration page
         */
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("FreeStyleProjectName");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/FreeStyleProjectName/']")).click();

                Assert.assertEquals(
                        getDriver().findElement(By.cssSelector("#main-panel >h1")).getText(),
                        "Project FreeStyleProjectName");

    }
}
