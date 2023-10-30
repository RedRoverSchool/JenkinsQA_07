package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;
@Ignore
public class ViktoriiaTest extends BaseTest {
@Ignore
    @Test
    public void testMyViewCreateJob() {

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement((By.xpath("//a[@href='newJob']"))).click();
        getDriver().findElement(By.cssSelector("input#name")).sendKeys("new project");
        getDriver().findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        getDriver().findElement(By.tagName("h2")).getText();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys("It is my first project");
        getDriver().findElement(new By.ByName("Submit")).click();
        getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText();
        getDriver().findElement(By.linkText("Dashboard")).click();

        WebElement projectName = getDriver().findElement(By.cssSelector("a.jenkins-table__link.model-link.inside"));

        Assert.assertTrue(projectName.isDisplayed());
    }
}
