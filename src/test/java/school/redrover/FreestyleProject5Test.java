package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreestyleProject5Test extends BaseTest {
    private final String PROJECT_NAME = "Freestyle Project";
    final static String NAME_TEST = "Test";
    final static String NEW_NAME_TEST = "newTest";

    private void createNewProject(String name){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRenameProject() throws InterruptedException {
        createNewProject("Test");

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NAME_TEST + "/']/span")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + NAME_TEST + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(NEW_NAME_TEST);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//h1")).getText().equals("Project " + NEW_NAME_TEST)
        );
    }

    @Test()
    public void testCreateFreeStyleProject() {
        int desiredLength = 5;

        String testFreeStyleProjectName = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);

        WebElement addNewProjectButton = getDriver().findElement(By.xpath("//span[@class='task-icon-link']"));
        addNewProjectButton.click();
        WebElement jenkinsJobNameField = getDriver().findElement(By.xpath("//*[@class='jenkins-input']"));
        jenkinsJobNameField.sendKeys(testFreeStyleProjectName);
        WebElement freeStyleProject = getDriver().findElement(By.xpath("//*[text()='Freestyle project']"));
        freeStyleProject.click();
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();
        String jenkinsJobName = getDriver().findElement(By.xpath("//*[@class='job-index-headline page-headline']")).getText();

        Assert.assertTrue(jenkinsJobName.contains(testFreeStyleProjectName));
    }

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("FreeStyleProjectName");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/FreeStyleProjectName/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel >h1")).getText(),
                "Project FreeStyleProjectName");
    }

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test

    public void testCreateFreestyleProjectFromNewItem() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input#name.jenkins-input")).sendKeys("FreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement freestyleProject = getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']"));

        Assert.assertTrue(freestyleProject.isDisplayed());
    }

    @Test
    public void testPermalinksListOnStatusPage() {

        final String[] buildSuccessfulPermalinks = {"Last build", "Last stable build", "Last successful build",
                "Last completed build"};

        createFreeStyleProject(PROJECT_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Build Now"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='build-row-cell']")));

        getDriver().navigate().refresh();

        List<WebElement> permalinks = getDriver().findElements(
                By.xpath("//ul[@class='permalinks-list']/li"));

        ArrayList<String> permalinksTexts = new ArrayList<>();

        Assert.assertEquals(permalinks.size(), 4);

        for (int i = 0; i < permalinks.size(); i++) {
            permalinksTexts.add(permalinks.get(i).getText());
            Assert.assertTrue((permalinksTexts.get(i)).contains(buildSuccessfulPermalinks[i]));
        }

    }
    private void createProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Dead project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
    @Test
    public void deleteProject() {
        final String projectName1 = "Dead project";
        createProject();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//span[text()='" + projectName1 + "']")).click();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.linkText("Dashboard")).click();

        boolean deleted = isElementDeleted(By.xpath("//span[text()='" + projectName1 + "']"));
        assert (deleted);
    }
    private boolean isElementDeleted(By locator) {
        List<WebElement> elements = getDriver().findElements(locator);
        return elements.isEmpty() || !elements.get(0).isDisplayed();
    }
}

