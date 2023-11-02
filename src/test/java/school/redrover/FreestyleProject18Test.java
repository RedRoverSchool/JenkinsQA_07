package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject18Test extends BaseTest {

    @Test
    public void createProject() {
        final String projectName = "Freestyle Project";
        getDriver().findElement(By.xpath("//a [@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(projectName);

        getDriver().findElement(By.xpath("//li[@class = 'hudson_model_FreeStyleProject']")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(), "Project " + projectName);

    }
}
