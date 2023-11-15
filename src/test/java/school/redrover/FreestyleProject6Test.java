package school.redrover;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject6Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject5";
    private final static String DESCRIPTION_NAME = "Here are the project description!";
    private final static String EDITED_DESCRIPTION_NAME = "Here is the edited project description!";
    private final static String PROJECT_NAME1 = "Starlight";
    private final static String FOLDER_NAME1 = "Folder";

    private void createProject(String PROJECT_NAME) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
    }
    private void addDescription() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
    }

    @Test
    public void testCreate() {
        String projectName = PROJECT_NAME;

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testAddDescription() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div[id='description'] div:nth-child(1)")).getText(),
                DESCRIPTION_NAME);
    }

    @Test
    public void testEditDescription() {
        addDescription();

        getDriver().findElement(By.xpath("//td/a[@href='job/FreestyleProject5/']")).click();
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(EDITED_DESCRIPTION_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div[id='description'] div:nth-child(1)")).getText(),
                EDITED_DESCRIPTION_NAME);
    }

    @Test
    public void testDeleteProject() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside'] span")).click();
        getDriver().findElement(By.xpath("//a[@class='task-link  confirmation-link']")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div[class='empty-state-block'] h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testDisableProjectMessage() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector(".jenkins-table__link.model-link.inside")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        boolean isMessageVisible = getDriver().findElement(By.className("warning")).isDisplayed();

        Assert.assertTrue(isMessageVisible, "The warning message is not visible.");
    }


    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[starts-with(@href,'/view/all/newJob') and contains (@class,'task-link')]")).click();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[starts-with(@href,'/view/all/newJob') and contains (@class,'task-link')]")).click();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[contains(@class,'com_cloud')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test(dependsOnMethods = "testMoveProjectToFolder")
    public void testDeleteFreestyleProject() {
        createFreestyleProject(PROJECT_NAME1);
        getDriver().findElement(By.xpath("//a[@class='task-link  confirmation-link']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElements(By.id("job_" + PROJECT_NAME1)).size(), 0);
    }

    @Test
    public void testDiscardOldBuildsCheckbox() {
        final String configureLink = "//a[@href='/job/" + PROJECT_NAME1 + "/configure']";
        final String daysToKeepBuildsField = "//input[@name = '_.daysToKeepStr']";
        final String daysToKeepBuildsFieldValue = "5";
        final String maxOfBuildsToKeepField = "//input[@name = '_.numToKeepStr']";
        final String maxOfBuildsToKeepFieldValue = "7";

        createFreestyleProject(PROJECT_NAME1);
        getDriver().findElement(By.xpath(configureLink)).click();
        getDriver().findElement(By.xpath("//label[normalize-space()='Discard old builds']")).click();
        getDriver().findElement(By.xpath(daysToKeepBuildsField)).sendKeys(daysToKeepBuildsFieldValue);
        getDriver().findElement(By.xpath(maxOfBuildsToKeepField)).sendKeys(maxOfBuildsToKeepFieldValue);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath(configureLink)).click();

        Assert.assertTrue(getDriver().findElement(By.id("cb4")).isSelected(), "Checkbox is not click");
        Assert.assertEquals(getDriver().findElement(By.xpath(daysToKeepBuildsField)).getAttribute("value"),daysToKeepBuildsFieldValue);
        Assert.assertEquals(getDriver().findElement(By.xpath(maxOfBuildsToKeepField)).getAttribute("value"),maxOfBuildsToKeepFieldValue);
    }

    @Test
    public void testMoveProjectToFolder() {
        createFolder(FOLDER_NAME1);
        getDriver().findElement(By.id("jenkins-home-link")).click();

        createFreestyleProject(PROJECT_NAME1);
        getDriver().findElement(By.xpath("//a[@href='/job/" + PROJECT_NAME1 + "/move']")).click();
        Select select = new Select(getDriver().findElement(By.xpath("//select[@name = 'destination']")));
        select.selectByValue("/" + FOLDER_NAME1);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME1 + "/']"));
        actions.moveToElement(element).moveByOffset(0, 0).click().build().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),FOLDER_NAME1);
        Assert.assertTrue(getDriver().findElement(By.id("job_" + PROJECT_NAME1)).isDisplayed());
    }

        @Test
    public void testDescriptionPreviewDisplayed() {
        final String description = "Project description preview test!";

        createFreestyleProject(PROJECT_NAME1);
        getDriver().findElement(By.xpath("//a[@href='/job/" + PROJECT_NAME1 + "/configure']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div//div[@class='textarea-preview']")).getText(),description);
    }
}