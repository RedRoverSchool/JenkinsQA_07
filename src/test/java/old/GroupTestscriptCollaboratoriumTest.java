package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
@Ignore
public class GroupTestscriptCollaboratoriumTest extends BaseTest {

    private void utilsGetJenkinsHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
    }

    private void utilsCreateFreestyleProject(String projectName) {

        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[contains(@class, 'jenkins-input')]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//button[contains(@id, 'ok-button')]")).click();
    }


    private void utilsDeleteProjectByItsName(String projectName) {

        utilsGetJenkinsHomePage();

        getDriver().findElement(By.xpath("//div[contains(@class, 'dashboard')]"))
                .findElement(By.xpath(String.format("//a[contains(@href, 'job/%s/')]", projectName)))
                .click();
        getDriver().findElement(By.xpath(String.format("//a[@data-url = '/job/%s/doDelete']", projectName))).click();
        getDriver().switchTo().alert().accept();
    }

    private void utilsCreateFolder(String folderName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//img[@alt = 'Jenkins']")).click();
    }

    @Test
    public void testVersion() {

        getDriver().findElement(By.xpath("//*[@id = 'jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        WebElement version = getDriver().findElement(By.xpath("//p[@class = 'app-about-version']"));

        Assert.assertEquals(version.getText(), "Version 2.414.2");
    }

    @Test
    public void testCreateNewPipelineProject() {

        final String projectName = "TestNew";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Pipeline %s", projectName));
    }

    @Test
    public void testJenkinsGetVersionInAboutMenu() {

        getDriver().findElement(By.className("page-footer__links")).findElement(By.tagName("button")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("app-about-version")).getText(),
                "Version 2.414.2");
    }

    @Test
    public void testJenkinsFreestyleProjectIsCreatedCheckByH1() {

        final String expectedProjectName = "testJenkinsFreestyleProjectIsCreatedCheckByH1()";

        utilsCreateFreestyleProject(expectedProjectName);
        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();

        String actualProjectName = getDriver().findElement(By
                .xpath("//h1[@class = 'job-index-headline page-headline']")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", expectedProjectName));

        utilsDeleteProjectByItsName(expectedProjectName);
    }

    @Test
    public void testJenkinsFreestyleProjectIsCreatedCheckByBreadcrumb() {

        final String expectedProjectName = "testJenkinsFreestyleProjectIsCreatedCheckByBreadcrumb()";

        utilsCreateFreestyleProject(expectedProjectName);

        String actualProjectName = getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, 'job/%s/')]", expectedProjectName))).getText();

        Assert.assertEquals(actualProjectName, expectedProjectName);

        utilsDeleteProjectByItsName(actualProjectName);
    }

    @Test
    public void testJenkinsNewProjectIsDisplayedOnDashboard() {

        final String expectedProjectName = "testJenkinsNewProjectIsDisplayedOnDashboard()";

        utilsCreateFreestyleProject(expectedProjectName);

        utilsGetJenkinsHomePage();

        String actualProjectName = getDriver().findElement(By.xpath("//div[contains(@class, 'dashboard')]"))
                .findElement(By.xpath(String.format("//a[contains(@href, 'job/%s/')]/span", expectedProjectName)))
                .getText();

        Assert.assertEquals(actualProjectName, expectedProjectName);

        utilsDeleteProjectByItsName(actualProjectName);
    }

    @Test
    public void testDeleteFolder() {

        utilsCreateFolder("Folder1");

        getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/Folder1/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.xpath("//input[@role = 'searchbox']")).sendKeys("Folder1" + "\n");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@class = 'error']")).getText(), "Nothing seems to match.");
    }
}
