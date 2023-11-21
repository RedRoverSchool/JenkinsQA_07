package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewTest extends BaseTest {

    private static final String JOB_NAME = "FreestyleProject-1";
    private static final String JOB_NAME_1 = "FreestyleProject-2";
    private static final String JOB_NAME_2 = "FreestyleProject-3";
    private static final String VIEW_NAME = "ListView-1";
    private static final String VIEW_NAME_1 = "ListView-new";

    private void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewFreestyleProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(projectName)
                .goHomePage();
    }

    private void createMyNewListView(String viewName) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.cssSelector("a[title='New View']")).click();
        getDriver().findElement(By.cssSelector("input[name='name']")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.cssSelector("button[id='ok']")).click();
    }

    private void createListViewWithoutAssociatedJob(String newListViewName) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href = '/newView']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(newListViewName);
        getDriver().findElement(By.xpath("//label[@for = 'hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok']")).click();
    }

    private void createListViewWithAssociatedJob(String newListViewName) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href = '/newView']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(newListViewName);
        getDriver().findElement(By.xpath("//label[@for = 'hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok']")).click();
        getDriver().findElement(By.xpath("//div[@class = 'listview-jobs']/span/span[1]")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void addNewDescriptionForTheView(String listViewName, String newDescriptionForTheView) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + listViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(newDescriptionForTheView);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void associateJobToTheView(String listViewName, String jobName) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + listViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + listViewName + "/configure']")).click();
        getDriver().findElement(By.xpath("//label[@title = '" + jobName + "']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void createNewFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createNewMultibranchPipeline(String PipelineName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PipelineName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateNewView() {
        final String nameFreestyleProject = "My new Freestyle project";
        final String nameView = "Test view";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//a[@href='/user/admin/my-views/newView']")).click();
        getDriver().findElement(By.xpath("//input[@checkurl='/user/admin/my-views/viewExistsCheck']")).sendKeys(nameView);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/admin/my-views/view/Test%20view/']")).getText(), nameView);
    }

    @Test(dependsOnMethods = "testCreateNewListView")
    public void testRenameView() {
        final String renamedViewName = "Renamed View Name";

        HomePage homePage = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME)
                .clickEditView()
                .typeNewName(renamedViewName)
                .clickSubmit()
                .goHomePage();

        Assert.assertTrue(homePage.getViewsList().contains(renamedViewName));
    }

    @Test
    public void testCreateNewView2() {
        final String myProjectName = "My new freestyle project name";
        final String newViewName = "My new view name";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(myProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@tooltip = 'New View']")).click();

        getDriver().findElement(By.id("name")).sendKeys(newViewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id= 'projectstatus-tabBar']/div/div[1]/div[2]/a")).getText(),  newViewName);
    }

    @Test
    public void testCreateNewView3(){

        getDriver().findElement(By.xpath("//*[@id=\'tasks\']/div[5]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\'tasks\']/div[1]/span/a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("Bob");
        getDriver().findElement(By.xpath("//*[@id=\'j-add-item-type-nested-projects\']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\'ok-button\']")).click();
        getDriver().findElement(By.xpath("//*[@id=\'breadcrumbs\']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\'job_Bob\']/td[3]"));

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\'job_Bob\']/td[3]")).getText(),"Bob");

    }

    @Test
    public void testAddJobToTheView() {
        final String PROJECT_NAME = "Freestyle Project";
        final String VIEW_NAME = "View";

        createNewFreestyleProject(PROJECT_NAME);
        createMyNewListView(VIEW_NAME);
        goHome();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//div[@class='tab']/a[contains(text(), '" + VIEW_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'/configure')]")).click();
        getDriver().findElement(By.xpath(String.format("//label[@title='%s']", PROJECT_NAME))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String projectName = getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']")).getText();

        Assert.assertEquals(projectName, PROJECT_NAME);
    }

    @Test
    public void testEditView() {
        final String myProjectName = "My new freestyle project name";
        final String newViewName = "My new view name";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(myProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@tooltip = 'New View']")).click();

        getDriver().findElement(By.id("name")).sendKeys(newViewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//*[@id='projectstatus-tabBar']/div/div[1]/div[2]/a")).click();
        getDriver().findElement(By.xpath("//a[@data-post = 'true']")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        String checkDeletedViewName = getDriver().findElement(By.xpath("//*[@id='projectstatus-tabBar']/div/div[1]/div[2]/a")).getText();
        Assert.assertEquals(checkDeletedViewName,"");
    }

    @Test
    public void testDeleteViewOnDashboard() {

        createNewFreestyleProject("New View");
        createMyNewListView("NewView");

        getDriver().findElement(By.xpath("//span[text()='Delete View']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertFalse(getDriver().findElement(By.xpath("//div[@class='tabBar']"))
                .getText().contains("NewView"));
    }

    @Test
    public void testCreateNewFolder() {
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("TestFolder");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_TestFolder']/td[3]/a/span")).getText(),
                "TestFolder");
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testCreateNewEmptyView() {
        final String nameView = "My new view";

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//a[@title='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameView);
        getDriver().findElement(By.xpath("//input[@id='hudson.model.ListView']/following-sibling::label")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']"));
        String text = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar' and descendant::*[contains(text(), '" + nameView +"' )]]")).getText();

        Assert.assertTrue(text.contains(nameView));

    }

    @Test
    public void testAddingDescriptionForTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String newDescriptionForTheView = "Test description for the List View";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(newDescriptionForTheView);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                newDescriptionForTheView);
    }

    @Test
    public void testEditingDescriptionForTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String newDescriptionForTheView = "Test description for the List View";
        final String editedDescriptionForTheView = "New Test description for the List View instead of the previous one";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        addNewDescriptionForTheView(newListViewName, newDescriptionForTheView);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(editedDescriptionForTheView);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                editedDescriptionForTheView);
    }

    @Test
    public void testDeletingDescriptionForTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String newDescriptionForTheView = "Test description for the List View";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        addNewDescriptionForTheView(newListViewName, newDescriptionForTheView);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "");
    }

    @Test
    public void testNoJobsShownForTheViewWithoutAssociatedJob() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String noAssociatedJobsForTheViewMessage = "This view has no jobs associated with it. " +
                "You can either add some existing jobs to this view or create a new job in this view.";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//div[@id = 'main-panel']")).getText().
                        contains(noAssociatedJobsForTheViewMessage));
    }

    @Test
    public void testProjectCouldBeAddedToTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/configure']")).click();
        getDriver().findElement(By.xpath("//label[@title = '" + newFreeStyleProjectName + "']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).getText(),
                newFreeStyleProjectName);
    }

    @Test
    public void testAssociatedJobIsShownOnTheViewDashboard() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithoutAssociatedJob(newListViewName);
        associateJobToTheView(newListViewName, newFreeStyleProjectName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).getText(),
                newFreeStyleProjectName);
    }

    @Test
    public void testAddingNewColumnToTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String newColumnName = "Git Branches";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/configure']")).click();
        JavascriptExecutor scriptForScrolling = (JavascriptExecutor) getDriver();
        scriptForScrolling.executeScript("window.scrollBy(0,926)");
        getDriver().findElement(By.xpath("//button[@id = 'yui-gen3-button']")).click();

        List<WebElement> newColumnOptions = getDriver().findElements(By.xpath("//a[@class = 'yuimenuitemlabel']"));
        for (WebElement newColumnOption : newColumnOptions) {
            if (newColumnOption.getText().contains(newColumnName)) {
                newColumnOption.click();
            }
        }

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        List<WebElement> dashboardColumnNames = getDriver().findElements(By.xpath("//table[@id = 'projectstatus']//th"));

        Assert.assertEquals(
                dashboardColumnNames.get(dashboardColumnNames.size()-1).getText(),
                newColumnName);
    }

    @Test
    public void testDeletingColumnFromTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String deletedColumnName = "Last Duration";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/configure']")).click();
        JavascriptExecutor scriptForScrolling = (JavascriptExecutor) getDriver();
        scriptForScrolling.executeScript("window.scrollBy(0,926)");
        getDriver().findElement(By.xpath(
                "//div[contains(text(), '" + deletedColumnName + "')]/button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> dashboardColumnNamesAfterColumnDeletion = getDriver().findElements(By.xpath(
                "//table[@id = 'projectstatus']//th"));

        Assert.assertFalse(dashboardColumnNamesAfterColumnDeletion.contains(deletedColumnName));
    }

    @Test
    public void testReorderColumnsForTheView() {
        final String newFreeStyleProjectName = "FreeStyleTestProject";
        final String newListViewName = "ListViewTest";
        final String reorderedColumnName = "Name";

        createNewFreestyleProject(newFreeStyleProjectName);
        createListViewWithAssociatedJob(newListViewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/view/" + newListViewName + "/configure']")).click();
        JavascriptExecutor scriptForScrolling = (JavascriptExecutor) getDriver();
        scriptForScrolling.executeScript("window.scrollBy(0,926)");
        WebElement columnToReorder = getDriver().findElement(By.xpath(
                "//div[contains(text(), '" + reorderedColumnName + "')]/div[@class = 'dd-handle']"));
        WebElement placeForTheReorderedColumn = getDriver().findElement(By.xpath(
                "//div[@class = 'repeated-chunk__header'][1]"));
        Actions actions = new Actions(getDriver());
        actions.clickAndHold(columnToReorder)
                .moveToElement(placeForTheReorderedColumn)
                .release(placeForTheReorderedColumn)
                .build()
                .perform();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> dashboardColumnNamesAfterColumnReorder = getDriver().findElements(By.xpath(
                "//table[@id = 'projectstatus']//th"));

        Assert.assertTrue(dashboardColumnNamesAfterColumnReorder.get(0).getText().contains(reorderedColumnName));
    }

    @Test
    public void testCreateNewListView_WithoutJobs() {
        final String jobName = "FreestyleProject-1";
        final String newViewName = "ListView-1";

        createNewFreestyleProject(jobName);
        goHome();

        List<String> viewsNamesList = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(newViewName)
                .selectListViewType()
                .clickCreateButton()
                .goHomePage()
                .getViewsList();

        Assert.assertTrue(viewsNamesList.contains(newViewName));
    }

    @Test(dependsOnMethods = "testCreateNewListView_WithoutJobs")
    public void testRenameListView() {
        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME + "/']"))
                .click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + VIEW_NAME + "/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).clear();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(VIEW_NAME_1);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'tab active']/a"))
                        .getText(),
                VIEW_NAME_1);
    }

    @Test(dependsOnMethods = "testRenameListView")
    public void testAddJobToListViewWithoutJobs_fromMainSectionLink() {
        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME_1 + "/']"))
                .click();

        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText()
                .contains("This view has no jobs associated with it. You can either add some existing jobs to this " +
                        "view or create a new job in this view."));

        getDriver().findElement(By.xpath("//a[@href='configure']")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");
        getDriver().findElement(By.xpath("//label[@title='" + JOB_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]"))
                        .getText(),
                JOB_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testAddJobToListViewWithoutJobs_fromMainSectionLink")
    public void testAddAllJobsToView() {
        createNewFreestyleProject(JOB_NAME_1);
        goHome();
        createNewFreestyleProject(JOB_NAME_2);
        goHome();

        getDriver().findElement(By.xpath("//div[@class='tabBar']/div/a[@href='/view/" + VIEW_NAME_1 + "/']"))
                .click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + VIEW_NAME_1 + "/configure']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,600)");

        getDriver().findElement(By.xpath("//label[@title='" + JOB_NAME + "']")).click();
        List<WebElement> listOfJobs = getDriver().findElements(
                By.xpath("//div[@class='listview-jobs']/span/span/label"));
        for (WebElement job : listOfJobs) {
            job.click();
        }
        getDriver().findElement(By.name("Submit")).click();

        List<String> jobNames = List.of(JOB_NAME, JOB_NAME_1, JOB_NAME_2);
        List<WebElement> dashboardItems = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr/td"));
        List<String> jobNamesDashboard = new ArrayList<>();
        for (WebElement item : dashboardItems) {
            if (item.getText().contains("FreestyleProject")) {
                jobNamesDashboard.add(item.getText());
            }
        }

        Assert.assertEquals(jobNamesDashboard.size(), jobNames.size());
        for (int i = 0; i < jobNamesDashboard.size(); i++) {
            Assert.assertEquals(jobNames.get(i), jobNamesDashboard.get(i));
        }
    }

    @Test
    public void testCreateViewWithOptionGlobalView() {

        final String VIEW_NAME = UUID.randomUUID().toString();

        createNewFreestyleProject(UUID.randomUUID().toString());

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.name("name")).sendKeys(VIEW_NAME);

        getDriver().findElement(By.xpath("//div/label[@for='hudson.model.ProxyView']")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/ol/li/a[@href='/user/admin/my-views/view/"+VIEW_NAME+"/']")).getText(), VIEW_NAME);

    }

    @Test
    public void testCreateNewListView() {
        final String multibranchPipelineName = "Multibranch Pipeline Name";
        final String folderName = "New Folder Name";

        createNewFolder(folderName);
        goHome();
        createNewMultibranchPipeline(multibranchPipelineName);
        goHome();
        String expectedListViewName = new HomePage(getDriver())
                .clickNewViewButton()
                .typeNewViewName(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .clickCheckboxByTitle(multibranchPipelineName)
                .clickOKButton()
                .getActiveViewName();

        Assert.assertEquals(VIEW_NAME, expectedListViewName);
    }

}
