package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject10Test extends BaseTest {

    private void creatingFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreatingFreestyleProject() {
        final String projectName = "NewFreestyleProject";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1['Configure']")).getText(),
                "Configure");
    }

    @Test
    public void testFreestyleProjectAddDescription() {
        final String nameDescription = "Test123%#";

        creatingFreestyleProject("FreestyleProject123");

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(nameDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-0']/div[1]")).getText(),
                nameDescription);
    }
}
