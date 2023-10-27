package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.testng.Assert.*;


public class FreestyleProjectTest extends BaseTest {

    private final String PROJECT_NAME = "New Freestyle Project";

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private boolean isProjectExist(String projectName) {
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    private boolean isProjectEnabledOnDashBoard(String projectName) {
        if (!isProjectExist(projectName)) return false;

        return !getDriver()
                .findElement(By.id("job_" + projectName))
                .findElement(By.className("svg-icon"))
                .getAttribute("title").equals("Disabled");
    }

    private boolean isProjectEnabledOnProjectStatusPage(String projectName) {
        if (!isProjectExist(projectName)) return false;

        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();

        return !getDriver()
                .findElement(By.xpath("//div[@class='warning']"))
                .getText()
                .contains("This project is currently disabled");
    }

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void addDescriptionInConfiguration(String text) {
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(text);
        getDriver().findElement(By.xpath("//button[@name ='Submit']")).click();
    }

    private void changeDescriptionTextInStatus(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(text);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();
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
        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL + "a");
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

        getDriver().findElement(By.xpath("//td/a[@href= 'job/" + projectName + "/']")).click();

        getDriver().findElement(By.cssSelector("#description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name ='description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        assertTrue(getDriver().findElement(By.xpath("//div[contains(text(), description)]")).isDisplayed());
        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), description);
    }

    @Test
    public void testEditDescription() {
        String projectName = "Hello";
        String descriptionText = "Project freestyle";
        String descriptionEditText = "Welcome";

        createFreeStyleProject(projectName);

        addDescriptionInConfiguration(descriptionText);

        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//td/a[@href= 'job/" + projectName + "/']")).click();

        changeDescriptionTextInStatus(descriptionEditText);

        assertTrue(getDriver().findElement(By.xpath("//div[contains(text(), descriptionAfterEdit)]")).isDisplayed());
        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), descriptionEditText);

    }

    @Test
    public void testDeleteTheExistingDescription() {
        String projectName = "Hello";
        String descriptionText = "Project freestyle";

        createFreeStyleProject(projectName);

        addDescriptionInConfiguration(descriptionText);

        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//td/a[@href= 'job/" + projectName + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "");
    }

    @Test
    public void testTooltipDiscardOldBuildsIsVisible() {
        createFreeStyleProject("New Freestyle Project");
        WebElement helpButton = getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"));

        boolean tioltopIsVisible = true;
        new Actions(getDriver())
                .moveToElement(helpButton)
                .perform();

        if (helpButton.getAttribute("title").equals("Help for feature: Discard old builds")) {
            tioltopIsVisible = false;
        }

        Assert.assertTrue(tioltopIsVisible, "The tooltip is not displayed.");
    }

    @Test
    public void testDisableProjectFromStatusPage() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();
        getDriver().findElement(By.name("Submit")).click();
        goToJenkinsHomePage();

        assertFalse(isProjectEnabledOnDashBoard(projectName));
        assertFalse(isProjectEnabledOnProjectStatusPage(projectName));
    }

    @DataProvider(name = "ValidName")
    public String[][] validCredentials() {
        return new String[][]{
                {"\'Акико\'"}, {"Ак,ко"}, {"Акико"}, {"Akiko"}, {"12345`67890"}
        };
    }

    @Test(description = "Creating new Freestyle project using valid data", dataProvider = "ValidName")
    public void testFreestyleProjectWithValidData(String name) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String result = getDriver().findElement(By.xpath("//*[@id =\"job_" + name + "\"]/td[3]/a/span")).getText();
        Assert.assertEquals(result, name);

    }

    @DataProvider(name = "InvalidName")
    public String[][] invalidCredentials() {
        return new String[][]{
                {"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {"/"},
                {"["}
        };
    }

    @Test(description = "Creating new Freestyle project using invalid data", dataProvider = "InvalidName")
    public void testFreestyleProjectWithInvalidData(String name) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);

        String textRessult = getDriver().findElement(By.id("itemname-invalid")).getText();
        WebElement buttonOK = getDriver().findElement(By.id("ok-button"));

        Assert.assertEquals(textRessult, "» ‘" + name + "’ is an unsafe character");
        Assert.assertFalse(buttonOK.isEnabled());

    }

    @Test(description = "Creating Freestyle project using an empty name")
    public void testFreestyleProjectWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String textResult = getDriver().findElement(By.id("itemname-required")).getText();
        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));

        Assert.assertEquals(textResult, "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(buttonOk.isEnabled());
    }

    @Test(description = "Creating Freestyle project using duplicative name")
    public void testFreestyleProjectWithDublicativeName() {

        final String name = "Akiko";

        createFreeStyleProject(name);

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);

        String textResult = getDriver().findElement(By.id("itemname-invalid")).getText();
        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));

        Assert.assertEquals(textResult, "» A job already exists with the name ‘" + name + "’");
        Assert.assertFalse(buttonOk.isEnabled());

    }

    @Test
    public void testCreateFreestyleProject() {
        final String projectName = "FreestyleProjectNameRandom";

        getDriver().findElement(By.cssSelector("a[href = '/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(projectName);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("button[type = 'submit']")).click();

        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("li[class = 'jenkins-breadcrumbs__list-item']")).click();

        Assert.assertEquals(projectName,
                getDriver().findElement(By.cssSelector("a[href = 'job/FreestyleProjectNameRandom/']")).getText());
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        final String projectName = "New Freestyle Project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//span[text()='" + projectName + "']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("h1")).getText(),
                "Project " + projectName);
    }


    @Test
    public void testRenameToEmptyName() {

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item'][2]")).click();
        getDriver().findElement(By.xpath(
                "//a[@class='task-link ' and contains(@href, 'confirm-rename')]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No name is specified");
    }

    @Test
    public void testDisable() {
        createFreeStyleProject("FSProject");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='enable']/button")).isEnabled());
    }

    @Test
    public void testEnable() {
        createFreeStyleProject("FSProject");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();
        getDriver().findElement(By.xpath("//form[@action='enable']/button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='disable']")).isEnabled());
    }
}
