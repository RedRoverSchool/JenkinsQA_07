package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

//@Ignore
public class GroupTestscriptCollaboratoriumTest extends BaseTest {

    private void utilsCreateFreestyleProject(String projectName) {

        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[contains(@class, 'jenkins-input')]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//button[contains(@id, 'ok-button')]")).click();
    }

    @Test
    @Ignore
    public void testSearch() {

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testVersion() {

        WebElement buttonVersion = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"));
        buttonVersion.click();

        WebElement buttonVersionNext = getDriver().findElement(By.xpath("//a[@href='/manage/about']"));
        buttonVersionNext.click();

        WebElement version = getDriver().findElement(By.xpath("//p[@class='app-about-version']"));
        String valueVersion = version.getText();
        Assert.assertEquals(valueVersion, "Version 2.414.2");
    }

    @Test
    public void testJenkinsGetVersionInAboutMenu() {

        getDriver().findElement(By.className("page-footer__links")).findElement(By.tagName("button")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("app-about-version")).getText(),
                "Version 2.414.2");
    }

    @Test
    public void testJenkinsFreestyleProjectIsCreatedCheckByH1() {

        final String expectedProjectName = "testJenkinsFreestyleProjectIsCreatedCheckByH1()";

        utilsCreateFreestyleProject(expectedProjectName);
        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();

        String actualProjectName = getDriver().findElement(By
                .xpath("//h1[@class = 'job-index-headline page-headline']")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", expectedProjectName));
    }

    @Test
    public void testJenkinsFreestyleProjectIsCreatedCheckByBreadcrumb() {

        final String expectedProjectName = "testJenkinsFreestyleProjectIsCreatedCheckByBreadcrumb()";

        utilsCreateFreestyleProject(expectedProjectName);

        String actualProjectName = getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, 'job/%s/')]", expectedProjectName))).getText();

        Assert.assertEquals(actualProjectName, expectedProjectName);
    }

    @Test
    public void testJenkinsNewProjectIsDisplayedOnDashboard() {

        final String expectedProjectName = "testJenkinsNewProjectIsDisplayedOnDashboard()";

        utilsCreateFreestyleProject(expectedProjectName);

        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();

        String actualProjectName = getDriver().findElement(By.xpath("//div[contains(@class, 'dashboard')]"))
                .findElement(By.xpath(String.format("//a[contains(@href, 'job/%s/')]/span", expectedProjectName)))
                .getText();

        Assert.assertEquals(actualProjectName, expectedProjectName);
    }
}
