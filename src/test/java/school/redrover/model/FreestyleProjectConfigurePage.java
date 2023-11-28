package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class FreestyleProjectConfigurePage extends BasePage {
    @FindBy(css = "a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']")
    private WebElement helpButtonDiscardOldBuilds;

    @FindBy(css = "[nameref='rowSetStart26'] .help")
    private WebElement helpDescriptionDiscardOldBuilds;

    @FindBy(id = "source-code-management")
    private WebElement sourceCodeManagementSectionHeader;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement sourseCodeManagementLink;

    @FindBy(xpath = "//label[normalize-space()='Discard old builds']")
    private WebElement discardOldBuildsCheckBox;

    @FindBy(xpath = "//label[normalize-space()='Throttle builds']")
    private WebElement throttleBuildsCheckBox;

    @FindBy(xpath = "//label[normalize-space()='Execute concurrent builds if necessary']")
    private WebElement executeConcurrentBuildsIfNecessaryCheckBox;

    @FindBy(xpath = "//label[@for='radio-block-1']")
    private WebElement gitRadioButton;

    @FindBy(xpath = "//div[@class = 'form-container tr']")
    private WebElement gitRadioButtonSettingsForm;

    @FindBy(xpath = "//input[@name='_.url']")
    private WebElement inputGitRadioButtonRepositoryUrlField;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement inputDaysToKeepBuildsField;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement inputMaxNumberOfBuildsToKeepField;

    @FindBy(xpath = "//input[@name='_.count']")
    private WebElement numberOfBuilds;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement selectTimePeriod;

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement disableToggle;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement inputProjectDescription;

    @FindBy(xpath = "//label[contains(text(), 'This project is parameterized')]")
    private WebElement getThisProjectIsParameterizedCheckbox;

    @FindBy(xpath = "//label[normalize-space()='This project is parameterized']")
    private WebElement clickCheckBoxThisProjectIsParametrized;

    @FindBy(xpath = "//button[contains( text(), 'Add Parameter')]")
    private WebElement clickAddParameterDropDownBtn;

    @FindBy(xpath = "//label[contains(text(), 'This project is parameterized')]/../input")
    private WebElement getThisProjectIsParameterizedCheckboxInput;

    @FindBy(xpath = "//a[@previewendpoint = '/markupFormatter/previewDescription']")
    private WebElement previewDescriptionButton;

    @FindBy(xpath = "//a[@class = 'textarea-hide-preview']")
    private WebElement hidePreviewDescriptionButton;

    @FindBy(xpath = "//div[@class = 'textarea-preview']")
    private WebElement descriptionPreviewText;

    @FindBy(xpath = "//button[@name = 'Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//label[contains(text(), 'GitHub project')]")
    private WebElement checkboxGitHubProject;

    @FindBy(xpath = "_.projectUrlStr")
    private WebElement inputGitHubProjectUrl;

    @FindBy(xpath = "//section[@nameref = 'rowSetStart30']/div[@nameref = 'rowSetStart27']//button")
    private WebElement advancedDropdownGitHubProject;

    @FindBy(xpath = "//input[@name = '_.displayName']")
    private WebElement inputDisplayNameGitHubProject;

    @FindBy(xpath = "//span[@class = 'jenkins-edited-section-label']")
    private WebElement labelEditedInGitHubProject;

    @FindBy(xpath = "//div[@class ='jenkins-form-item tr jenkins-form-item--tight']//button")
    private WebElement advancedButton;

    @FindBy(xpath = "//a[@title='Help for feature: Quiet period']")
    private WebElement quietPeriodToolTip;

    @FindBy(xpath = "//div[@class='tbody dropdownList-container']//div[@class='help']//div")
    private WebElement helpMessage;

    @FindBy(xpath = "//button[contains( text(), 'Add Parameter')]")
    private WebElement addParameterDropdownMenu;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement addBuildStepDropdown;

    @FindBy(xpath = "//a[text()='Execute shell']")
    private WebElement executeShellOption;

    @FindBy(xpath = "//div[@class='CodeMirror-scroll cm-s-default']")
    private WebElement shellScriptInput;

    @FindBy(xpath = "//button[@data-section-id='build-environment']")
    private WebElement buildEnvironmentSidebarItem;

    @FindBy(xpath = "//a[contains(text(), 'Boolean Parameter')]")
    private WebElement booleanParameterOption;

    @FindBy(xpath = "//input[@name = 'parameter.name']")
    private WebElement parameterNameInputBox;

    @FindBy(xpath = "//textarea[@name = 'parameter.description']")
    private WebElement parameterDescriptionInputBox;

    public FreestyleProjectConfigurePage(WebDriver driver) {
        super(driver);
    }

    public boolean tooltipDiscardOldBuildsIsVisible() {
        boolean tooltipIsVisible = true;
        new Actions(getDriver())
                .moveToElement(helpButtonDiscardOldBuilds)
                .perform();
        if (helpButtonDiscardOldBuilds.getAttribute("title").equals("Help for feature: Discard old builds")) {
            tooltipIsVisible = false;
        }
        return tooltipIsVisible;
    }

    public FreestyleProjectDetailsPage clickSaveButton() {
        saveButton.click();

        return new FreestyleProjectDetailsPage(getDriver());
    }

    public FreestyleProjectConfigurePage clickSourseCodeManagementLinkFromSideMenu() {
        sourseCodeManagementLink.click();

        return this;
    }

    public FreestyleProjectConfigurePage scrollPage(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(" + x + "," + y + ")");

        return this;
    }

    public FreestyleProjectConfigurePage clickGitRadioButtonWithScroll() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", sourceCodeManagementSectionHeader);

        gitRadioButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputGitHubRepositoryURLWithScroll(String repositoryUrl) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", sourceCodeManagementSectionHeader);

        inputGitRadioButtonRepositoryUrlField.sendKeys(repositoryUrl);

        return this;
    }

    public String getValueGitHubRepositoryURL() {return inputGitRadioButtonRepositoryUrlField.getAttribute("value");}

    public FreestyleProjectConfigurePage clickDiscardOldBuildsCheckBox() {
        discardOldBuildsCheckBox.click();
        return this;
    }

    public FreestyleProjectConfigurePage inputDaysToKeepBuilds(String num) {
        inputDaysToKeepBuildsField.sendKeys(num);
        return this;
    }

    public FreestyleProjectConfigurePage inputMaxNumberOfBuildsToKeep(String num) {
        inputMaxNumberOfBuildsToKeepField.sendKeys(num);
        return this;
    }

    public FreestyleProjectConfigurePage clickThrottleBuildsCheckBox() {
        throttleBuildsCheckBox.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputNumberOfBuilds(String num) {
        numberOfBuilds.clear();
        numberOfBuilds.sendKeys(num);

        return this;
    }

    public FreestyleProjectConfigurePage selectTimePeriod(String period) {
        Select select = new Select(selectTimePeriod);
        select.selectByValue(period);
        selectTimePeriod.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickExecuteConcurrentBuildsIfNecessaryCheckBox() {
        executeConcurrentBuildsIfNecessaryCheckBox.click();
        return this;
    }

    public String getExecuteConcurrentBuildsIfNecessaryCheckBoxValue(String value) { return executeConcurrentBuildsIfNecessaryCheckBox.getCssValue(value); }

    public FreestyleProjectConfigurePage clickDisableToggle() {
        disableToggle.click();

        return this;
    }

    public String getInputDaysToKeepBuildsFieldValue() {
        return inputDaysToKeepBuildsField.getAttribute("value");
    }

    public String getInputMaxNumberOfBuildsToKeepFieldValue() {
        return inputMaxNumberOfBuildsToKeepField.getAttribute("value");
    }

    public String getNumberOfBuildsFieldValue() {
        return numberOfBuilds.getAttribute("value");
    }

    public String getTimePeriodFieldValue() {
        return selectTimePeriod.getAttribute("value");
    }

    public List<WebElement> getExecuteConcurrentBuilds() { return getDriver().findElements(By.xpath("//div[@class='form-container']")); }

    public FreestyleProjectConfigurePage editProjectDescriptionField(String editDescription) {
        inputProjectDescription.clear();
        inputProjectDescription.sendKeys(editDescription);

        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage clickThisProjectIsParameterizedCheckbox() {
        getThisProjectIsParameterizedCheckbox.click();

        return this;
    }

    public WebElement getThisProjectIsParameterizedCheckbox() {
        return getThisProjectIsParameterizedCheckboxInput;
    }

    public FreestyleProjectConfigurePage clickOnParametrizedCheckBox(){
        clickCheckBoxThisProjectIsParametrized.click();
        return this;
    }
    public WebElement checkIsParameteresDropDownMenuAvailable(){
       return clickAddParameterDropDownBtn;
    }

    public FreestyleProjectConfigurePage inputDescription(String description) {
        inputProjectDescription.sendKeys(description);

        return this;
    }

    public FreestyleProjectConfigurePage clickPreviewDescription() {
        previewDescriptionButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickHidePreviewDescription() {
        hidePreviewDescriptionButton.click();

        return this;
    }

    public String getPreviewDescriptionText() {
        return descriptionPreviewText.getText();
    }

    public boolean isPreviewDescriptionTextDisplayed() {return descriptionPreviewText.isDisplayed();}

    public FreestyleProjectConfigurePage clickApply() {
        applyButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickCheckboxGitHubProject() {
        checkboxGitHubProject.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickAdvancedDropdownGitHubProjectWithScroll() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", checkboxGitHubProject);

        advancedDropdownGitHubProject.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputDisplayNameGitHubProject(String displayName) {
        inputDisplayNameGitHubProject.sendKeys(displayName);

        return this;
    }

    public boolean editedLabelInGitHubProjectIsDisplayed() {
        return labelEditedInGitHubProject.isDisplayed();
    }

    public FreestyleProjectConfigurePage clickAdvancedButton() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });",
                advancedButton);

        advancedButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickOnQuietPeriodToolTip() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });",
                quietPeriodToolTip);

        quietPeriodToolTip.click();
        return this;
    }

    public boolean helpMessageDisplay() {
        return helpMessage.isDisplayed();
    }

    public WebElement getAddParameterDropdownMenu() {
        return addParameterDropdownMenu;
    }

    public FreestyleProjectConfigurePage clickAddBuildStepsDropdown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript("arguments[0].scrollIntoView();",
                addBuildStepDropdown);

        new Actions(getDriver())
                .moveToElement(addBuildStepDropdown)
                .click()
                .perform();

        return this;
    }

    public FreestyleProjectConfigurePage clickExecuteShellOption() {
        new Actions(getDriver())
                .moveToElement(executeShellOption)
                .click()
                .perform();

        return this;
    }

    public FreestyleProjectConfigurePage inputShellScript(String script) {
        new Actions(getDriver())
                .moveToElement(shellScriptInput)
                .click()
                .sendKeys(script)
                .perform();

        return this;
    }

    public String getShellScriptText() {
        return shellScriptInput.getText();
    }

    public String getAttributeOfHelpDescriptionDiscardOldBuilds (){
        return helpDescriptionDiscardOldBuilds.getAttribute("style");
    }
    public FreestyleProjectConfigurePage clickHelpDescriptionOfDiscardOldBuilds(){
        helpButtonDiscardOldBuilds.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickAddParameterDropdown() {
        addParameterDropdownMenu.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickBooleanParameterOption() {
        booleanParameterOption.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputParameterName(String name) {
        parameterNameInputBox.sendKeys(name);

        return this;
    }

    public FreestyleProjectConfigurePage inputParameterDescription(String description) {
        parameterDescriptionInputBox.sendKeys(description);

        return this;
    }

    public String getParameterName() {
        return parameterNameInputBox.getAttribute("value");
    }

    public String getParameterDescription() {
        return parameterDescriptionInputBox.getAttribute("value");
    }

    public List<String> getParameterNameAndDescription() {
        return List.of(
                getParameterName(),
                getParameterDescription()
        );
    }

    public boolean isGitRadioButtonSettingsFormAppears() {return gitRadioButtonSettingsForm.isDisplayed();}
}
