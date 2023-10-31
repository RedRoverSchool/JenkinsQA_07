package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View4Test extends BaseTest {

    private void createNewFreestyleProject() {
        final String jobName = "FreestyleProject-1";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }

    private void goToHomepage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewListView() {
        final String viewName = "ListView-1";
        goToHomepage();
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
    }

    @Test
    public void testCreateNewView() {
     createNewFreestyleProject();
     goToHomepage();
     createNewListView();
     goToHomepage();

     Assert.assertEquals(getDriver().findElement(
             By.xpath("//div[@class='tab']/a[@href='/view/ListView-1/']")).getText(),
             "ListView-1");
    }
}
