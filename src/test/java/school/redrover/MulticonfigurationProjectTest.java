package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.jobs.configs.MultiConfigurationConfigurePage;
import school.redrover.model.jobs.details.MultiConfigurationDetailsPage;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String DESCRIPTION = "This is a description!!!";
    private static final String PROJECT_NAME = "MultiConfigurationProject123";

    @DataProvider
    public Object[][] unsafeCharacters() {
        return new Object[][]{
                {PROJECT_NAME + "!"}, {PROJECT_NAME + "@"}, {PROJECT_NAME + "#"}, {PROJECT_NAME + "$"},
                {PROJECT_NAME + "%"}, {PROJECT_NAME + "^"}, {PROJECT_NAME + "&"}, {PROJECT_NAME + "*"},
        };
    }

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

    @Test(dataProvider = "unsafeCharacters")
    public void testCreateWithInvalidName(String invalidChar) {
        String errorMassage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(invalidChar)
                .selectMultiConfigurationProject()
                .getInvalidNameErrorMessage();

        Assert.assertTrue(errorMassage.contains("is an unsafe character"));
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
                .clearDescriptionTextArea()
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
                .clearDescriptionTextArea()
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
