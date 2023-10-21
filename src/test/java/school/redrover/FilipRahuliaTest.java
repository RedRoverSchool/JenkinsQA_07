package school.redrover;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


enum ItemNames {
    FREESTYLE_PROJECT, PIPELINE, MULTI_CONFIGURATION_PROJECT, FOLDER, MULTI_BRANCH_PIPELINE, ORGANIZATION_FOLDER
}

public class FilipRahuliaTest extends BaseTest {
    public  final String testProjectName = "Test";


    private static String itemNameXpath(ItemNames itemName) {
        String xpath = "";
        switch (itemName) {
            case FREESTYLE_PROJECT -> xpath = "//li[@class='hudson_model_FreeStyleProject']";
            case PIPELINE -> xpath = "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']";
            case MULTI_CONFIGURATION_PROJECT -> xpath = "//li[@class='hudson_matrix_MatrixProject']";
            case FOLDER -> xpath = "//li[@class='com_cloudbees_hudson_plugins_folder_Folder']";
            case MULTI_BRANCH_PIPELINE -> xpath = "//li[@class='org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject']";
            case ORGANIZATION_FOLDER -> xpath = "//li[@class='jenkins_branch_OrganizationFolder']";
        }
        return xpath;
    }

    public void createNewItem (ItemNames itemName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(testProjectName);
        getDriver().findElement(By.xpath(itemNameXpath(itemName))).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit"));

    }

    @Test
    public void  testFreestyleProjectCreation () {

        createNewItem(ItemNames.FREESTYLE_PROJECT);

        getDriver().findElement(By.xpath("//ol[@id = 'breadcrumbs'] //a[.='Dashboard']")).click();

        String actualNameFromDashboardList = getDriver().findElement(By.xpath
                ("//tr[@id = 'job_" +testProjectName + "']//td[3]")).getText();
        Assert.assertEquals(actualNameFromDashboardList, testProjectName);


    }
    @Test
    public void  testPipelineCreation () {

        createNewItem(ItemNames.PIPELINE);
        getDriver().findElement(By.xpath("//ol[@id = 'breadcrumbs'] //a[.='Dashboard']")).click();

        String actualNameFromDashboardList = getDriver().findElement(By.xpath
            ("//tr[@id = 'job_" +testProjectName + "']//td[3]")).getText();
        Assert.assertEquals(actualNameFromDashboardList, testProjectName);

    }

    @Test
    public void removeItemFromDashboard () {

        createNewItem(ItemNames.PIPELINE);
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement hiddenMenu = getDriver().findElement(By.xpath
                ("//tr[@id='job_" + testProjectName + "'] " +
                        "//button [@class = 'jenkins-menu-dropdown-chevron']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(hiddenMenu).build().perform();
        hiddenMenu.click();

        getDriver().findElement(By.xpath("//button[@href='/job/" + testProjectName + "/doDelete']"))
                .click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        WebElement emptyBlock = getDriver().findElement(By.xpath("//div[@class = 'empty-state-block']"));
        Assert.assertTrue(emptyBlock.isDisplayed());
    }
}