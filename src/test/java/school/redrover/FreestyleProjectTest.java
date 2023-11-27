package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final static String NAME = "Ņame";
    private final static String DESCRIPTION = "Description";

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

        Assert.assertEquals(homePage, PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProjectSideMenu() {
        String homePage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject("other" + PROJECT_NAME)
                .clickSaveButton()
                .goHomePage()
                .getJobDisplayName();
        boolean projectExist = new HomePage(getDriver())
                .clickJobByName("other" + PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .deleteProject()
                .isProjectExist("other" + PROJECT_NAME);

        Assert.assertFalse(projectExist);
    }
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testRenameProject() {
        final HomePage homePage = new HomePage(getDriver())
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickRenameItem()
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

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .inputDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        assertEquals(actualDescription, DESCRIPTION_TEXT);
    }

    @Test
    public void testAddDescriptionFromStatusPage() {
        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickAddOrEditDescriptionButton()
                .insertDescriptionText(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAddDescriptionFreestyleProject")
    public void testEditDescription() {
        String actualNewDescriptionText = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickAddOrEditDescriptionButton()
                .deleteDescriptionText()
                .insertDescriptionText(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualNewDescriptionText, NEW_DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAddDescriptionFreestyleProject")
    public void testDeleteTheExistingDescription() {
        String actualEmptyDescriptionInputField = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickAddOrEditDescriptionButton()
                .deleteDescriptionText()
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualEmptyDescriptionInputField, "");
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

    @Test
    public void testFreestyleProjectWithDuplicateName() {
        ErrorPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .clickRenameLink()
                .clickRenameButtonAndRedirectErrorPage();

        Assert.assertEquals(errorPage.getErrorMessage(), "The new name is the same as the current name.");
    }

    @Test
    public void testRenameToEmptyName() {
        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickRenameItem()
                .clickRenameButtonEmptyName()
                .getErrorText();

        Assert.assertEquals(errorText, "No name is specified");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testDisable() {
//        createFreeStyleProject(PROJECT_NAME);
//        clickSubmitButton();
//
//        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();
//
//        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='enable']/button")).isEnabled());

        FreestyleProjectDetailsPage detailsPage = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickEnableDisableButton();

        Assert.assertTrue(detailsPage.isProjectDisabled());
    }

    @Test(dependsOnMethods = "testDisable")
    public void testEnable() {
//        createFreeStyleProject(PROJECT_NAME);
//        clickSubmitButton();
//
//        getDriver().findElement(By.xpath("//form[@action='disable']/button")).click();
//        getDriver().findElement(By.xpath("//form[@action='enable']/button")).click();
//
//        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@action='disable']")).isEnabled());
        FreestyleProjectDetailsPage detailsPage = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickEnableDisableButton();

        Assert.assertFalse(detailsPage.isProjectDisabled());
    }

    @Test(dependsOnMethods = "testTooltipDiscardOldBuildsIsVisible")
    public void testHelpDescriptionOfDiscardOldBuildsIsVisible() {
        String actualResult = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToConfigureFromSideMenu()
                .clickHelpDescriptionOfDiscardOldBuilds();

        assertEquals(actualResult, "display: block;");
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

    @Ignore
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testFreestyleProjectConfigureGeneralSettingsThisProjectIsParameterizedCheckbox() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .goHomePage();
        WebElement addParameter = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickThisProjectIsParameterizedCheckbox()
                .getAddParameterDropdownMenu();

        Assert.assertTrue(addParameter.isDisplayed());
    }

    @Test
    public void testFreestyleProjectConfigureGeneralSettingsThisProjectIsParameterizedCheckboxSelected() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickSaveButton()
                .goHomePage();

        WebElement thisProjectIsParameterizedCheckbox = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickThisProjectIsParameterizedCheckbox()
                .getThisProjectIsParameterizedCheckbox();

        Assert.assertTrue(thisProjectIsParameterizedCheckbox.isSelected());
    }
    @Ignore
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

    @Ignore("Expected condition failed: waiting for element to be clickable")
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testEditDescriptionFreestyleProject() {

        String editDescription = new HomePage(getDriver())
                .clickOnJob()
                .goToConfigureFromSideMenu()
                .editProjectDescriptionField(DESCRIPTION_TEXT)
                .clickSaveButton()
                .goToConfigureFromSideMenu()
                .editProjectDescriptionField(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(editDescription, NEW_DESCRIPTION_TEXT);
    }

    @Ignore("Expected condition failed: waiting for element to be clickable")
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testFreestyleProjectAdvancedSetting() {
        boolean helpMessageDisplay = new HomePage(getDriver())
                .clickOnJob()
                .goToConfigureFromSideMenu()
                .clickAdvancedButton()
                .clickOnQuietPeriodToolTip()
                .helpMessageDisplay();

        Assert.assertTrue(helpMessageDisplay);
    }

    @Ignore("Expected condition failed: waiting for element to be clickable")
    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testStatusPageUrlCheck() {
        String editedProjectName = PROJECT_NAME.replace(" ", "%20");

        String currentUrl = new HomePage(getDriver())
                .clickOnJob()
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/job/" + editedProjectName));
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

    @Ignore
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

        String shellScript = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickAddBuildStepsDropdown()
                .clickExecuteShellOption()
                .inputShellScript(buildStepTitle)
                .clickSaveButton()
                .clickConfigure()
                .getShellScriptText();

        Assert.assertEquals(shellScript, buildStepTitle);
    }

    @Test
    public void testGitHubEditedLabelAppears() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickCheckboxGitHubProject()
                .scrollToElement(By.name("_.projectUrlStr"))
                .clickAdvancedDropdownGitHubProject()
                .inputDisplayNameGitHubProject("GitHubURL");

        Assert.assertTrue(new FreestyleProjectConfigurePage(getDriver()).editedLabelInGitHubProjectIsDisplayed());
    }

    @Test
    public void testDescriptionPreviewAppears() {
        new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .inputDescription(DESCRIPTION_TEXT)
                .clickApply()
                .clickPreviewDescription();

        Assert.assertEquals(
                new FreestyleProjectConfigurePage(getDriver()).getPreviewDescriptionText(),
                DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testDescriptionPreviewAppears")
    public void testDescriptionPreviewHides() {
        new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickPreviewDescription()
                .clickHidePreviewDescription();

        Assert.assertEquals(
                new FreestyleProjectConfigurePage(getDriver()).getCssValue(By.xpath("//div[@class = 'textarea-preview']"), "display"),
                "none");
    }

    @Ignore
    @Test(dependsOnMethods = "testGitRadioButtonSettingsIsOpened")
    public void testVerifyValueOfInsertedGitSourceLink() {
        final String xpathLocator = "//input[@checkdependson='credentialsId']";
        final String inputText = "123";

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
        final String daysToKeepBuilds = "2";

        String daysToKeepBuildsFieldValue = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .clickDiscardOldBuildsCheckBox()
                .inputDaysToKeepBuilds(daysToKeepBuilds)
                .clickSaveButton()
                .clickConfigure()
                .getInputDaysToKeepBuildsFieldValue();

        Assert.assertEquals(daysToKeepBuildsFieldValue, daysToKeepBuilds);
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
        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        FreestyleProjectConfigurePage configurePage = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickThisProjectIsParameterizedCheckbox()
                .clickAddParameterDropdown()
                .clickBooleanParameterOption()
                .inputParameterName(NAME)
                .inputParameterDescription(DESCRIPTION)
                .clickSaveButton()
                .goHomePage()
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure();

        Assert.assertTrue(configurePage.getParameterName().equals(NAME) &&
                configurePage.getParameterDescription().equals(DESCRIPTION));
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
                .goToConfigureFromSideMenu()
                .clickSourseCodeManagementLinkFromSideMenu()
                .scrollPage(0, 600)
                .clickGitRadioButton()
                .inputGitLink("https://github.com/RedRoverSchool/JenkinsQA_07")
                .clickSaveButton()
                .goToConfigureFromSideMenu()
                .clickSourseCodeManagementLinkFromSideMenu()
                .scrollPage(0, 600)
                .getInputGitLinkFieldValue();

        Assert.assertEquals(inputUrlField, "https://github.com/RedRoverSchool/JenkinsQA_07");
    }

    @Test
    public void testCheckDiscardOldBuildsCheckbox() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, false);

        String inputDaysToKeepBuilds = new FreestyleProjectDetailsPage(getDriver())
                .goToConfigureFromSideMenu()
                .clickDiscardOldBuildsCheckBox()
                .scrollPage(0, 300)
                .inputMaxNumberOfBuildsToKeep("2")
                .inputDaysToKeepBuilds("3")
                .clickSaveButton()
                .goToConfigureFromSideMenu()
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
                .goToConfigureFromSideMenu()
                .clickThrottleBuildsCheckBox()
                .scrollPage(0, 600)
                .inputNumberOfBuilds("4")
                .selectTimePeriod("day")
                .clickSaveButton()
                .goToConfigureFromSideMenu()
                .scrollPage(0, 600)
                .getNumberOfBuildsFieldValue();

        String timePeriod = new FreestyleProjectConfigurePage(getDriver())
                .getTimePeriodFieldValue();

        Assert.assertEquals(numberOfBuilds, "4");
        Assert.assertEquals(timePeriod, "day");
    }

    @Test
    public void testSelectExecuteConcurrentBuilds() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        String checkBoxDisplayStyle = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToConfigureFromSideMenu()
                .scrollPage(0, 300)
                .clickExecuteConcurrentBuildsIfNecessaryCheckBox()
                .clickSaveButton()
                .goToConfigureFromSideMenu()
                .scrollPage(0, 300)
                .getExecuteConcurrentBuildsIfNecessaryCheckBoxValue("display");

        Assert.assertNotEquals(checkBoxDisplayStyle, "none");
    }

    @Test
    public void testIsWorkspaceCreated() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        String titleBeforeWorkspaceCreating = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToWorkspaceFromSideMenu()
                .getTitleText();

        Assert.assertEquals(titleBeforeWorkspaceCreating, "Error: no workspace");

        String titleAfterWorkspaceCreating = new BreadcrumbPage(getDriver())
                .clickJenkinsIcon()
                .clickBuildByGreenArrow(PROJECT_NAME)
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .goToWorkspaceFromSideMenu()
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

    @Test(dependsOnMethods = "testTooltipDiscardOldBuildsIsVisible")
    public void testDeletePermalinksOnProjectsStatusPage() {
        final List<String> removedPermalinks = List.of(
                "Last build",
                "Last stable build",
                "Last successful build",
                "Last completed build");

        String permaLinks = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickBuildNowButton()
                .refreshPage()
                .clickPermalinkLastBuild()
                .clickDeleteBuildSidePanel()
                .clickButtonDeleteBuild()
                .getPermalinksText();

        for (String x : removedPermalinks) {
            Assert.assertFalse(permaLinks.contains(x));
        }
    }

    @Test(dependsOnMethods = "testDeletePermalinksOnProjectsStatusPage")
    public void testRenameUnsafeCharacters() {
        final List<String> unsafeCharacters = List.of("%", "<", ">", "[", "]", "&", "#", "|", "/", "^");

        FreestyleProjectRenamePage error = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickRenameLink();

        for (String x : unsafeCharacters) {
            error.clearInputField()
                    .enterName(x);

            Assert.assertEquals(error.getErrorMessage(), "‘" + x + "’ is an unsafe character");
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

    @Test
    public void testVerify7ItemsSidePanelDetailsPage() {
        final List<String> itemsExpected = new ArrayList<>(Arrays.asList("Status", "Changes", "Workspace", "Build Now", "Configure", "Delete Project", "Rename"));

        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        List<String> itemsActual = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .getTextItemsSidePanel();

        Assert.assertEquals(itemsActual, itemsExpected);
    }
}