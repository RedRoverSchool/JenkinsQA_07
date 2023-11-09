package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreeStyleProject24Test extends BaseTest {

    private void createNewFreeStyleProject() {

        getDriver().findElement(By.xpath("(//*[@class = 'task-icon-link']) [1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("New FP");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testAddDescriptionToFreeStyleProject() {
        
        final String projectDescription = "New Description";

        createNewFreeStyleProject();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(projectDescription);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), projectDescription);
    }
}
