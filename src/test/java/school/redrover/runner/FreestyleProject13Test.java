package school.redrover.runner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.*;

/*
1. Create a freestyle project with valid name
2. See created project on a Dashboard
3. Can go to created project configuration page
*/
public class FreestyleProject13Test extends BaseTest {
    @Test
    public void testCreate() {
        final String findProjectName = "FreeStyleProjectName";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(findProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-head-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/FreeStyleProjectName/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + findProjectName);
    }
}
