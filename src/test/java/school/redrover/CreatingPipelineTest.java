package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatingPipelineTest extends BaseTest {

    private final String NAMEPIPLINE = "MyPipline";

    private void createPipline(String name) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(name);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

    }

    @Test
    public void testCreatePipline() {

        createPipline(NAMEPIPLINE);
        String titleName = getDriver()
                .findElement(By.xpath("//a[@href = 'job/" + NAMEPIPLINE + "/']/span[contains(text(),'" + NAMEPIPLINE + "')]"))
                .getText();

        getDriver().findElement(By.xpath("//a[@href = 'job/" + NAMEPIPLINE + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + NAMEPIPLINE + "/configure']")).click();

        String text = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']/h1")).getText();

        Assert.assertEquals(titleName, NAMEPIPLINE);
        Assert.assertEquals(text, "Configure");
    }
}
