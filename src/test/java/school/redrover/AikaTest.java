package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class AikaTest extends BaseTest {

    @Test
    public void testAdminUserDisplayed() {
        getDriver().findElement(By.xpath("//span[text()='People']/parent::a")).click();
        Assert.assertTrue(getDriver().findElement(By.linkText("admin")).isDisplayed());
    }

    @Test
    public void testVerifyJenkinsVersion() {
        String expectedJenkinsVersion = "2.414.2";

        String jenkinsVersion = getDriver().findElement(By.xpath("//button[contains(text(), 'Jenkins')]")).getText();
        Assert.assertEquals(jenkinsVersion.split(" ")[1], expectedJenkinsVersion);
    }

    @Test
    public void testClickingOnDashboardOpensDashboard() {

        WebElement newItem = getDriver().findElement(By.xpath("//div[@id='tasks']//a[1]"));
        newItem.click();

        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }

    @Test
    public void testCreateNewFreestyleProject() {
        final String PROJECTNAME = "FreestyleProject v1";
        createFreestyleProject(PROJECTNAME);

        String actualProjectName = getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1"))
                .getText();

        Assert.assertEquals(actualProjectName, "Project " + PROJECTNAME);

    }

    @Test
    public void testCreateNewPipeline() {
        final String PIPELINENAME = "Pipeline v1";
        createPipeline(PIPELINENAME);

        String actualPipelineName = getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1"))
                .getText();

        Assert.assertEquals(actualPipelineName, "Pipeline "+PIPELINENAME);

    }
    @Test
    public void testRenameExistingProject() {
        final String renamedProject = "Renamed Project";
        final String freestyleName = "Freestyle";
        final String pipelineName = "Pipeline";

        createFreestyleProject(freestyleName);
        goToDashboard();
        createPipeline(pipelineName);
        goToDashboard();

        renameProject(freestyleName, renamedProject);

        String newName = getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1"))
                .getText();

        Assert.assertEquals(newName.substring(newName.indexOf(' ')+1), renamedProject);

    }

    @Test
    public void testProjectDisable() {
        final String freestyleName = "Freestyle v2";
        final String pipelineName = "Pipeline v2";

        createFreestyleProject(freestyleName);
        goToDashboard();
        createPipeline(pipelineName);
        goToDashboard();

        disableProject(freestyleName);

        String warningMsg = getDriver().findElement(By.id("enable-project")).getAttribute("innerText");
        Assert.assertEquals(warningMsg.substring(0, warningMsg.lastIndexOf(' ')),
                "This project is currently disabled" );

    }

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text() = 'Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Freestyle project description");
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createPipeline(String pipelineName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Pipeline description");
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToDashboard() {
        getDriver().findElement(By.xpath("//a[@href='/']")).click();
    }

    private void renameProject(String projectName, String renameString) {
        clickOnProjectFromDashboard(projectName);

        getDriver().findElement(By.xpath("//a[@href ='/job/Freestyle/confirm-rename']")).click();

        WebElement inputForRename = getDriver().findElement(By.xpath("//input[@name = 'newName']"));
        inputForRename.clear();
        inputForRename.sendKeys(renameString);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void disableProject(String projectName) {
        clickOnProjectFromDashboard(projectName);

        getDriver().findElement(By.xpath("//form[@id='disable-project']/button")).click();

    }

    private void clickOnProjectFromDashboard(String projectName) {
        getDriver().findElement(
                By.xpath("//table[@id = 'projectstatus']//span[text() = '"+projectName+"']")).click();
    }
}
