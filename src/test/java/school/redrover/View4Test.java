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

    final String LIST_VIEW_NAME = "List View Sv";

    private void createFreestyleProject() {

        getDriver().findElement(By.xpath("//*[@href=\"/view/all/newJob\"]")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("One more Freestyle project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

    }

    @Test
    public void testCreateView() {

        createFreestyleProject();

        getDriver().findElement(By.xpath("//a[@href=\"/newView\"]")).click();
        getDriver().findElement(By.id("name")).sendKeys(LIST_VIEW_NAME);
        getDriver().findElement(By.xpath("//*[@class='jenkins-radio']//*[text()='List View']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit'][@id='ok']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit' and contains(text(), 'OK')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(),
                LIST_VIEW_NAME
        );

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='tabBar']/div[2]/a")).getText(),
                LIST_VIEW_NAME
        );

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='tabBar']/div[2]/a")).getText(),
                LIST_VIEW_NAME
        );
    }
}
