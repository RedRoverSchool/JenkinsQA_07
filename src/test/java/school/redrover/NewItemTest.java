package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertTrue;

public class NewItemTest extends BaseTest {

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createFreeStyleProject(String projectName) {
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testNewItemFromExistedJobFieldIsDisplayedWhenItemCreated(){
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();

        boolean isDisplayed = !getDriver().findElements(By.className("item-copy")).isEmpty();
        assertTrue(isDisplayed);
    }
}
