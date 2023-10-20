package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class PetrovTest extends BaseTest {
    public final static String projectName="TestProject";
    @Test
    public void testCreateJenkinsProject() {

        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(projectName);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.linkText(projectName)).click();

        String createdProject=getDriver().findElement(By.cssSelector(".job-index-headline")).getText();

        Assert.assertEquals(createdProject, "Project "+projectName);

    }
}
