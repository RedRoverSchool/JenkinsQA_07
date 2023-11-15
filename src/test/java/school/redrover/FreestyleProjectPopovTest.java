package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectPopovTest extends BaseTest{

    private final static String PROJECT_NAME = "NewFreestyleProject";
    private final static String PROJECT_DESCRIPTION = "New Test Project";

    private void createProject(){
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();
    }

    @Ignore
    @Test
    public void testCreateProject() {
        createProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Assert.assertEquals(getDriver().findElement(By.partialLinkText(PROJECT_NAME)).getText(),
                PROJECT_NAME);

        getDriver().findElement(By.partialLinkText(PROJECT_NAME)).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href,'configure')]")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("textarea[name='description']")).getText(),
                PROJECT_DESCRIPTION);
    }

    @Ignore
    @Test
    public void testDeleteProject() {
        createProject();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }
}
