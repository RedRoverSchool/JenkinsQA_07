package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder13Test extends BaseTest {
    private static final String FOLDER_NAME = "TestFolder";
    private static final String FREESTYLE_PROJECT_NAME = "TestProject";
    private void createFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(Folder13Test.FOLDER_NAME);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();
        returnToDashboard();
    }

    private void createFreestyleProject() {
        getDriver().findElement(By.id("name")).sendKeys(Folder13Test.FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();
    }

    private void returnToDashboard(){
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void createNewJobInsideFolder() {
        createFolder();
        getDriver().findElement(By.xpath("//span[text()='" + FOLDER_NAME + "']")).click();
        getDriver().findElement(By.className("content-block__link")).click();
        createFreestyleProject();
        returnToDashboard();
        getDriver().findElement(By.xpath("//span[text()='" + FOLDER_NAME + "']")).click();
        Assert.assertTrue(getDriver().findElement(By.id("job_" + FREESTYLE_PROJECT_NAME + "")).isDisplayed());
    }
}
