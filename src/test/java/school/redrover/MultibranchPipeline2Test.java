package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Multi";

    public void multibranchCreation() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(),'Multibranch Pipeline')]")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testMultibranchNameDisplayBreadcrumbTrail() {

        multibranchCreation();
        String pipelineNameExpected =  getDriver().findElement(By.xpath("//a[contains(text(),'" + PIPELINE_NAME + "')]")).getText();
        Assert.assertEquals(pipelineNameExpected, PIPELINE_NAME);
    }

    @Test
    public void testMultibranchCreationFromExisting() {

        multibranchCreation();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.className("task-link")).click();

        getDriver().findElement(By.id("name")).sendKeys("Multi2");
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String expectedName = getDriver().findElement(By.xpath("//span[text()='Multi2']")).getText();
        Assert.assertEquals(expectedName, "Multi2");
    }

    @Test
    public void testMultibranchCreationFromNonExisting() {

        multibranchCreation();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.className("task-link")).click();

        getDriver().findElement(By.id("name")).sendKeys("Multi3");
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys("Multi0");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        String error = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(error, "Error");
    }

}
