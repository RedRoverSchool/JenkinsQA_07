package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject32Test extends BaseTest {

    private void createNewFreestyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCreateProject(){
        String projectName = "Freestyle Project";
        createNewFreestyleProject(projectName);
        String actualProjectName = getDriver().findElement(By.id("main-panel")).getText();
        Assert.assertTrue(actualProjectName.contains(projectName));
    }
}
