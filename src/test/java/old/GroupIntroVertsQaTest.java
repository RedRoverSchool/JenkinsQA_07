package old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class GroupIntroVertsQaTest extends BaseTest {

    /**
     * DmitryS. Тесты
     */
    // region DmitryS. Добавляю в данный блок тесты.
    @Test(description = "check Jenkins's version")
    public void testJenkinsVersion() {
        String jenkinsVersion = getDriver().findElement((By.xpath("//*[@type='button']"))).getText();
        Assert.assertEquals(jenkinsVersion, "Jenkins 2.414.2");
    }

    @Test(description = "check welcome header")
    public void testWelcomeHeader() {
        String welcomeHeader = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/h1")).getText();
        Assert.assertEquals(welcomeHeader, "Welcome to Jenkins!");
    }

    @Test(description = "check logout and start page")
    public void testLogoutButton() {
        getDriver().findElement(By.xpath("//*[@href = '/logout']")).click();
        String logoutButton = getDriver().findElement(By.xpath("//*/main/div/h1")).getText();
        Assert.assertEquals(logoutButton, "Sign in to Jenkins");
    }

    @Test(description = "Create new job")
    public void testNewFreeJob(){
        getDriver().findElement(By.xpath("//*[@href = 'newJob']")).click();
        WebElement setJobNames = getDriver().findElement(By.xpath("//input[@name = 'name']"));
        String nameJob = "My first job";
        setJobNames.sendKeys(nameJob);
        getDriver().findElement(By.xpath("//li[@class = 'hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).submit();
        getDriver().findElement(By.xpath("//div[@* = 'rowSetStart28']/div[1]/div/span/label")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).submit();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class = 'job-index-headline page-headline']")).getText(), "Project " + nameJob);
    }
    // endregion

    // region AkiMiraTest

    @Test(description = "Test of Jenkins Search field")
    public void testJenkinsSearchFieldPositiveAkiMira() {

        WebElement searchField = getDriver().findElement(By.id("search-box"));
        searchField.sendKeys("admin");
        searchField.sendKeys(Keys.ENTER);

        WebElement searchResults = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]"));
        Assert.assertTrue(searchResults.isDisplayed());
        String value = searchResults.getText();
        Assert.assertTrue(value.contains("admin"));
    }

    @Test(description = "New Freestyle project is created using valid name")
    public void testNewFreestyleProjectPositiveAkiMira() {

        WebElement newItem = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItem.click();

        WebElement itemNameField = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        itemNameField.sendKeys("Test");

        WebElement freestyleProjectField = getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"));
        freestyleProjectField.click();

        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]"));
        submitButton.click();

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Test Config [Jenkins]");

    }
    // endregion

    @Test
    public void testCreateNewJob() {
        String jobName = "TestJob";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'jenkins_branch_OrganizationFolder')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), jobName);
    }

    @Test
    public void testAddDescription() {
        String description = "Test description for jenkins";

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='description']")).getText().contains(description));
    }

    @Test
    public void testDeletePipeline(){
        String pipelineName = "Jenkins test pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'org_jenkinsci_plugins_workflow_job_WorkflowJob')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Pipeline']/parent::a")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertFalse(getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']")).getText().contains(pipelineName));
    }
}
