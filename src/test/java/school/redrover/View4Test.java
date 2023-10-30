package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View4Test extends BaseTest {

    private void createNewFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }

    private void goToHomepage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewListView(String viewName) {
        goToHomepage();
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
    }

    private void createNewMyView(String viewName) {
        goToHomepage();
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(viewName);
        getDriver().findElement(By.xpath("//input[@id='hudson.model.MyView']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
    }

    private void createNewListViewWithAllJobs(String viewName) {
        goToHomepage();
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(viewName);
        getDriver().findElement(By.xpath("//input[@id='hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        getDriver().findElement(
                By.xpath("//div[@class='listview-jobs']//input[@type='checkbox']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCreateNewView() {
     final String freestyleProjectName = "FreestyleProject-1";
     final String viewName = "View-1";

     createNewFreestyleProject(freestyleProjectName);
     goToHomepage();
     createNewListView(viewName);
     goToHomepage();

     Assert.assertEquals(getDriver().findElement(
             By.xpath("//div[@class='tab']/a[@href='/view/View-1/']")).getText(), viewName);
    }
}
