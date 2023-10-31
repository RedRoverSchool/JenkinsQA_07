package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View4Test extends BaseTest {

    private static final String VIEW_NAME = "ListView-1";
    private static final String JOB_NAME = "FreestyleProject-1";


    private void createNewFreestyleProject(String jobName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(JOB_NAME);
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

    @Test
    public void testCreateNewView() {

        createNewFreestyleProject(JOB_NAME);
        createNewListView(VIEW_NAME);
        goToHomepage();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//div[@class='tab']/a[@href='/view/ListView-1/']")).getText(),
                "ListView-1");
    }

    @Test
    public void testRenameView() {
        final String viewNameNew = "ListView-new";

        createNewFreestyleProject(JOB_NAME);
        createNewListView(VIEW_NAME);
        goToHomepage();

        getDriver().findElement(By.xpath("//a[contains(text(),'" + VIEW_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/ListView-1/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).clear();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(viewNameNew);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'tab active']/a")).getText(),
                viewNameNew);
    }
}

