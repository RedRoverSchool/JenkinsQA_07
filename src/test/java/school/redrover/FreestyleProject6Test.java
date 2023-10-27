package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject6Test extends BaseTest {

        @Test
        public void testCreate() {
            final String projectName = "FreestyleProject5";

            getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
            getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(projectName);
            getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
            getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

            getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
            getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

            getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']")).click();

            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//h1[normalize-space()='Project FreestyleProject5']")).getText(),
                    "Project " + projectName);
        }
    }

