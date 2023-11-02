package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject16Test extends BaseTest {

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createFreestyleProject("create project test");

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'create project test')]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText(), "Project create project test");
    }
}
