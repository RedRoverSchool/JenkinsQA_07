package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class GroupCarlTheFogTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String NEW_PROJECT_NAME = "newFreestyleProject";
    private static final String PIPELINE_NAME = "Pipeline";
    private static final String PIPELINE_DESCRIPTION = "Pipeline Description";

    private void createNewFreestyleProject(String projectName) {
        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.cssSelector("input[type='text']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
    }

    public void createNewPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[@class='label' and text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

    }

    @Test
    public void testJenkinsGreetings() {
        String JenkinsGreetings = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals("Welcome to Jenkins!", JenkinsGreetings);
    }

    @Test
    public void testCreateNewFreestyleProject() {
        createNewFreestyleProject(PROJECT_NAME);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1.job-index-headline")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testRenameFreestyleProject() {
        createNewFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[href*='rename']")).click();
        getDriver().findElement(By.cssSelector("input[name=newName]")).clear();
        getDriver().findElement(By.cssSelector("input[name=newName]")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1.job-index-headline")).getText(),
                "Project " + NEW_PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProjectFromSideMenu() {
        createNewFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[data-message*=Delete]")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div.empty-state-block h1")).getText(),
                "Welcome to Jenkins!");
    }
    @Test
    public void testJenkinsTitle() {
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Dashboard [Jenkins]");
    }

    @Test
    public void testCreateNewPipeline() {
        createNewPipeline(PIPELINE_NAME);
        String createdPipelineName = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(createdPipelineName, String.format("Pipeline %s", PIPELINE_NAME));

    }

    @Test
    public void testAddDescriptionPipeline() {
        createNewPipeline(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//div[@id = 'description']//button[@name='Submit']")).click();
        String savedDescription = getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText();

        Assert.assertEquals(savedDescription, PIPELINE_DESCRIPTION);
    }
}
