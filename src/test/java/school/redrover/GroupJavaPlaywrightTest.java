package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class GroupJavaPlaywrightTest extends BaseTest {

    @Test
    public void testCreateFreeStyleProject() throws InterruptedException {
        String projectName = "Project1";

        JenkinsUtils.login(getDriver());

        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[contains(@href, 'newJob')]")).click();

        Thread.sleep(1000);

        getDriver().findElement(By.id("name")).sendKeys(projectName);

        getDriver().findElement(By.xpath("//span[@class = 'label'][text()='Freestyle project']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(1000);

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Thread.sleep(2000);

        Assert.assertTrue(getDriver().findElement(By.id("projectstatus")).isDisplayed());
        Assert.assertEquals(getDriver().findElements(By.xpath("//table[@id='projectstatus']/tbody/tr")).size(), 1);

        WebElement tableRow = getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr"));
        Assert.assertEquals(tableRow.getAttribute("id"), "job_" + projectName);

    }
}
