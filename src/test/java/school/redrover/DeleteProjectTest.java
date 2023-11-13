
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.concurrent.locks.Condition;

public class DeleteProjectTest extends BaseTest {
    private static final String NAME_OF_FOLDER = "MyFolder";

    private void createProject() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(NAME_OF_FOLDER);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

    }

    @Test
    public void testCreateProject() {
        createProject();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//a[@href = 'job/" + NAME_OF_FOLDER + "/']")).getText(), NAME_OF_FOLDER);

    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testDeleteProject() {

        getDriver().findElement(By.xpath("//a[@href = 'job/" + NAME_OF_FOLDER + "/']")).click();

        getDriver().findElement(By.xpath("//a[contains(@class, 'confirmation-link')]")).click();
        getDriver().switchTo().alert().accept();

        String actualTitle = getDriver().findElement(By.xpath("//h2[contains(text(),'Start building your software project')]")).getText();

        Assert.assertEquals(actualTitle, "Start building your software project");

    }
}