package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.view.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;
import java.util.UUID;

public class ViewTest extends BaseTest {

    private static final String JOB_NAME = "FreestyleProject-1";
    private static final String JOB_NAME_1 = "FreestyleProject-2";
    private static final String JOB_NAME_2 = "Folder";
    private static final String JOB_NAME_3 = "Multibranch Pipeline Name";
    private static final String VIEW_NAME = "ListView-1";
    private static final String VIEW_NAME_1 = "ListView-new";
    private static final String NEW_DESCRIPTION_FOR_THE_VIEW = "Test description for the List View";
    private static final String EDITED_DESCRIPTION_FOR_THE_VIEW = "New Test description for the List View instead of the previous one";
    private static final String NO_ASSOCIATED_JOBS_FOR_THE_VIEW_MESSAGE = "This view has no jobs associated with it. " +
            "You can either add some existing jobs to this view or create a new job in this view.";

    private void createListViewWithoutAssociatedJob() {
        new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .goHomePage();
    }

    private void createListViewWithAssociatedJob() {
        new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .checkFirstJobCheckbox()
                .clickOKButton()
                .goHomePage();
    }

    private void addNewDescriptionForTheView() {
        new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(NEW_DESCRIPTION_FOR_THE_VIEW)
                .clickSaveDescription()
                .goHomePage();
    }

    @Test
    public void testCreateNewView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);

        String view = new HomePage(getDriver())
                .clickMyViews()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .clickOKButton()
                .getActiveViewName();

        Assert.assertEquals(view,VIEW_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewListView")
    public void testRenameView() {
        final String renamedViewName = "Renamed View Name";

        HomePage homePage = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .typeNewName(renamedViewName)
                .clickOKButton()
                .goHomePage();

        Assert.assertTrue(homePage.getViewsList().contains(renamedViewName));
    }

    @Test
    public void testCreateNewView2() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);

        List<String> list = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectMyViewType()
                .clickCreateButton()
                .goHomePage()
                .getViewsList();

        Assert.assertTrue(list.contains(VIEW_NAME));
    }

    @Test
    public void testDeleteViewOnDashboard() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);

        boolean deleteView = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .clickDeleteView()
                .acceptAlert()
                .isViewExist(VIEW_NAME);

        Assert.assertFalse(deleteView);
    }

    @Test()
    public void testCreateNewEmptyView() {
        TestUtils.createFolder(this, JOB_NAME_2, true);

        boolean viewInBreadcrumbBar = new HomePage(getDriver())
                .clickMyViews()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .isItemExistInBreadcrumbBar(VIEW_NAME);

        Assert.assertTrue(viewInBreadcrumbBar);
    }

    @Test
    public void testAddingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob();

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(NEW_DESCRIPTION_FOR_THE_VIEW)
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description, NEW_DESCRIPTION_FOR_THE_VIEW);
    }

    @Test
    public void testEditingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob();

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(EDITED_DESCRIPTION_FOR_THE_VIEW)
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description, EDITED_DESCRIPTION_FOR_THE_VIEW);
    }

    @Test
    public void testDeletingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob();
        addNewDescriptionForTheView();

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description,"");
    }

    @Test
    public void testNoJobsShownForTheViewWithoutAssociatedJob() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob();

        String mainPanelText = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .getMainPanelText();

        Assert.assertTrue(mainPanelText.contains(NO_ASSOCIATED_JOBS_FOR_THE_VIEW_MESSAGE));
    }

    @Test
    public void testProjectCouldBeAddedToTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob();

        List<String> jobList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .checkSelectedJobCheckbox(JOB_NAME)
                .clickOKButton()
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Test
    public void testAssociatedJobIsShownOnTheViewDashboard() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithAssociatedJob();

        List<String> jobList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Test
    public void testAddingNewColumnToTheView() {
        final String newColumnName = "Git Branches";

        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithAssociatedJob();

        List<String> columnNamesList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .addColumn(newColumnName)
                .clickOKButton()
                .getColumnNamesList();

        Assert.assertTrue(columnNamesList.contains(newColumnName));
    }

    @Test(dependsOnMethods = "testAddingNewColumnToTheView")
    public void testDeletingColumnFromTheView() {
        final String deletedColumnName = "Last Duration";

        List<String> columnNamesList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .deleteColumn("Last Duration")
                .clickOKButton()
                .getColumnNamesList();

        Assert.assertFalse(columnNamesList.contains(deletedColumnName));
    }

    @Test(dependsOnMethods = "testDeletingColumnFromTheView")
    public void testReorderColumnsForTheView() {

        final String reorderedColumnName = "Name";
        List<String> columnNamesList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .moveColumnToFirstPosition(reorderedColumnName)
                .clickOKButton()
                .getColumnNamesList();


        Assert.assertTrue(columnNamesList.get(0).contains(reorderedColumnName));
    }

    @Test
    public void testCreateListViewWithoutJobs() {
        List<String> viewsList = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(JOB_NAME)
                .goHomePage()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .goHomePage()
                .getViewsList();

        Assert.assertTrue(viewsList.contains(VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateListViewWithoutJobs")
    public void testRenameListView() {
        List<String> viewsList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ViewPage(getDriver()))
                .clickEditView()
                .typeNewName(VIEW_NAME_1)
                .clickOKButton()
                .goHomePage()
                .getViewsList();

        Assert.assertTrue(viewsList.contains(VIEW_NAME_1));
    }

    @Test(dependsOnMethods = "testRenameListView")
    public void testJobCanBeAddedFromMainPanel() {
        boolean noJobsMessage = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME_1, new ViewPage(getDriver()))
                .getMainPanelText()
                .contains(NO_ASSOCIATED_JOBS_FOR_THE_VIEW_MESSAGE);

        Assert.assertTrue(noJobsMessage);

        List<String> jobList = new ViewPage(getDriver())
                .clickAddJobsFromMainPanel()
                .checkFirstJobCheckboxWithJavaExecutor()
                .clickOKButton()
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Test(dependsOnMethods = "testJobCanBeAddedFromMainPanel")
    public void testAddSeveralJobsToView() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(JOB_NAME_1)
                .goHomePage()
                .clickViewByName(VIEW_NAME_1, new ViewPage(getDriver()))
                .clickEditView()
                .checkJobsCheckboxesWithJavaExecutor()
                .clickOKButton()
                .goHomePage()
                .getJobList();

        Assert.assertTrue(jobList.containsAll(List.of(JOB_NAME, JOB_NAME_1)));
    }

    @Test
    public void testCreateViewWithOptionGlobalView() {
        final String VIEW_NAME = UUID.randomUUID().toString();
        TestUtils.createFreestyleProject(this, UUID.randomUUID().toString(), true);

        String nameViewNameActual = new HomePage(getDriver())
                .clickMyViews()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .clickIncludeGlobalViewTypeRadioBTN()
                .clickCreateButton()
                .clickOkButton()
                .getActiveViewName();

        Assert.assertEquals(nameViewNameActual, VIEW_NAME);
    }

    @Test
    public void testCreateNewListView() {
        TestUtils.createFolder(this, JOB_NAME_2, true);
        TestUtils.createMultibranchPipeline(this, JOB_NAME_3, true);

        String expectedListViewName = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .clickCheckboxByTitle(JOB_NAME_3)
                .clickOKButton()
                .getActiveViewName();

        Assert.assertEquals(VIEW_NAME, expectedListViewName);
    }
}
