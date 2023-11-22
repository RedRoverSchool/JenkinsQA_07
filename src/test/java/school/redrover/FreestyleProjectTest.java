package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.BreadcrumbPage;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.FreestyleProjectDetailsPage;
import school.redrover.model.FreestyleProjectRenamePage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.*;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "NewFreestyleProject";
    private static final String NEW_PROJECT_NAME = "NewFreestyleProjectName";
    private static final By LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR = By.xpath("//a[@href='/job/" + PROJECT_NAME + "/configure']");
    private static final String DESCRIPTION_TEXT = "Freestyle project description";
    private final static By LOCATOR_CREATED_JOB_LINK_MAIN_PAGE = By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]");

    private final static String NEW_DESCRIPTION_TEXT = "New freestyle project description";

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private boolean isProjectExist(String projectName) {
        goToJenkinsHomePage();
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    private void disableProjectByName(String projectName) {
        goToJenkinsHomePage();
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        clickSubmitButton();
    }

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        if (goToHomePage) {
            goToJenkinsHomePage();
        }
    }

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
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

    private void addDescriptionInConfiguration(String text) {
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(text);
        clickSubmitButton();
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

    private void clickSubmitButton() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void clickProjectOnDashboard(String projectName) {
        getDriver().findElement(By
                .xpath(String.format("//a[@href='job/%s/']", projectName.replace(" ", "%20")))).click();
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

    private void configureParameterizedBuild(String projectName, String choiceName, String choiceOptions) {
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();

        WebElement parameterizedOption = getDriver().findElement(By.xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']"));
        parameterizedOption.click();
        getDriver().findElement(By.xpath("//button[@suffix='parameterDefinitions']")).click();
        getDriver().findElement(By.linkText("Choice Parameter")).click();
        getDriver().findElement(By.name("parameter.name")).sendKeys(choiceName);
        getDriver().findElement(By.name("parameter.choices"))
                .sendKeys(choiceOptions.replace(" ", Keys.chord(Keys.SHIFT, Keys.ENTER)));
        getDriver().findElement(By.name("Submit")).click();
    }

    private void addTimeStampToConsoleOutput(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.linkText("REST API"))).perform();

        getDriver().findElement(By.xpath("//div[@id = 'tasks']//div[5]/span")).click();

        getDriver().findElement(By.xpath("//label[contains(text(), 'Add timestamps')]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private String createUniqueTextValue() {
        int desiredLength = 5;

        String testFreeStyleProjectName = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);
        return testFreeStyleProjectName;
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        String homePage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .goHomePage()
                .getJobDisplayName();

        Assert.assertEquals(homePage,PROJECT_NAME);
    }

    @Test
    public void testRenameProject() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickRename()
                .clearInputField()
                .enterName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .goHomePage();

        assertTrue(isProjectExist(NEW_PROJECT_NAME));
    }

    @Test
    public void testErrorMessageWhenNewNameFieldEmpty() {
        String expectedErrorMessage = "No name is specified";

        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .clickRenameLink()
                .clearInputField()
                .getErrorMessage();

        assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void testAddDescriptionFreestyleProject() {
        createFreeStyleProject(PROJECT_NAME);

        addDescriptionInConfiguration(DESCRIPTION_TEXT);

        assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                DESCRIPTION_TEXT);
    }

    @Test
    public void testAddDescriptionFromStatusPage() {
        createFreeStyleProject(PROJECT_NAME);

        goToJenkinsHomePage();

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();

        getDriver().findElement(By.cssSelector("#description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name ='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        assertTrue(getDriver().findElement(By.xpath("//div[contains(text(), description)]")).isDisplayed());
        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAddDescriptionFreestyleProject")
    public void testEditDescription() {
        String descriptionEditText = "Welcome";

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();

        changeDescriptionTextInStatus(descriptionEditText);

        assertTrue(getDriver().findElement(By.xpath("//div[contains(text(), descriptionAfterEdit)]")).isDisplayed());
        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), descriptionEditText);

    }

    @Test(dependsOnMethods = "testAddDescriptionFreestyleProject")
    public void testDeleteTheExistingDescription() {
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "");
    }

    @Test
    public void testTooltipDiscardOldBuildsIsVisible() {
        boolean tooltipIsVisible = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .tooltipDiscardOldBuildsIsVisible();

        Assert.assertTrue(tooltipIsVisible, "The tooltip is not displayed.");
    }

    @Test
    public void testDisableProjectFromStatusPage() {
        boolean isEnabled = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .clickEnableDisableButton()
                .isEnabled();

        assertFalse(isEnabled);
    }

    @Test
    public void testDisableProjectFromConfigurePage() {
        boolean isEnabled = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickDisableToggle()
                .clickSaveButton()
                .isEnabled();

        assertFalse(isEnabled);
    }

    @Test
    public void testDisableProjectWhenCreating() {
        boolean isEnabled = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .isEnabled();

        assertFalse(isEnabled);
    }

    @Test
    public void testEnableProjectFromStatusPage() {
        boolean isEnabled = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .clickEnableDisableButton()
                .isEnabled();

        assertTrue(isEnabled);
    }

    @Test
    public void testEnableProjectFromConfigurePage() {
        boolean isEnabled = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .clickConfigure()
                .clickDisableToggle()
                .clickSaveButton()
                .isEnabled();

        assertTrue(isEnabled);
    }

    @Test
    public void testWarningMessageOnStatusPageWhenDisabled() {
        String expectedWarningMessage = "This project is currently disabled";

        String actualWarningMessage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .getWarningMessageWhenDisabled();

        assertEquals(actualWarningMessage, expectedWarningMessage);
    }

    @Test
    public void testEnableButtonOnStatusPageWhenDisabled() {
        String expectedButtonName = "Enable";

        String actualButtonName = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .getTextEnableDisableButton();

        assertEquals(actualButtonName, expectedButtonName);
    }

    @Test
    public void testStatusDisabledOnDashboardWhenDisabled() {
        String expectedProjectStatus = "Disabled";

        String actualProjectStatus = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDisableToggle()
                .clickSaveButton()
                .goHomePage()
                .getProjectBuildStatusByName(PROJECT_NAME);

        assertEquals(actualProjectStatus, expectedProjectStatus);
    }

    @Test
    public void testScheduleBuildButtonOnDashboardWhenDisabled() {
        createFreeStyleProject(PROJECT_NAME);
        disableProjectByName(PROJECT_NAME);
        goToJenkinsHomePage();

        boolean isDisabled = getDriver().findElements(By.xpath("//*[@id='job_" + PROJECT_NAME + "']//*[@class='jenkins-table__cell--tight']//a")).isEmpty();
        assertTrue(isDisabled);
    }

    @Test
    public void testRedirectionToStatusPageAfterRenaming() {
        boolean isStatusPageSelected = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .clickRenameLink()
                .clearInputField()
                .enterName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .isStatusPageSelected();

        assertTrue(isStatusPageSelected);
    }

    @DataProvider(name = "ValidName")
    public String[][] validCredentials() {
        return new String[][]{
                {"Акико"}, {"Ак,ко"}, {"Акико"}, {"Akiko"}, {"12345`67890"}
        };
    }

    @Test(description = "Creating new Freestyle project using valid data", dataProvider = "ValidName")
    public void testFreestyleProjectWithValidData(String name) {
        createFreeStyleProject(name);
        clickSubmitButton();
        goToJenkinsHomePage();

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
        createFreeStyleProject(PROJECT_NAME);

        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();

        String textResult = getDriver().findElement(By.id("itemname-invalid")).getText();
        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));

        Assert.assertEquals(textResult, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
        Assert.assertFalse(buttonOk.isEnabled());
    }

    @Test
    public void testRenameToEmptyName() {
        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item'][2]")).click();
        getDriver().findElement(By.xpath(
                "//a[@class='task-link ' and contains(@href, 'confirm-rename')]")).click();
        getDriver().findElement(By.name("newName")).clear();
        clickSubmitButton();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No name is specified");
    }

    @Test
    public void testDisable() {
        createFreeStyleProject(PROJECT_NAME);
        clickSubmitButton();

        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='enable']/button")).isEnabled());
    }

    @Test
    public void testEnable() {
        createFreeStyleProject(PROJECT_NAME);
        clickSubmitButton();

        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();
        getDriver().findElement(By.xpath("//form[@action='enable']/button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='disable']")).isEnabled());
    }

    @Test
    public void testHelpDescriptionOfDiscardOldBuildsIsVisible() {
        createFreeStyleProject(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .help"))
                .getAttribute("style"), "display: block;");
    }

    @Test
    public void testHelpDescriptionOfDiscardOldBuildsIsClosed() {
        createFreeStyleProject(PROJECT_NAME);
        WebElement helpButton = getDriver().findElement(By.cssSelector("a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']"));
        helpButton.click();
        helpButton.click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .help"))
                .getAttribute("style"), "display: none;");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testRenameFreestyleProjectSideMenu() {
        getWait10().until(ExpectedConditions.elementToBeClickable(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE)).click();
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
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();

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
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
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
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();
        getDriver().findElement(By.xpath("//label[contains(text(), 'This project is parameterized')]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//label[contains(text(), 'This project is parameterized')]/../input"))
                        .isSelected());
    }

    @Test
    public void testOldBuildsAreDiscarded() {
        final int numOfBuildNowClicks = 2;

        createFreeStyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//label[text()='Discard old builds']")).click();
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys(String.valueOf(numOfBuildNowClicks));
        clickSubmitButton();

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
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Project')]/..")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertTrue(getDriver().findElements(By.id("job_" + PROJECT_NAME)).isEmpty());
    }

    @Test
    public void testEditDescriptionFreestyleProject() {
        createFreeStyleProject(PROJECT_NAME);

        String editDescription = new FreestyleProjectDetailsPage(getDriver())
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .editProjectDescriptionField(DESCRIPTION_TEXT)
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .editProjectDescriptionField(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getNewDescriptionText();

        Assert.assertEquals(editDescription,NEW_DESCRIPTION_TEXT);
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

        Assert.assertTrue(getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).isDisplayed());
    }

    @Test
    public void testFreestyleProjectAdvancedSetting() {
        createFreeStyleProject(PROJECT_NAME);
        clickSubmitButton();

        getDriver().findElement(By.cssSelector("li[class = 'jenkins-breadcrumbs__list-item']")).click();

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();

        getDriver().findElement(By.xpath("(//button[@type='button'][normalize-space()='Advanced'])[3]")).click();
        getDriver().findElement(By.cssSelector("a[title='Help for feature: Quiet period']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='tbody dropdownList-container']//div[@class='help']//div")).isDisplayed());
    }

    @Test
    public void testStatusPageUrlCheck() {
        String editedProjectName = PROJECT_NAME.replace(" ", "%20");

        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + editedProjectName));
    }

    @Test
    public void testDisableFreestyleProject() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside']")).click();
        clickSubmitButton();
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
        clickSubmitButton();

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
        WebElement errorMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@nameref='rowSetStart26']//div[@class='jenkins-form-item tr '][1]//div[@class='error']")));

        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testGitRadioButtonSettingsIsOpened() {
        createFreeStyleProject(PROJECT_NAME);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.id("source-code-management")));

        new Actions(getDriver())
                .moveToElement(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label[for='radio-block-1']"))))
                .click()
                .perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@tooltip = 'Help for feature: Repositories']"))
                        .getText(),
                "?");
    }

    @Test
    public void testAddBuildStep() {
        final String buildStepTitle = "buildStep";
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        createFreeStyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();

        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//button[contains(text(), 'Add build step')]")));

        hoverClick("//button[contains(text(), 'Add build step')]");

        hoverClick("//a[contains(text(), 'Execute shell')]");

        hoverClickInput("//div[@class='CodeMirror-scroll cm-s-default']", buildStepTitle);

        clickSubmitButton();

        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();

        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@class='CodeMirror-scroll cm-s-default']"))
                        .getText(),
                buildStepTitle);
    }

    @Test
    public void testGitHubEditedLabelAppears() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        createFreeStyleProject(PROJECT_NAME);

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
        createFreeStyleProject(PROJECT_NAME);

        hoverClickInput("//textarea[@name = 'description']", DESCRIPTION_TEXT);

        getDriver().findElement(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@class = 'textarea-preview']"))
                        .getText(),
                DESCRIPTION_TEXT);
    }

    @Ignore
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

    @Ignore
    @Test(dependsOnMethods = "testGitRadioButtonSettingsIsOpened")
    public void testVerifyValueOfInsertedGitSourceLink() {
        final String xpathLocator = "//input[@checkdependson='credentialsId']";
        final String inputText = "123";

        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        hoverClickInput(xpathLocator, inputText);

        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();
        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath(xpathLocator))
                        .getAttribute("value"),
                inputText);
    }

    @Test
    public void testSetNumberDaysToKeepBuildsIsSaved() {
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();
        WebElement daysToKeepBuildsField = getDriver().findElement(By.cssSelector("input[name='_.daysToKeepStr']"));
        daysToKeepBuildsField.click();
        daysToKeepBuildsField.sendKeys("2");
        clickSubmitButton();
        getDriver().findElement(By.cssSelector(".task-link-wrapper  [href='/job/New%20Freestyle%20project/configure']"))
                .click();

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("input[name='_.daysToKeepStr']")).getAttribute("value"),
                "2");
    }

    @Test
    public void testSavedNotificationIsDisplayed() {
        createAnItem("Freestyle project");
        getDriver().findElement(By.name("Apply")).click();
        String notificationIsDisplayed = getDriver().findElement(By.id("notification-bar")).getAttribute("class");

        Assert.assertTrue(notificationIsDisplayed.contains("--visible"));
    }


    @Ignore
    @Test
    public void testRenameProjectFromDashboard() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .goHomePage()
                .clickRenameInDropdownMenu(PROJECT_NAME, new FreestyleProjectRenamePage(getDriver()))
                .clearInputField()
                .enterName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .goHomePage();

        Assert.assertTrue(new HomePage(getDriver())
                .isProjectExist(NEW_PROJECT_NAME));
    }

    @Ignore
    @Test
    public void testConfigureBuildEnvironmentSettingsAddTimestamp() {
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement addTimestampsCheckbox = getDriver().findElement(
                By.xpath("//label[text()='Add timestamps to the Console Output']"));
        js.executeScript("arguments[0].scrollIntoView(true);", addTimestampsCheckbox);
        addTimestampsCheckbox.click();
        clickSubmitButton();

        getDriver().findElement(By.xpath("//span[text()='Build Now']/..")).click();
        js.executeScript("setTimeout(function() {location.reload();}, 2000);");
        getDriver().navigate().refresh();
        getWait10().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//span[@class='build-status-icon__outer']")))).click();

        List<WebElement> timestamps = getDriver().findElements(
                By.xpath("//pre[@class='console-output']//span[@class='timestamp']"));

        Assert.assertNotEquals(timestamps.size(), 0);
        for (WebElement timestamp : timestamps) {
            Assert.assertTrue(timestamp.getText().trim().matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"));
        }
    }

    @Test
    public void testMoveJobToFolder() {
        final String folderName = "FolderWrapper";
        final String destinationOption = "Jenkins » " + folderName;

        createProject("Freestyle project", PROJECT_NAME, true);
        createProject("Folder", folderName, true);

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.xpath("//span[text()='Move']/..")).click();

        Select destinationDropdown = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        destinationDropdown.selectByVisibleText(destinationOption);
        clickSubmitButton();
        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();

        Assert.assertTrue(getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).isDisplayed());
    }

    @Test
    public void testThisProjectIsParameterizedCheckboxAddBooleanParameter() {
        final String name = "Ņame";
        final String description = "Description";

        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]")).click();

        getDriver().findElement(By.
                        xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']"))
                .click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Add Parameter')]")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Boolean Parameter')]")).click();
        getDriver().findElement(By.xpath("//input[@name = 'parameter.name']"))
                .sendKeys(name);
        getDriver().findElement(By.xpath("//textarea[@name = 'parameter.description']"))
                .sendKeys(description);
        clickSubmitButton();

        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//input[@name = 'parameter.name']"))
                        .getAttribute("value"),
                name);
        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//textarea[@name = 'parameter.description']"))
                        .getAttribute("value"),
                description);
    }

    @Test
    public void testAddBooleanParameterDropdownIsSortedAlphabetically() {
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]")).click();

        getDriver().findElement(
                        By.xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']"))
                .click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Add Parameter')]")).click();

        List<WebElement> listDropDownElements = getDriver().findElements(By.xpath("//li[@index]"));
        List<String> getTextOfDropDownElements = new ArrayList<>();
        for (WebElement element : listDropDownElements) {
            getTextOfDropDownElements.add(element.getText());
        }

        List<String> expectedListResult = getTextOfDropDownElements.stream().sorted().toList();

        Assert.assertEquals(getTextOfDropDownElements, expectedListResult);
    }

    @Test
    public void testPermalinksListOnStatusPage() {
        final String[] buildSuccessfulPermalinks = {"Last build", "Last stable build", "Last successful build",
                "Last completed build"};

        createFreeStyleProject(PROJECT_NAME);
        clickSubmitButton();

        for (int i = 0; i < 4; i++) {
            getWait2().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Build Now"))).click();
        }

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

    @Test
    public void testAddLinkToGitHubInGitHubProjectSection() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, false);

        String inputUrlField = new FreestyleProjectDetailsPage(getDriver())
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .clickSourseCodeManagementLinkFromSideMenu()
                .scrollPage(0, 600)
                .clickGitRadioButton()
                .inputGitLink("https://github.com/RedRoverSchool/JenkinsQA_07")
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .clickSourseCodeManagementLinkFromSideMenu()
                .scrollPage(0, 600)
                .getInputGitLinkFieldValue();

        Assert.assertEquals(inputUrlField, "https://github.com/RedRoverSchool/JenkinsQA_07");
    }

    @Test
    public void testCheckDiscardOldBuildsCheckbox() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, false);

        String inputDaysToKeepBuilds = new FreestyleProjectDetailsPage(getDriver())
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .clickDiscardOldBuildsCheckBox()
                .scrollPage(0, 300)
                .inputMaxNumberOfBuildsToKeep("2")
                .inputDaysToKeepBuilds("3")
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .scrollPage(0, 300)
                .getInputDaysToKeepBuildsFieldValue();

        String inputMaxNumberOfBuildsToKeep = new FreestyleProjectConfigurePage(getDriver())
                .getInputMaxNumberOfBuildsToKeepFieldValue();

        Assert.assertEquals(inputDaysToKeepBuilds, "3");
        Assert.assertEquals(inputMaxNumberOfBuildsToKeep, "2");
    }

    @Test
    public void testCheckThrottleBuildsCheckbox() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, false);

        String numberOfBuilds = new FreestyleProjectDetailsPage(getDriver())
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .clickThrottleBuildsCheckBox()
                .scrollPage(0, 600)
                .inputNumberOfBuilds("4")
                .selectTimePeriod("day")
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .scrollPage(0, 600)
                .getNumberOfBuildsFieldValue();

        String timePeriod = new FreestyleProjectConfigurePage(getDriver())
                .getTimePeriodFieldValue();

        Assert.assertEquals(numberOfBuilds, "4");
        Assert.assertEquals(timePeriod, "day");
    }

    @Test
    public void testSelectExecuteConcurrentBuilds() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, false);

        List<WebElement> quantityOfElementsBeforeClicking = new FreestyleProjectDetailsPage(getDriver())
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .scrollPage(0, 300)
                .getExecuteConcurrentBuilds();

        List<WebElement> quantityOfElementsAfterClicking = new FreestyleProjectConfigurePage(getDriver())
                .clickExecuteConcurrentBuildsIfNecessaryCheckBox()
                .clickSaveButton()
                .goToConfigureFromSideMenu(PROJECT_NAME)
                .scrollPage(0, 300)
                .getExecuteConcurrentBuilds();

        Assert.assertEquals(quantityOfElementsAfterClicking.size(), quantityOfElementsBeforeClicking.size());
    }

    @Test
    public void testIsWorkspaceCreated() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        String titleBeforeWorkspaceCreating = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToWorkspaceFromSideMenu(PROJECT_NAME)
                .getTitleText();

        Assert.assertEquals(titleBeforeWorkspaceCreating, "Error: no workspace");

        String titleAfterWorkspaceCreating = new BreadcrumbPage(getDriver())
                .clickJenkinsIcon()
                .clickBuildByGreenArrow(PROJECT_NAME)
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToWorkspaceFromSideMenu(PROJECT_NAME)
                .getTitleText();

        Assert.assertEquals(titleAfterWorkspaceCreating, "Workspace of " + PROJECT_NAME + " on Built-In Node");
    }

    @Test
    public void testDisableProjectMessage() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(By.cssSelector(".jenkins-table__link.model-link.inside")).click();
        clickSubmitButton();

        boolean isMessageVisible = getDriver().findElement(By.className("warning")).isDisplayed();

        Assert.assertTrue(isMessageVisible, "The warning message is not visible.");
    }

    @Ignore
    @Test
    public void testDeletePermalinksOnProjectsStatusPage() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.cssSelector("a[onclick^='return build_']")).click();
        getDriver().navigate().refresh();

        getDriver().findElement(By.xpath("//a[@href='lastBuild/']")).click();
        getDriver().findElement(By.cssSelector("a[href$='confirmDelete']")).click();
        goToJenkinsHomePage();

        List<By> permaLinks = List.of(
                By.xpath("//ul[@class='permalinks-list']/li[1]"),
                By.xpath("//ul[@class='permalinks-list']/li[2]"),
                By.xpath("//ul[@class='permalinks-list']/li[3]"),
                By.xpath("//ul[@class='permalinks-list']/li[4]"));

        for (By link : permaLinks) {
            Assert.assertEquals(
                    getDriver().findElements(link).size(),
                    0);
        }
    }

    @Test
    public void testRenameUnsafeCharacters() {
        createFreeStyleProject(PROJECT_NAME);
        goToJenkinsHomePage();
        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(By.cssSelector("a[href$='confirm-rename']")).click();
        WebElement newName = getDriver().findElement(By.name("newName"));

        List<String> unsafeCharacters = List.of("%", "<", ">", "[", "]", "&", "#", "|", "/", "^");

        for (String x : unsafeCharacters) {
            newName.clear();
            newName.sendKeys(x);
            newName.sendKeys(Keys.TAB);
            getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='error']")));

            Assert.assertEquals(getDriver().findElement(By.cssSelector("div[class='error']")).getText(),
                    "‘" + x + "’ is an unsafe character");
        }
    }

    @Test
    public void testVisibilityHelDescriptionQuietPeriod() {
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(LOCATOR_CREATED_JOB_LINK_MAIN_PAGE).click();
        getDriver().findElement(LOCATOR_JOB_CONFIGURE_LINK_SIDE_BAR).click();
        getDriver().findElement(By.cssSelector(".config-table > .jenkins-form-item .jenkins-button")).click();
        getDriver().findElement(By.xpath("//a[@title='Help for feature: Quiet period']")).click();

        Assert.assertTrue(getDriver().findElement(By.
                        xpath("//div[@class='tbody dropdownList-container']//div[@class='help']//div")).isDisplayed(),
                "Help description of Quiet Period is not displayed!");
    }

    @Test
    public void testParameterizedBuildWithChoices() {
        final String choices = "Chrome Firefox Edge Safari";
        final String choiceName = "browsers";

        createProject("Freestyle project", PROJECT_NAME, true);
        configureParameterizedBuild(PROJECT_NAME, choiceName, choices);

        WebElement build = getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a"));
        build.click();

        List<WebElement> actualChoiceList = getDriver().findElements(By.xpath("//div[@class='setting-main']//select/option"));

        Assert.assertNotEquals(getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a")).getText(), "Build Now");
        Assert.assertEquals(getDriver().findElement(By.className("jenkins-form-label")).getText(), choiceName);
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='setting-main']//select/option[1]")).isSelected());
        Assert.assertFalse(actualChoiceList.isEmpty());
        for (WebElement ch : actualChoiceList) {
            Assert.assertTrue(choices.contains(ch.getText()));
        }
    }

    @Test
    public void testMoveFreestyleToFolder() {
        final String freestyleName = "freestyleName";
        final String folderName = "folderName";

        createProject("Freestyle project", freestyleName, true);
        createProject("Folder", folderName, true);

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + freestyleName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + freestyleName + "/move']")).click();

        Select select = new Select(getDriver().findElement(By.xpath("//select[@name = 'destination']")));
        select.selectByValue("/" + folderName);

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + folderName + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText().trim(), folderName);
        Assert.assertTrue(
                getDriver().findElement(By.xpath("//tbody/tr[@id = 'job_" + freestyleName + "']")).isDisplayed());
    }
}