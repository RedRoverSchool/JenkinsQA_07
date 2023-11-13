package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class View4Test extends BaseTest {

    private static final String JOB_NAME = "FreestyleProject-1";
    private static final String JOB_NAME_1 = "FreestyleProject-2";
    private static final String JOB_NAME_2 = "FreestyleProject-3";
    private static final String VIEW_NAME = "ListView-1";
    private static final String VIEW_NAME_1 = "ListView-new";

    private void goToHomepage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewFreestyleProject(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void createNewListViewWithoutJobs() {
        goToHomepage();
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"))
                .sendKeys(View4Test.VIEW_NAME);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
    }

    @Test
    public void testCreateNewListViewWithoutJobs() {
        createNewFreestyleProject(JOB_NAME);
        goToHomepage();
        createNewListViewWithoutJobs();
        goToHomepage();

        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class='tabBar']/div"));
        List<String> actualViewsNames = new ArrayList<>();
        for (WebElement el : listOfViews) {
            actualViewsNames.add(el.getText());
        }

        Assert.assertTrue(actualViewsNames.contains(VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithoutJobs")
    public void testRenameListView() {
        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME + "/']"))
                .click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + VIEW_NAME + "/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).clear();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(VIEW_NAME_1);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'tab active']/a"))
                        .getText(),
                VIEW_NAME_1);
    }

    @Test(dependsOnMethods = "testRenameListView")
    public void testAddJobToListViewWithoutJobs_fromMainSectionLink() {
        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME_1 + "/']"))
                .click();

        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText()
                .contains("This view has no jobs associated with it. You can either add some existing jobs to this " +
                        "view or create a new job in this view."));

        getDriver().findElement(By.xpath("//a[@href='configure']")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        getDriver().findElement(By.xpath("//label[@title='" + JOB_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]"))
                        .getText(),
                JOB_NAME);
    }

    @Test(dependsOnMethods = "testAddJobToListViewWithoutJobs_fromMainSectionLink")
    public void testAddAllJobsToView() {
        createNewFreestyleProject(JOB_NAME_1);
        goToHomepage();
        createNewFreestyleProject(JOB_NAME_2);
        goToHomepage();

        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME_1 + "/']"))
                .click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + VIEW_NAME_1 + "/configure']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");

        getDriver().findElement(By.xpath("//label[@title='" + JOB_NAME + "']")).click();
        List<WebElement> listOfJobs = getDriver().findElements(
                By.xpath("//div[@class='listview-jobs']/span/span/label"));
        for (WebElement job : listOfJobs) {
            job.click();
        }
        getDriver().findElement(By.name("Submit")).click();

        List<String> jobNames = List.of(JOB_NAME, JOB_NAME_1, JOB_NAME_2);
        List<WebElement> dashboardItems = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr/td"));
        List<String> jobNamesDashboard = new ArrayList<>();
        for (WebElement item : dashboardItems) {
            if (item.getText().contains("FreestyleProject")) {
                jobNamesDashboard.add(item.getText());
            }
        }

        Assert.assertEquals(jobNamesDashboard.size(), jobNames.size());
        for (int i = 0; i < jobNamesDashboard.size(); i++) {
            Assert.assertEquals(jobNames.get(i), jobNamesDashboard.get(i));
        }
    }

    @Test(dependsOnMethods = "testAddAllJobsToView")
    public void testOrderColumns() {
        goToHomepage();
        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME_1 + "/']"))
                .click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + VIEW_NAME_1 + "/configure']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,950)");

        Actions action = new Actions(getDriver());
        action.clickAndHold(getDriver().findElement(By.xpath("(//div[@class='dd-handle'])[3]")))
                .moveToElement(getDriver().findElement(By.xpath("(//div[@class='dd-handle'])[5]")))
                .release(getDriver().findElement(By.xpath("(//div[@class='dd-handle'])[5]")))
                .build()
                .perform();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<WebElement> row1 = getDriver().findElements(By.xpath("//tr[@id='job_" + JOB_NAME + "']/td"));
        int columnNumberAfterOrdering = 0;
        for (WebElement element : row1) {
            if (element.getText().contains("FreestyleProject")) {
                columnNumberAfterOrdering = row1.indexOf(element) + 1;
                break;
            }
        }

        Assert.assertEquals(columnNumberAfterOrdering, 5);
    }
}


