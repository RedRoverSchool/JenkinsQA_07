package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;

public class GroupPlusThreeTest extends BaseTest {
    
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

    public String universalCreateJobMethod(String name, String radioButtonClassName) {
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
}
