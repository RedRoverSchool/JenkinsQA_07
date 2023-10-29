package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;

public class Breadcrumb3Test extends BaseTest {
    final String jobName = "Unicorns" + getRandomNumber();

    private static String getRandomNumber() {

        Random random = new Random();
        return String.valueOf(100000000 + random.nextInt(900000000));
    }

    private void searchDashboardBtnAndClick() {

        getDriver().findElement(By.xpath("//li/a[@class = 'model-link']")).click();
    }

    private void createNewFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/all/']")).isDisplayed();
        getDriver().findElement(By.name("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//img[@class = 'icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testReturnOnMainPageFromNewItem() {

        createNewFreestyleProject();
        searchDashboardBtnAndClick();

        Assert.assertTrue(getDriver().findElement(By.id("tasks")).isDisplayed());
    }

    @Test
    public void testReturnOnMainPageFromPeople() {

        createNewFreestyleProject();
        searchDashboardBtnAndClick();

        getDriver().findElement(By.xpath("//a[@href = '/asynchPeople/']")).click();
        if (getDriver().findElement(By.xpath("//h1")).isDisplayed()) {
            searchDashboardBtnAndClick();
        }
        Assert.assertTrue(getDriver().findElement(By.id("breadcrumbBar")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("tasks")).isDisplayed());
    }

    @Test
    public void testReturnOnMainPageFromBuildHistory() {

        createNewFreestyleProject();
        searchDashboardBtnAndClick();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/builds']")).click();
        if (getDriver().findElement(By.xpath("//h1")).isDisplayed()) {
            searchDashboardBtnAndClick();
        }
        Assert.assertTrue(getDriver().findElement(By.id("breadcrumbBar")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("tasks")).isDisplayed());
    }

    @Test
    public void testReturnOnMainPageFromManageJenkins() {

        createNewFreestyleProject();
        searchDashboardBtnAndClick();

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        if (getDriver().findElement(By.xpath("//h1")).isDisplayed()) {
            searchDashboardBtnAndClick();
        }
        Assert.assertTrue(getDriver().findElement(By.id("breadcrumbBar")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("tasks")).isDisplayed());
    }

    @Test
    public void testReturnOnMainPageFromMyViews() {

        createNewFreestyleProject();
        searchDashboardBtnAndClick();

        getDriver().findElement(By.xpath("//a[@href = '/me/my-views']")).click();
        searchDashboardBtnAndClick();

        Assert.assertTrue(getDriver().findElement(By.id("breadcrumbBar")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("tasks")).isDisplayed());
    }
}
