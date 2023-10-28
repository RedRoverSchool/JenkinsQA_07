package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class ASCreateNewItemTest extends BaseTest {

    private static final String itemName = "AS Test File";
    private static final String encodedItemName = itemName.replace(" ", "%20");
    private static final String expectedMessage = "» This field cannot be empty, please enter a valid name";
    private static final String validationMessage = "Validation message does not match expected text";
    private static final String notFoundMessage = "Item does not exist";
    private static final String createdItem = "a[href='job/" + encodedItemName + "/'] span";

    private void Submit(){
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void InputName(){
        getDriver().findElement(By.id("name")).sendKeys(itemName);
    }


    @Test
    public void testCreateFreeStyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

    @Test
    public void testCreateMultiConfigurationProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

    @Test
    public void testCreateFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

    @Test
    public void testCreateMultiBranchPipeline() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

    @Test
    public void testCreateOrganizationFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        org.testng.Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), expectedMessage, validationMessage);

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        Assert.assertNotNull(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "OK button is not disabled");

        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        InputName();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        Submit();
        getDriver().findElement(By.cssSelector("a.model-link[href='/']")).click();
        Assert.assertNotNull(getDriver().findElement(By.cssSelector(createdItem)), notFoundMessage);

    }

}