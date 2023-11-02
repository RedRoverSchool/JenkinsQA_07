package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject10Test extends BaseTest {

    private final static String NAME_FREESTYLE_PROJECT = "Freestyle Project 1 2 3";

    private void creatingFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreatingFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NAME_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]/span/a")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1['Configure']")).getText(),
                "Configure");
    }

    @Test
    public void testFreestyleProjectAddDescription() {
        final String nameDescription = "Test123%#";

        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(nameDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-0']/div[1]")).getText(),
                nameDescription);
    }

    @Test
    public void testRenameFreestyleProject() {
        final String newName = "Test Rename Project 3210";

        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();

        getDriver().findElement(By.name("newName")).sendKeys(newName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@class=' job-status-nobuilt']/td[3]/a")).getText(),
                newName);
    }

    @Test
    public void testDeleteFreestyleProject() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@data-message='Delete the Project ‘" + NAME_FREESTYLE_PROJECT + "’?']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElements(By.id("job_" + NAME_FREESTYLE_PROJECT)).size(), 0);
    }

    @Test
    public void testTooltipDiscardIsVisible() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]")).click();

        WebElement helpButton = getDriver().findElement(By.xpath("//a[@helpurl='/descriptor/hudson.model.FreeStyleProject/help/concurrentBuild']"));

        boolean toolTipIsVisible = true;
        new Actions(getDriver()).
                moveToElement(helpButton).
                perform();

        if (helpButton.getAttribute("title").equals("Help for feature: Execute concurrent builds if necessary")) {
            toolTipIsVisible = false;
        }

        Assert.assertTrue(toolTipIsVisible, "The tooltip message is not visible");
    }
}
