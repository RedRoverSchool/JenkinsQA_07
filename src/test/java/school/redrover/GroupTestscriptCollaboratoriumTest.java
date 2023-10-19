package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class GroupTestscriptCollaboratoriumTest extends BaseTest {
    @Test
    public void testVersion() {

        getDriver().findElement(By.xpath("//*[@id = 'jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        WebElement version = getDriver().findElement(By.xpath("//p[@class = 'app-about-version']"));

        Assert.assertEquals(version.getText(), "Version 2.414.2");
    }

    @Test
    public void testCreateNewPipelineProject() {

        final String projectName = "TestNew";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Pipeline %s", projectName));
    }

}
