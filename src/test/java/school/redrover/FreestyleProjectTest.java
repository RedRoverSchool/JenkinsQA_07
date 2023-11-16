package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.*;

public class FreestyleProjectTest extends BaseTest {

    private final String PROJECT_NAME = "New Freestyle Project";
    private static final String NEW_PROJECT_NAME = "New Freestyle project name";

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private boolean isProjectExist(String projectName) {
        goToJenkinsHomePage();
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    private void disableProjectByName(String projectName) {
        goToJenkinsHomePage();
        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createFreeStyleProject(String projectName) {
        goToJenkinsHomePage();
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

    private void clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@class='task-link ' and contains(@href, 'build')]")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElements(getDriver()
                .findElements(By.xpath("//a[@class='model-link inside build-link display-name']"))));
    }

    private void clickSaveConfiguration() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void clickProjectOnDashboard(String projectName) {
        getDriver().findElement(By
                .xpath(String.format("//a[@href='job/%s/']", projectName.replace(" ", "%20")))).click();
    }

    private void createAnItem(String itemName) {
        String createdItemName = "New " + itemName;

        if (isItemTitleExists(createdItemName)) {
            int randInt = ((int) (Math.random() * 100));
            createdItemName = createdItemName + randInt;
        } else {
            createdItemName = createdItemName;
        }

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(createdItemName);
        List<WebElement> items = getDriver().findElements(By.cssSelector(".label"));
        for (WebElement el : items) {
            if (itemName.equals(el.getText())) {
                el.click();
                break;
            }
        }
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
    }

    private boolean isItemTitleExists(String itemName) {
        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean res = false;
        if (itemsList.isEmpty()) {
            return res;
        } else {
            for (WebElement e : itemsList) {
                if (e.getText().equals(itemName)) {
                    res = true;
                    break;
                }
            }
        }

        return res;
    }

    private void hoverClickInput(String xpathLocator, String inputText) {
        new Actions(getDriver())
                .moveToElement(getDriver()
                .findElement(By.xpath(xpathLocator)))
                .click()
                .sendKeys(inputText)
                .perform();
    }

    private void hoverClick(String xpathLocator) {
        new Actions(getDriver())
                .moveToElement(getDriver()
                .findElement(By.xpath(xpathLocator)))
                .click()
                .perform();
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
    public void testDeleteProjectFromLeftSidMenuOnProjectPage() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@data-message,'Delete')]")).click();
        getDriver().switchTo().alert().accept();

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
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newProjectName);
        getDriver().findElement(By.name("Submit")).click();
        goToJenkinsHomePage();

        assertTrue(isProjectExist(newProjectName));
        assertFalse(isProjectExist(initialProjectName));
    }

    @Ignore
    @Test
    public void testErrorMessageWhenNewNameFieldEmpty() {
        final String initialProjectName = "Test Project";
        createFreeStyleProject(initialProjectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + initialProjectName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.TAB);

        String errorMessage = getDriver().findElement(By.className("error")).getText();
        assertEquals(errorMessage, "No name is specified");
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

        boolean isDisabled = getDriver()
                .findElement(By.id("enable-project"))
                .getText()
                .contains("This project is currently disabled");
        assertTrue(isDisabled);
    }

    @Test
    public void testDisableProjectFromConfigurePage() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + projectName + "')]")).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isDisabled = getDriver()
                .findElement(By.id("enable-project"))
                .getText()
                .contains("This project is currently disabled");
        assertTrue(isDisabled);
    }

    @Test
    public void testDisableProjectWhenCreating() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);

        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isDisabled = getDriver()
                .findElement(By.id("enable-project"))
                .getText()
                .contains("This project is currently disabled");
        assertTrue(isDisabled);
    }

    @Test
    public void testEnableProjectFromStatusPage() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);

        getDriver().findElement(By.name("Submit")).click();

        boolean isEnabled = getDriver()
                .findElement(By.name("Submit"))
                .getText()
                .contains("Disable Project");
        assertTrue(isEnabled);
    }

    @Test
    public void testEnableProjectFromConfigurePage() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);

        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isEnabled = getDriver()
                .findElement(By.name("Submit"))
                .getText()
                .contains("Disable Project");
        assertTrue(isEnabled);
    }

    @Test
    public void testWarningMessageOnStatusPageWhenDisabled() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);

        boolean isDisabled = getDriver()
                .findElement(By.id("enable-project"))
                .getText()
                .contains("This project is currently disabled");
        assertTrue(isDisabled);
    }

    @Test
    public void testEnableButtonOnStatusPageWhenDisabled() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);

        boolean isVisible = getDriver().findElement(By.name("Submit")).isDisplayed();
        String buttonName = getDriver().findElement(By.name("Submit")).getText();
        assertTrue(isVisible);
        assertTrue(buttonName.contains("Enable"));
    }

    @Test
    public void testStatusDisabledOnDashboardWhenDisabled() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);
        goToJenkinsHomePage();

        String actualProjectStatus = getDriver()
                .findElement(By.id("job_" + projectName))
                .findElement(By.className("svg-icon"))
                .getAttribute("title");
        assertEquals(actualProjectStatus, "Disabled");
    }

    @Test
    public void testScheduleBuildButtonOnDashboardWhenDisabled() {
        final String projectName = "Test Project";
        createFreeStyleProject(projectName);
        disableProjectByName(projectName);
        goToJenkinsHomePage();

        boolean isDisabled = getDriver().findElements(By.xpath("//*[@id='job_" + projectName + "']//*[@class='jenkins-table__cell--tight']//a")).isEmpty();
        assertTrue(isDisabled);
    }

    @Test
    public void testRedirectionToStatusPageAfterRenaming() {
        final String initialProjectName = "Test Project";
        final String newProjectName = "New Test Project";
        createFreeStyleProject(initialProjectName);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + initialProjectName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL + "a");
        getDriver().findElement(By.name("newName")).sendKeys(newProjectName);
        getDriver().findElement(By.name("Submit")).click();

        boolean isStatusPageSelected = getDriver()
                .findElement(By.linkText("Status"))
                .getAttribute("class")
                .contains("active");
        assertTrue(isStatusPageSelected);
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

    @Ignore
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

    @Ignore
    @Test(description = "Creating Freestyle project using duplicative name")
    public void testFreestyleProjectWithDublicativeName() {

        final String name = "Akiko";

        createFreeStyleProject(name);

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.id("ok-button")).click();

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
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("h1")).getText(),
                "Project " + PROJECT_NAME);
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
    public void testNewFreestyleProjectWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();

        String expectedNotice = "» This field cannot be empty, please enter a valid name";
        WebElement actualNotice = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));

        Assert.assertEquals(actualNotice.getText(), expectedNotice);
        Assert.assertFalse(getDriver().findElement(By.xpath("//button[@id='ok-button']"))
                .isEnabled());
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

    @Ignore
    @Test()
    public void testErrorWhenRenameWithExistingName() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage(); //New Freestyle Project

        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();
        getDriver().findElement(By.name("newName")).sendKeys(Keys.CONTROL + "a");
        getDriver().findElement(By.name("newName")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "The new name is the same as the current name.");
    }

    @Test
    public void testHelpDescriptionOfDiscardOldBuildsIsVisible() {
        createFreeStyleProject("New Freestyle Project");
        getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .help"))
                .getAttribute("style"), "display: block;");
    }

    @Test
    public void testHelpDescriptionOfDiscardOldBuildsIsClosed() {
        createFreeStyleProject("New Freestyle Project");
        WebElement helpButton = getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"));
        helpButton.click();
        helpButton.click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .help"))
                .getAttribute("style"), "display: none;");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testRenameFreestyleProjectSideMenu() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]"))).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']//button")).click();

        Assert.assertEquals(
                getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1"))).getText(),
                "Project " + NEW_PROJECT_NAME
        );
    }

    @Test
    public void testSelectThisProjectIsParameterizedCheckbox() {

        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();
        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();

        getDriver().findElement(By.xpath("//div[5]//span[1]//a[1]//span[1]//*[name()='svg']")).click();

        getDriver().findElement
                        (By.xpath("//label[normalize-space()='This project is parameterized']"))
                .click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[contains(text(), 'Add Parameter')]"))
                .isDisplayed());
    }

    @Test
    public void testFreestyleProjectConfigureGeneralSettingsThisProjectIsParameterizedCheckbox() {

        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();
        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]")).click();

        getDriver().findElement(By.xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//button[contains( text(), 'Add Parameter')]")).isDisplayed()
        );
    }

    @Test
    public void testFreestyleProjectConfigureGeneralSettingsThisProjectIsParameterizedCheckboxSelected() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();
        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), 'This project is parameterized')]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//label[contains(text(), 'This project is parameterized')]/../input"))
                        .isSelected());
    }

    @Ignore
    @Test
    public void testOldBuildsAreDiscarded() {

        final int numOfBuildNowClicks = 2;

        createFreeStyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//label[text()='Discard old builds']")).click();
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys(String.valueOf(numOfBuildNowClicks));
        getDriver().findElement(By.name("Submit")).click();

        for (int i = 0; i < numOfBuildNowClicks + 1; i++) {
            clickBuildNow();
        }

        getDriver().navigate().refresh();

        List<String> buildsList = getDriver().findElements(
                        By.xpath("//a[@class='model-link inside build-link display-name']"))
                .stream().map(WebElement::getText).toList();

        Assert.assertEquals(buildsList.get(buildsList.size() - 1), "#2");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testDeleteFreestyleProjectSideMenu() {
        getDriver().findElement(By.xpath("//span[contains(text(), '" + PROJECT_NAME + "')]/..")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Project')]/..")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertTrue(getDriver().findElements(By.id("job_" + PROJECT_NAME)).isEmpty());
    }

    @Test
    public void testEditDescriptionFreestyleProject() {
        final String editedDescriptionText = "New description text";
        final String descriptionText = "Description";

        createFreeStyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(descriptionText);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.cssSelector("a[id='description-link']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).clear();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(editedDescriptionText);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id='description']//div[1]")).getText(),
                editedDescriptionText);
    }

    @Ignore
    @Test(dependsOnMethods = {"testCreateFreestyleProjectWithValidName", "testRenameFreestyleProjectSideMenu"})
    public void testCreateFreestyleProjectFromExistingProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("from")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[contains(text(),'" + NEW_PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        goToJenkinsHomePage();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).isDisplayed());
    }

    @Ignore
    @Test
    public void testFreestyleProjectAdvancedSetting() {
        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("li[class = 'jenkins-breadcrumbs__list-item']")).click();

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(6) > span > a")).click();

        getDriver().findElement(By.xpath("(//button[@type='button'][normalize-space()='Advanced'])[3]")).click();
        getDriver().findElement(By.cssSelector("a[title='Help for feature: Quiet period']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='tbody dropdownList-container']//div[@class='help']//div")).isDisplayed());
    }

    @Test
    public void testFreestyleProjectNavigateToStatusPage() {
        String editedProjectName = PROJECT_NAME.replace(" ", "%20");

        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(), '" + PROJECT_NAME + "')]/..")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + editedProjectName));
    }

    @Test
    public void testDisableFreestyleProject() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.cssSelector("button[name = 'Submit']")).click();
        String result = getDriver().findElement(By.cssSelector("form[id='enable-project']")).getText();

        Assert.assertEquals("This project is currently disabled", result.substring(0, 34));
    }

    @Test
    public void testSetUpstreamProject() {
        final String upstreamProjectName = "Upstream Test";

        createFreeStyleProject(upstreamProjectName);
        goToJenkinsHomePage();
        createFreeStyleProject(PROJECT_NAME);

        WebElement buildAfterOtherProjectsCheckbox = getDriver()
                .findElement(By.xpath("//label[text()='Build after other projects are built']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsCheckbox);
        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys(upstreamProjectName);
        js.executeScript("arguments[0].scrollIntoView(true);", buildAfterOtherProjectsCheckbox);
        getDriver().findElement(
                By.xpath("//label[text()='Always trigger, even if the build is aborted']")).click();
        clickSaveConfiguration();

        js.executeScript("setTimeout(function(){\n" +
                "    location.reload();\n" +
                "}, 500);");

        Assert.assertEquals(getDriver().findElement(By.xpath("//ul[@style='list-style-type: none;']/li/a")).getText(),
                upstreamProjectName);
    }

    @Test
    public void testSettingsOfDiscardOldBuildsIsDisplayed() {
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .form-container.tr"))
                .getAttribute("style"), "");
    }

    @Test
    public void testDaysToKeepBuildsErrorMessageIsDisplayed() {
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();
        WebElement daysToKeepBuildsField = getDriver().findElement(By.cssSelector("input[name='_.daysToKeepStr']"));
        daysToKeepBuildsField.click();
        daysToKeepBuildsField.sendKeys("-2");
        getDriver().findElement(By.cssSelector("input[name='_.numToKeepStr']")).click();
        WebElement errorMessage =  getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@nameref='rowSetStart26']//div[@class='jenkins-form-item tr '][1]//div[@class='error']")));

        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testSettingsGitIsOpened() {
        createAnItem("Freestyle project");

        WebElement radioGit = getDriver().findElement(By.cssSelector("label[for='radio-block-1']"));
        new Actions(getDriver())
                .click(radioGit)
                .perform();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".form-container.tr[nameref='radio-block-1']"))
                .getAttribute("style"), "");
    }

    @Test
    public void testAddBuildStep() {
        final String projectName = "FSproject";
        final String buildStepTitle = "buildStep";
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        createFreeStyleProject(projectName);
        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//button[contains(text(), 'Add build step')]")));
        hoverClick("//button[contains(text(), 'Add build step')]");
        hoverClick("//a[contains(text(), 'Execute shell')]");
        hoverClickInput("//div[@class='CodeMirror-scroll cm-s-default']", buildStepTitle);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();
        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//div[@class='CodeMirror-scroll cm-s-default']")).getText(), buildStepTitle);
    }

    @Test
    public void testGitHubEditedLabelAppears() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        createAnItem("Freestyle project");

        hoverClick("//label[contains(text(), 'GitHub project')]");

        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.name("_.projectUrlStr")));

        hoverClick("//*[@id='main-panel']/form/div[1]/section[1]/div[6]/div[3]/div[2]/div[1]/button");

        hoverClickInput("//input[@name = '_.displayName']", "GitHubURL");

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//span[@class = 'jenkins-edited-section-label']"))
                        .getText()
                        .trim(),
                "Edited");
    }

    @Test
    public void testDescriptionPreviewAppears() {
        final String inputText = "This project describes smth";
        createAnItem("Freestyle project");

        hoverClickInput("//textarea[@name = 'description']", inputText);

        getDriver().findElement(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@class = 'textarea-preview']"))
                        .getText(),
                inputText);
    }

    @Test(dependsOnMethods = "testDescriptionPreviewAppears")
    public void testDescriptionPreviewHides() {
        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        hoverClick("//a[@class = 'textarea-hide-preview']");

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@class = 'textarea-preview']"))
                        .getCssValue("display"),
                "none");
    }

    @Test
    public void testVerifyValueOfInsertedGitSourceLink() {
        createFreeStyleProject("FreestyleProject");

        new Actions(getDriver())
                .moveToElement(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label[for='radio-block-1']"))))
                .click()
                .perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.id("source-code-management")));

        new Actions(getDriver())
                .moveToElement(
                        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//input[@checkdependson='credentialsId']"))))
                .click()
                .sendKeys("123")
                .perform();

        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();
        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//input[@checkdependson='credentialsId']")).getAttribute("value"),
                "123");
    }

    @Test
    public void testSetNumberDaysToKeepBuildsIsSaved(){
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();
        WebElement daysToKeepBuildsField = getDriver().findElement(By.cssSelector("input[name='_.daysToKeepStr']"));
        daysToKeepBuildsField.click();
        daysToKeepBuildsField.sendKeys("2");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector(".task-link-wrapper  [href='/job/New%20Freestyle%20project/configure']"))
                .click();

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("input[name='_.daysToKeepStr']")).getAttribute("value"),
                "2");

    }

    @Test
    public void testRenameProjectFromDashboard() {
        final String projectName = "FSproject";
        final String projectRename = "FSproject1";

        createFreeStyleProject(projectName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
        hoverClick("//*[@id='job_" + projectName + "']/td[3]/a");
        hoverClick("//a[@href='/job/" + projectName + "/confirm-rename']");
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        hoverClickInput("//input[@name='newName']", projectRename);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='" + projectRename + "']")).isDisplayed());
    }
}
