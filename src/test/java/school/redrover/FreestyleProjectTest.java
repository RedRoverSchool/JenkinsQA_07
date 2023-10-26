package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.testng.Assert.*;


public class FreestyleProjectTest extends BaseTest {

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private boolean isProjectExist(String projectName) {
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private List<String> getAllProjectsNames() {
        return getDriver()
                .findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Test
    public void testCreate() {
        final String projectName = "FreeStyleProjectName";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + projectName + "/']")).click();

        assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + projectName);
    }

    @Test
    public void testNewProjectCreatedOlena() {
        String randomName = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        WebElement newItem = getDriver().findElement(By.linkText("New Item"));
        newItem.click();

        WebElement projectNameField = getDriver().findElement(By.id("name"));
        projectNameField.click();
        projectNameField.sendKeys(randomName);

        WebElement selectProjectType = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        selectProjectType.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        getDriver().findElement(By.linkText("Dashboard")).click();
        WebElement projectName = getDriver().findElement(By.xpath("//td[3]/a"));
        String actualProjectName = projectName.getText();
        assertEquals(actualProjectName, randomName);
    }

    @Test
    public void testDeleteProject() {
        final String projectName = "Test Project";
        int initialProjectsAmount = getAllProjectsNames().size();
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@data-message,'Delete')]")).click();
        getDriver().switchTo().alert().accept();
        goToJenkinsHomePage();

        int resultingProjectsAmount = getAllProjectsNames().size();
        assertEquals(initialProjectsAmount, resultingProjectsAmount);
        assertFalse(isProjectExist(projectName));
    }

    @Test
    public void testRenameProject() {
        final String initialProjectName = "Test Project";
        final String newProjectName = "New Test Project";
        createFreeStyleProject(initialProjectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + initialProjectName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL+"a");
        getDriver().findElement(By.name("newName")).sendKeys(newProjectName);
        getDriver().findElement(By.name("Submit")).click();
        goToJenkinsHomePage();

        assertTrue(isProjectExist(newProjectName));
        assertFalse(isProjectExist(initialProjectName));
    }

    @Test
    public void testAddDescriptionFreestyleProject() {
        final String projectName = "FreestyleProject";
        final String descriptionText = "Description";

        createFreeStyleProject(projectName);

        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(descriptionText);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                descriptionText);
    }

    @Test
    public void testCreateNew() {
        final String projectName = "New Test Project1";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();

        assertEquals(
                getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(),
                projectName);
    }

    @Test
    public void testAddDescription() {
        String projectName = "Hello";
        String description = "My description";
        createFreeStyleProject(projectName);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href= 'job/"+ projectName +"/']")).click();

        getDriver().findElement(By.cssSelector("#description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name ='description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        assertTrue(getDriver().findElement(By.xpath("//div[contains(text(), description)]")).isDisplayed());
        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), description);

    }

    @Test
    public  void testTooltipDiscardOldBuildsIsVisible() {
        createFreeStyleProject("New Freestyle Project");
        WebElement helpButton = getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"));

       boolean tioltopIsVisible = true;
        new Actions(getDriver())
                .moveToElement(helpButton)
                .perform();

       if(helpButton.getAttribute("title").equals("Help for feature: Discard old builds")) {
           tioltopIsVisible = false;
       }

       Assert.assertTrue(tioltopIsVisible, "The tooltip is not displayed.");



    }
}
