package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.SeleniumUtils;
import school.redrover.runner.TestUtils;

import java.time.Duration;
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

    private void createListViewWithoutAssociatedJob(String newListViewName) {
        new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(newListViewName)
                .selectListViewType()
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .goHomePage();
    }

    private void createListViewWithAssociatedJob(String newListViewName) {
        new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(newListViewName)
                .selectListViewType()
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .checkFirstJobCheckbox()
                .clickOKButton(new ListViewPage(getDriver()))
                .goHomePage();
    }

    private void addNewDescriptionForTheView(String listViewName, String newDescriptionForTheView) {
        new HomePage(getDriver())
                .clickViewByName(listViewName, new ListViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(newDescriptionForTheView)
                .clickSaveDescription()
                .goHomePage();
    }

    @Test
    public void testCreateNewView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);

        String view = new HomePage(getDriver())
                .clickMyView()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .clickOKButton(new MyViewPage(getDriver()))
                .getMyViewName();

        Assert.assertEquals(view, VIEW_NAME);
    }

    @Test
    public void testBreadCrumbNewItem(){
        Actions actions = new Actions(getDriver());

        WebElement dash = getDriver().findElement(By.cssSelector("#breadcrumbBar a"));
        actions.moveToElement(dash).moveByOffset(dash.getSize().getWidth() / 2,0).pause(Duration.ofMillis(500)).perform();

        //actions.pause(Duration.ofMillis(300)).click().perform();

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//button")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[@class='jenkins-dropdown__item'][1]")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".h3")).getText(), "Enter an item name");
    }

    @Test
    public void testJobChevron() {
        TestUtils.createFreestyleProject(this, SeleniumUtils.generateRandomName(), true);
        Actions actions = new Actions(getDriver());
        WebElement job = getDriver().findElement(By.xpath("//td/a[contains(@href,'job/')]"));

        actions.moveToElement(job).moveByOffset(job.getSize().getWidth() / 2,0).pause(Duration.ofMillis(500)).click().perform();

        actions.pause(Duration.ofMillis(300)).click().perform();
        actions.pause(Duration.ofMillis(300)).click().perform();

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[contains(@href,'configure')]")))).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h2")).getText(), "General");
    }

    @Test(dependsOnMethods = "testCreateNewListView")
    public void testRenameView() {
        final String renamedViewName = "Renamed View Name";

        HomePage homePage = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new GlobalViewPage(getDriver()))
                .clickEditView()
                .typeNewName(renamedViewName)
                .clickSubmit()
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
                .clickCreateButton(new MyViewPage(getDriver()))
                .goHomePage()
                .getViewsList();

        Assert.assertTrue(list.contains(VIEW_NAME));
    }

    @Test
    public void testRest() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[contains(@href,'newJob')]")).click();
        WebElement a = getDriver().findElement(By.xpath("//a[contains(@href,'api')]"));
        Thread.sleep(5000);
                a.click();
        Thread.sleep(5000);
    }

    @Test
    public void testCreateNewView3() {
        String newItemName = "Bob";

        String newItemNameActual = new HomePage(getDriver())
                .clickMyView()
                .clickNewItem()
                .typeItemName(newItemName)
                .selectItemFolder()
                .clickOk(new MultibranchPipelineConfigurationPage(getDriver()))
                .goHomePage()
                .getItemNameInTable();

        Assert.assertEquals(newItemNameActual, newItemName);
    }

    @Test
    public void testDeleteViewOnDashboard() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);

        boolean deleteView = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton(new MyViewPage(getDriver()))
                .clickDeleteView()
                .acceptAlert(new MyViewPage(getDriver()))
                .isViewExists(VIEW_NAME);

        Assert.assertFalse(deleteView);
    }

    @Test()
    public void testCreateNewEmptyView() {
        TestUtils.createFolder(this, JOB_NAME_2, true);

        boolean viewInBreadcrumbBar = new HomePage(getDriver())
                .clickMyView()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton(new NewViewPage(getDriver()))
                .isItemExistInBreadcrumbBar(VIEW_NAME);

        Assert.assertTrue(viewInBreadcrumbBar);
    }

    @Test
    public void testAddingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob(VIEW_NAME);

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(NEW_DESCRIPTION_FOR_THE_VIEW)
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description, NEW_DESCRIPTION_FOR_THE_VIEW);
    }

    @Test
    public void testEditingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob(VIEW_NAME);

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .clickAddOrEditDescription()
                .typeNewDescription(EDITED_DESCRIPTION_FOR_THE_VIEW)
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description, EDITED_DESCRIPTION_FOR_THE_VIEW);
    }

    @Test
    public void testDeletingDescriptionForTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob(VIEW_NAME);
        addNewDescriptionForTheView(VIEW_NAME, NEW_DESCRIPTION_FOR_THE_VIEW);

        String description = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .clickSaveDescription()
                .getDescription();

        Assert.assertEquals(description,"");
    }

    @Test
    public void testNoJobsShownForTheViewWithoutAssociatedJob() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob(VIEW_NAME);

        String mainPanelText = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .getMainPanelText();

        Assert.assertTrue(mainPanelText.contains(NO_ASSOCIATED_JOBS_FOR_THE_VIEW_MESSAGE));
    }

    @Test
    public void testProjectCouldBeAddedToTheView() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithoutAssociatedJob(VIEW_NAME);

        List<String> jobList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .clickEditView()
                .checkSelectedJobCheckbox(JOB_NAME)
                .clickOKButton()
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Test
    public void testAssociatedJobIsShownOnTheViewDashboard() {
        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithAssociatedJob(VIEW_NAME);

        List<String> jobList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .getJobList();

        Assert.assertTrue(jobList.contains(JOB_NAME));
    }

    @Test
    public void testAddingNewColumnToTheView() {
        final String newColumnName = "Git Branches";

        TestUtils.createFreestyleProject(this, JOB_NAME, true);
        createListViewWithAssociatedJob(VIEW_NAME);

        List<String> columnNamesList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
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
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
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
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
                .clickEditView()
                .moveColumnToFirstPosition(reorderedColumnName)
                .clickOKButton()
                .getColumnNamesList();


        Assert.assertTrue(columnNamesList.get(0).contains(reorderedColumnName));
    }

    @Test
    public void testCreateListViewWithoutJobs() {
        boolean isListViewCreated = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(JOB_NAME)
                .goHomePage()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .goHomePage()
                .getViewsList()
                .contains(VIEW_NAME);

        Assert.assertTrue(isListViewCreated);
    }

    @Test(dependsOnMethods = "testCreateListViewWithoutJobs")
    public void testRenameListView() {
        List<String> viewsList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME, new ListViewPage(getDriver()))
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
                .clickViewByName(VIEW_NAME_1, new ListViewPage(getDriver()))
                .getMainPanelText()
                .contains(NO_ASSOCIATED_JOBS_FOR_THE_VIEW_MESSAGE);

        Assert.assertTrue(noJobsMessage);

        List<String> jobList = new ListViewPage(getDriver())
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
                .clickViewByName(VIEW_NAME_1, new ListViewPage(getDriver()))
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
                .clickMyView()
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .clickIncludeGlobalViewTypeRadioBTN()
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .clickOKButton(new MyViewPage(getDriver()))
                .getMyViewName();

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
                .clickCreateButton(new NewViewConfigurePage(getDriver()))
                .clickCheckboxByTitle(JOB_NAME_3)
                .clickOKButton(new MyViewPage(getDriver()))
                .getActiveViewName();

        Assert.assertEquals(VIEW_NAME, expectedListViewName);
    }
}
