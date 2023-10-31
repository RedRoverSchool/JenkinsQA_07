package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.UUID;

public class FreeStyleProject8Test extends BaseTest {
    @Test()
    public void testCreateFreeStyleProject() {
        int desiredLength = 5;

        String testFreeStyleProjectName = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);

        WebElement addNewProjectButton = getDriver().findElement(By.xpath("//span[@class='task-icon-link']"));
        addNewProjectButton.click();
        WebElement jenkinsJobNameField = getDriver().findElement(By.xpath("//*[@class='jenkins-input']"));
        jenkinsJobNameField.sendKeys(testFreeStyleProjectName);
        WebElement freeStyleProject = getDriver().findElement(By.xpath("//*[text()='Freestyle project']"));
        freeStyleProject.click();
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();
        String jenkinsJobName = getDriver().findElement(By.xpath("//*[@class='job-index-headline page-headline']")).getText();

        Assert.assertTrue(jenkinsJobName.contains(testFreeStyleProjectName));
    }
}

