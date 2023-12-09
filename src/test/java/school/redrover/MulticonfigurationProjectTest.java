package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.MultiConfigurationConfigurePage;
import school.redrover.model.MultiConfigurationDetailsPage;
import school.redrover.model.NewItemPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String DESCRIPTION = "This is a description!!!";
    private static final String PROJECT_NAME = "MultiConfigurationProject123";

    @Test
    public void testCreateWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectMultiConfigurationProject()
                .clickOk(new MultiConfigurationConfigurePage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(PROJECT_NAME));
    }

    @Test
    public void testCreateWithEmptyName() {
        boolean validationMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("")
                .selectMultiConfigurationProject()
                .inputValidationMessage(
                        "» This field cannot be empty, please enter a valid name");

        Assert.assertTrue(validationMessage);
        Assert.assertTrue(getDriver().findElement(By.cssSelector(".disabled")).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateWithValidName")
    public void testCreateWithDuplicateName() {
        boolean duplicateName = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .inputValidationMessage(
                        "» A job already exists with the name ‘" + PROJECT_NAME + "’");

        Assert.assertTrue(duplicateName);
    }

    @Test
    public void testCreateProjectWithDescription() {
        String description = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectMultiConfigurationProject()
                .clickOk(new MultiConfigurationConfigurePage(getDriver()))
                .inputDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateWithValidName")
    public void testAddDescription() {
        String description = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .inputDescription(DESCRIPTION)
                .clickSaveDescriptionButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateProjectWithDescription")
    public void testEditDescription() {
        final String newDescription = "newDescription123?!";

        String description = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescription()
                .inputDescription(newDescription)
                .clickSaveDescriptionButton()
                .getDescriptionText();

        Assert.assertEquals(description, newDescription);
    }

    @Test(dependsOnMethods = {"testCreateProjectWithDescription", "testEditDescription"})
    public void testDeleteDescription() {
        String delete = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationDetailsPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescription()
                .clickSaveDescriptionButton()
                .getDescriptionText();

        Assert.assertEquals(delete, "");
    }

    @Test(dependsOnMethods = {"testCreateWithValidName", "testAddDescription"})
    public void testCancelDelete() {
        String page = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationDetailsPage(getDriver()))
                .taskLinkDeleteMultiConfigurationProject()
                .cancelDelete()
                .getProjectHeadLineText();

        Assert.assertEquals(page, "Project " + PROJECT_NAME);
    }

    @Test(dependsOnMethods = {"testCreateProjectWithDescription", "testEditDescription", "testDeleteDescription"})
    public void testDelete() {
        String page = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationDetailsPage(getDriver()))
                .taskLinkDeleteMultiConfigurationProject()
                .acceptAlert()
                .getHeadLineText();

        Assert.assertEquals(page, "Welcome to Jenkins!");
    }

    @Test
    public void testCreateWithInvalidValueData() {
        final String INVALID_NAME = "#!@#$%^&*()";
        String invalidMessage = "» ‘#’ is an unsafe character";

        boolean invalidTypeOfValue = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(INVALID_NAME)
                .selectMultiConfigurationProject()
                .inputValidationMessage(invalidMessage);

        Assert.assertTrue(invalidTypeOfValue);
    }
}
