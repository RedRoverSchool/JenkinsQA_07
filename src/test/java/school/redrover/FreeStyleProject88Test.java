package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.UUID;

public class FreeStyleProject88Test extends BaseTest {

    @Test
    public void testAddDescription() {

        createFreestyleProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        String actualDescription = createUniqueTextValue();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(actualDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().navigate().refresh();
        String expectedDescription = getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();
        Assert.assertEquals(actualDescription, expectedDescription);

    }

    @Test
    public void testEditDescription() {
        final String expectedDescription = "New freestyle Description text";
        createFreestyleProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        String newDescription = createUniqueTextValue();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(newDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().navigate().refresh();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(expectedDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        String text = getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();
        String actualDescription = text.replaceAll(newDescription, "");
        Assert.assertEquals(actualDescription, expectedDescription);

    }

    private void createFreestyleProject() {

        String testFreeStyleProjectName = createUniqueTextValue();

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
    }


    private String createUniqueTextValue() {
        int desiredLength = 5;

        String testFreeStyleProjectName = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);
        return testFreeStyleProjectName;
    }

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }
}