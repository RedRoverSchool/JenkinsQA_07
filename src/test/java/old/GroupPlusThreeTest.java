package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.testng.Assert.assertEquals;
@Ignore
public class GroupPlusThreeTest extends BaseTest {
    
    @DataProvider(name = "nameForJobs")
    public String[][] validCredentials() {
        return new String[][] {
                { "Akiko" }
        };
    }

    // Universal method for creating new different items
    private String createNewItemTemplate(String name, String radioClassName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className(radioClassName)).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        return getDriver().findElement(By.xpath("//h1")).getText();

    }

    @Test(description = "Test creating new Freestyle project", dataProvider = "nameForJobs")
    public void testNewFreestyleProject(String name) {

        String result = createNewItemTemplate(name, "hudson_model_FreeStyleProject");
        Assert.assertEquals(result, "Project " + name);

    }

    @Test(description = "Test creating new Pipeline", dataProvider = "nameForJobs")
    public void testNewPipeline(String name) {

        String result = createNewItemTemplate(name, "org_jenkinsci_plugins_workflow_job_WorkflowJob");
        Assert.assertEquals(result, "Pipeline " + name);

    }

    @Test(description = "Test creating new Multi-configuration project", dataProvider = "nameForJobs")
    public void testNewMultiConfigurationProject(String name) {

        String result = createNewItemTemplate(name, "hudson_matrix_MatrixProject");
        Assert.assertEquals(result, "Project " + name);

    }

    @Test(description = "Test creating new Folder", dataProvider = "nameForJobs")
    public void testNewFolder(String name) {

        String result = createNewItemTemplate(name, "com_cloudbees_hudson_plugins_folder_Folder");
        Assert.assertEquals(result, name);

    }

    @Test(description = "Test creating new Multibranch Pipeline", dataProvider = "nameForJobs")
    public void testNewMultiBranchPipeline(String name) {

        String result = createNewItemTemplate(name,
                "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        Assert.assertEquals(result, name);

    }

    @Test(description = "Test creating new Organization Folder", dataProvider = "nameForJobs")
    public void testNewOrganizationFolder(String name) {

        String result = createNewItemTemplate(name, "jenkins_branch_OrganizationFolder");
        Assert.assertEquals(result, name);

    }
    
    @Test (description = "Test creating new item")
    public void testNewItem () {
        
        final String itemName = "Akiko";
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//li[@class = 'hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();


        Assert.assertNotNull(getDriver().findElement(By.xpath("//a[@href = '/job/" + itemName + "/']")), "Ссылка на страницу созданного Item не была найдена");
        assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + itemName);

    }

    @Test (description = "Freestyle project create from New Item")
    public void testCreateJob() {
        String jobName = "Freestyle";

        getDriver().findElement(By.xpath("//*[@class='task '][1]")).click();

        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        String resultHeading = getDriver().findElement(By.xpath("//h1")).getText();

        assertEquals(resultHeading, "Project " + jobName);
    }

    private String universalCreateJobMethod(String name, String radioButtonClassName) {
        getDriver().findElement(By.xpath("//*[@class='task '][1]")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className(radioButtonClassName)).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    @Test (description = "Freestyle project create from New Item")
    public void testFreestyleProject() {
        String jobName = "Pipeline";

        String result = universalCreateJobMethod(jobName, "org_jenkinsci_plugins_workflow_job_WorkflowJob");

        assertEquals(result, "Pipeline " + jobName);
    }

    @Test (description = "Pipeline project create from New Item")
    public void testCreatePipeline() {
        String jobName = "Pipeline";

        String result = universalCreateJobMethod(jobName, "org_jenkinsci_plugins_workflow_job_WorkflowJob");

        assertEquals(result, "Pipeline " + jobName);
    }

    @Test (description = "Multi-configuration project project create from New Item")
    public void testMultiConfigurationProject() {
        String jobName = "Multi-configuration project";

        String result = universalCreateJobMethod(jobName, "hudson_matrix_MatrixProject");

        assertEquals(result, "Project " + jobName);
    }

    @Test (description = "Folder project create from New Item")
    public void testFolderProject() {
        String jobName = "Folder";

        String result = universalCreateJobMethod(jobName, "com_cloudbees_hudson_plugins_folder_Folder");

        assertEquals(result, jobName);
    }

    @Test (description = "Multibranch Pipeline project create from New Item")
    public void testMultibranchPipelineProject() {
        String jobName = "Multibranch Pipeline";

        String result = universalCreateJobMethod(jobName, "org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");

        assertEquals(result, jobName);
    }

    @Test (description = "Organization Folder project create from New Item")
    public void testOrganizationFolderProject() {
        String jobName = "Organization Folder";

        String result = universalCreateJobMethod(jobName, "jenkins_branch_OrganizationFolder");

        assertEquals(result, jobName);
    }

    @Test
    public void testSidePanel() {
        List <String> sidePanel = List.of(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views"
        );

        List <WebElement> elementsListSidePanel = getDriver()
                .findElements(By.cssSelector("#tasks .task"));

        List <String> getElementsListSidePanel = new ArrayList<>();

        for (WebElement task : elementsListSidePanel) {
            getElementsListSidePanel.add(task.getText());
        }

        Assert.assertEquals(sidePanel, getElementsListSidePanel);

    }

    @Test
    public void testCheckBuildHistory() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMM d, yyyy, h:mm aaa", Locale.ENGLISH);
        String date = formatForDateNow.format(dateNow);

        getDriver().findElement(By.className("content-block__link")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("Test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//*[@class=\"jenkins-button jenkins-button--primary \"]")).click();

        getDriver().findElement(By.cssSelector("a[href=\"/job/Test/build?delay=0sec\"]")).click();
        getDriver().navigate().refresh();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class =\"model-link inside build-link\"]")).getText(), date);
    }
}
