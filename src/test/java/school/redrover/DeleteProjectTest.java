package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DeleteProjectTest extends BaseTest {
    private static final String NAMEFOLDER = "MyFolder";

    private void createProject(String name) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(name);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

    }

    @Test
    public void testProject() {

        createProject(NAMEFOLDER);
        getDriver().findElement(By.xpath("//a[contains(@class, 'confirmation-link')]")).click();
        getDriver().switchTo().alert().accept();

        String actualTitle = getDriver().findElement(By.xpath("//h2[contains(text(),'Start building your software project')]")).getText();

        Assert.assertEquals(actualTitle, "Start building your software project");

    }
}
