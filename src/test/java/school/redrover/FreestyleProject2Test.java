package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;

public class FreestyleProject2Test extends BaseTest {

    @Test
    public void testCreate() {
        final String projectName = "FreeStyleProjectName";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + projectName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + projectName);
    }

    @Test
    public void testGiveNewNameProject() {
        String jobName = "freestyleJob";
        String newJobName = "freestyleJob-2";

        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']/label[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().navigate().back();
        getDriver().navigate().back();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + jobName + "')]")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]/span")).click();
        getDriver().findElement(By.xpath("//input[@value='" + jobName + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value='" + jobName + "']")).sendKeys(newJobName);
        //getDriver().findElement(By.xpath("//div[@id='bottom-sticker']")).click();
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(),newJobName);

    }
}
