package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder13Test extends BaseTest {
    private final String folderName = "TestFolder";
    private final String freestyleProjectName = "TestProject";
    private void createFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();
        returnToDashboard();
    }

    private void createFreestyleProject(String freestyleProjectName) {
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();
    }

    private void returnToDashboard(){
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void createNewJobInsideFolder() {
        createFolder(folderName);
        getDriver().findElement(By.xpath("//span[text()='" + folderName  + "']")).click();
        getDriver().findElement(By.className("content-block__link")).click();
        createFreestyleProject(freestyleProjectName);
        returnToDashboard();
        getDriver().findElement(By.xpath("//span[text()='" + folderName  + "']")).click();
        Assert.assertTrue(getDriver().findElement(By.id("job_" + freestyleProjectName + "")).isDisplayed());
    }
}
