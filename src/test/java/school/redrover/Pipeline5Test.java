package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline5Test extends BaseTest {

    private boolean isElementExist(String xpath) {
        By locator = By.xpath(xpath);
        try {
            getDriver().findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testDelete() {
        final String pipelineName = "PipelineDelete";

        getDriver().findElement(By.xpath("//span[@class='task-link-wrapper ']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate ]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Pipeline " + pipelineName);

        getDriver().findElement(By.xpath("//a[@data-message]")).click();
        getDriver().switchTo().alert().accept();

        isElementExist("//span[contains(text(), '" + pipelineName + "')]");
    }


    @Test
    public void testCreate() {
        final String pipelineName = "Pipeline5";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[normalize-space()='" + pipelineName + "']")).getText(),
                "Pipeline5");
    }

}
