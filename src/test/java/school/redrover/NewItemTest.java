package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertFalse;
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

    private boolean isCloneItemSectionDisplayed() {
        return !getDriver().findElements(By.className("item-copy")).isEmpty();
    }

    @Test
    public void testNewItemFromExistedJobSectionIsDisplayedWhenItemCreated() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();

        assertTrue(isCloneItemSectionDisplayed());
    }

    @Test
    public void testNewItemFromExistedJobSectionIsNotDisplayedWhenNoItemsCreated() {
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();

        assertFalse(isCloneItemSectionDisplayed());
    }

}
