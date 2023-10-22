package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GroupCarlTheFogTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String NEW_PROJECT_NAME = "newFreestyleProject";

    private void createNewFreestyleProject(String projectName) {
        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.cssSelector("input[type='text']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
    }

    @Test
    public void testJenkinsGreetings() {
        String JenkinsGreetings = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals("Welcome to Jenkins!", JenkinsGreetings);
    }

    @Test
    public void testCreateNewFreestyleProject() {
        createNewFreestyleProject(PROJECT_NAME);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1.job-index-headline")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testRenameFreestyleProject() {
        createNewFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[href*='rename']")).click();
        getDriver().findElement(By.cssSelector("input[name=newName]")).clear();
        getDriver().findElement(By.cssSelector("input[name=newName]")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1.job-index-headline")).getText(),
                "Project " + NEW_PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProjectFromSideMenu() {
        createNewFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[data-message*=Delete]")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div.empty-state-block h1")).getText(),
                "Welcome to Jenkins!");
    }
}
