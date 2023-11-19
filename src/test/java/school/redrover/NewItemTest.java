package school.redrover;

import org.testng.annotations.Test;
import school.redrover.model.ErrorPage;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.*;

public class NewItemTest extends BaseTest {

    @Test
    public void testNewItemFromExistedJobSectionIsDisplayedWhenItemCreated() {
        final String projectName = "Test Project";

        boolean isCloneItemSectionDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(projectName)
                .goHomePage()
                .clickNewItem()
                .isCloneItemSectionDisplayed();

        assertTrue(isCloneItemSectionDisplayed);
    }

    @Test
    public void testNewItemFromExistedJobSectionIsNotDisplayedWhenNoItemsCreated() {
        boolean isCloneItemSectionDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .isCloneItemSectionDisplayed();

        assertFalse(isCloneItemSectionDisplayed);
    }

    @Test
    public void testAutocompleteListOfCopyFromFieldWithItemCreated() {
        final String firstProject = "Test project";
        final String secondProject = "Test project 2";

        boolean isAutocompleteSuggested = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(firstProject)
                .goHomePage()
                .clickNewItem()
                .typeItemName(secondProject)
                .enterExistentItemNameToClone(firstProject.substring(0, 4))
                .isAutocompleteToCloneSuggested(firstProject);

        assertTrue(isAutocompleteSuggested);
    }

    @Test
    public void testNewItemCreationWithNonExistentName() {
        final String firstProject = "Test project";
        final String secondProject = "Test project 2";
        final String nonExistentProject = "Test project 3";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(firstProject)
                .goHomePage()
                .clickNewItem()
                .typeItemName(secondProject)
                .enterExistentItemNameToClone(nonExistentProject)
                .clickOk(new ErrorPage(getDriver()))
                .getErrorMessage();

        assertEquals(errorMessage, ("Error\n" + "No such job: " + nonExistentProject));
    }

    @Test
    public void testNewItemCreationWithExistentName() {
        final String firstProject = "Test project";
        final String secondProject = "Test project 2";

        boolean isProjectCreated = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(firstProject)
                .goHomePage()
                .clickNewItem()
                .typeItemName(secondProject)
                .enterExistentItemNameToClone(firstProject)
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .goHomePage()
                .isProjectExist(secondProject);

        assertTrue(isProjectCreated);
    }
}
