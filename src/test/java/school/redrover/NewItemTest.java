package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.*;

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

    @Test
    public void testAutocompleteListOfCopyFromFieldWithItemCreated() {
        final String firstProject = "Test project";
        final String secondProject = "Test project 2";
        createFreeStyleProject(firstProject);
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys(secondProject);
        getDriver().findElement(By.id("from")).sendKeys(firstProject.substring(0, 4));

        boolean isAutocompleteSuggested = !getDriver()
                .findElements(By.xpath("//li[contains(text(),'" + firstProject + "')]"))
                .isEmpty();
        assertTrue(isAutocompleteSuggested);
    }

    @Test
    public void testNewItemCreationWithNonExistentName() {
        final String firstProject = "Test project";
        final String secondProject = "Test project 2";
        final String nonExistentProject = "Test project 3";
        createFreeStyleProject(firstProject);
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys(secondProject);
        getDriver().findElement(By.id("from")).sendKeys(nonExistentProject);
        getDriver().findElement(By.id("ok-button")).click();

        String expectedErrorMessage = "Error\nNo such job: " + nonExistentProject;
        String actualErrorMessage = getDriver().findElement(By.id("main-panel")).getText();
        assertEquals(actualErrorMessage, expectedErrorMessage);
    }

}
