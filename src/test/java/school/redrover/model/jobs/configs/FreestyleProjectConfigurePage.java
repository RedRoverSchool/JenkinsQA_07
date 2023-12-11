package school.redrover.model.jobs.configs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseConfigurationPage;
import school.redrover.model.jobs.details.FreestyleProjectDetailsPage;

import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectConfigurePage extends BaseConfigurationPage<FreestyleProjectDetailsPage, FreestyleProjectConfigurePage> {

    @FindBy(xpath = "//select[@checkdependson='url']")
    private WebElement credentialsOption;

    @FindBy(css = "a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']")
    private WebElement discardOldBuildsHelpButton;

    @FindBy(css = "[nameref='rowSetStart26'] .help")
    private WebElement discardOldBuildsHelpDescription;

    @FindBy(id = "source-code-management")
    private WebElement sourceCodeManagementSectionHeader;

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement sourceCodeManagementLink;

    @FindBy(xpath = "//label[normalize-space()='Discard old builds']")
    private WebElement discardOldBuildsCheckBox;

    @FindBy(css = "[nameref='rowSetStart26'] .form-container.tr")
    private WebElement discardOldBuildsSettingsField;

    @FindBy(xpath = "//label[normalize-space()='Throttle builds']")
    private WebElement throttleBuildsCheckBox;

    @FindBy(xpath = "//label[normalize-space()='Execute concurrent builds if necessary']")
    private WebElement executeConcurrentBuildsIfNecessaryCheckBox;

    @FindBy(xpath = "//label[@for='radio-block-1']")
    private WebElement gitRadioButton;

    @FindBy(xpath = "//div[@class = 'form-container tr']")
    private WebElement githubProjectSection;

    @FindBy(xpath = "//input[@name='_.url']")
    private WebElement gitRepositoryUrlField;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuildsField;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumberOfBuildsToKeepField;

    @FindBy(xpath = "//input[@name='_.count']")
    private WebElement numberOfBuilds;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement selectTimePeriod;

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement disableEnableToggle;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement projectDescriptionTextarea;

    @FindBy(xpath = "//label[normalize-space()='This project is parameterized']")
    private WebElement parametrizedProjectCheckboxLabel;

    @FindBy(xpath = "//button[contains( text(), 'Add Parameter')]")
    private WebElement addParameterButton;

    @FindBy(xpath = "//label[contains(text(), 'This project is parameterized')]/../input")
    private WebElement parameterizedProjectCheckbox;

    @FindBy(xpath = "//a[@previewendpoint = '/markupFormatter/previewDescription']")
    private WebElement previewDescriptionButton;

    @FindBy(xpath = "//a[@class = 'textarea-hide-preview']")
    private WebElement hidePreviewDescriptionButton;

    @FindBy(xpath = "//div[@class = 'textarea-preview']")
    private WebElement descriptionPreviewText;

    @FindBy(xpath = "//button[@name = 'Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//label[contains(text(), 'GitHub project')]")
    private WebElement githubProjectCheckbox;

    @FindBy(xpath = "//section[@nameref = 'rowSetStart30']/div[@nameref = 'rowSetStart27']//button")
    private WebElement githubProjectAdvancedButton;

    @FindBy(xpath = "//input[@name = '_.displayName']")
    private WebElement githubProjectDisplayNameField;

    @FindBy(xpath = "//span[@class = 'jenkins-edited-section-label']")
    private WebElement githubProjectEditedLabel;

    @FindBy(xpath = "//div[@class ='jenkins-form-item tr jenkins-form-item--tight']//button")
    private WebElement advancedButton;

    @FindBy(xpath = "//a[@title='Help for feature: Quiet period']")
    private WebElement quietPeriodHelpIcon;

    @FindBy(xpath = "//div[@class='tbody dropdownList-container']//div[@class='help']//div")
    private WebElement quietPeriodHelpText;

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

    @FindBy(xpath = "//div[@id='notification-bar']/span")
    private WebElement savedNotificationMessage;

    @FindBy(css = "div[name='strategy'] div[class='error']")
    private WebElement notAPositiveNumberErrorMessage;

    @FindBy(xpath = "//label[text()='Build after other projects are built']")
    private WebElement buildAfterOtherProjectsCheckboxLabel;

    @FindBy(name = "_.upstreamProjects")
    private WebElement upstreamProjectField;

    @FindBy(xpath = "//label[text()='Always trigger, even if the build is aborted']")
    private WebElement alwaysTriggerRadio;

    @FindBy(xpath = "//label[text()='Add timestamps to the Console Output']")
    private WebElement addTimestampsToConsoleCheckbox;

    @FindBy(name = "parameter.choices")
    private WebElement parameterChoicesTextArea;

    @FindBy(xpath = "//div[@class='credentials-select-control']//span[@class='first-child']/button")
    private WebElement addButton;

    @FindBy(xpath = "//span[@title= 'Jenkins Credentials Provider']")
    private WebElement jenkinsOption;

    @FindBy(xpath = "//input[@name = '_.username']")
    private WebElement usernameCredentialsProvider;

    @FindBy(xpath = "//button[@id = 'credentials-add-submit-button']")
    private WebElement addButtonCredentialsProvider;

    public FreestyleProjectConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectDetailsPage createProjectPage() {
        return new FreestyleProjectDetailsPage(getDriver());
    }

    public boolean tooltipDiscardOldBuildsIsVisible() {
        boolean tooltipIsVisible = true;

        new Actions(getDriver())
                .moveToElement(discardOldBuildsHelpButton)
                .perform();
        if (discardOldBuildsHelpButton.getAttribute("title").equals("Help for feature: Discard old builds")) {
            tooltipIsVisible = false;
        }

        return tooltipIsVisible;
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

        gitRepositoryUrlField.sendKeys(repositoryUrl);

        return this;
    }

    public String getValueGitHubRepositoryURL() {
        return gitRepositoryUrlField.getAttribute("value");
    }

    public FreestyleProjectConfigurePage clickDiscardOldBuildsCheckBox() {
        discardOldBuildsCheckBox.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputDaysToKeepBuilds(String num) {
        daysToKeepBuildsField.sendKeys(num);

        return this;
    }

    public FreestyleProjectConfigurePage inputMaxNumberOfBuildsToKeep(String num) {
        maxNumberOfBuildsToKeepField.sendKeys(num);

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

    public String getExecuteConcurrentBuildsIfNecessaryCheckBoxValue(String value) {
        return executeConcurrentBuildsIfNecessaryCheckBox.getCssValue(value);
    }

    public FreestyleProjectConfigurePage clickDisableEnableToggle() {
        disableEnableToggle.click();

        return this;
    }

    public String getInputDaysToKeepBuildsFieldValue() {
        return daysToKeepBuildsField.getAttribute("value");
    }

    public String getInputMaxNumberOfBuildsToKeepFieldValue() {
        return maxNumberOfBuildsToKeepField.getAttribute("value");
    }

    public String getNumberOfBuildsFieldValue() {
        return numberOfBuilds.getAttribute("value");
    }

    public String getTimePeriodFieldValue() {
        return selectTimePeriod.getAttribute("value");
    }

    public List<WebElement> getExecuteConcurrentBuilds() {
        return getDriver().findElements(By.xpath("//div[@class='form-container']"));
    }

    public FreestyleProjectConfigurePage inputProjectDescription(String projectDescription) {
        projectDescriptionTextarea.clear();
        projectDescriptionTextarea.sendKeys(projectDescription);

        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage clickThisProjectIsParameterizedCheckbox() {
        parametrizedProjectCheckboxLabel.click();

        return this;
    }


    public FreestyleProjectConfigurePage inputDescription(String description) {
        projectDescriptionTextarea.sendKeys(description);

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

    public boolean isPreviewDescriptionTextDisplayed() {
        return descriptionPreviewText.isDisplayed();
    }

    public FreestyleProjectConfigurePage clickApply() {
        applyButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickCheckboxGitHubProject() {
        githubProjectCheckbox.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickAdvancedDropdownGitHubProjectWithScroll() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", githubProjectCheckbox);

        githubProjectAdvancedButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage inputDisplayNameGitHubProject(String displayName) {
        githubProjectDisplayNameField.sendKeys(displayName);

        return this;
    }

    public boolean isEditedLabelInGitHubProjectDisplayed() {
        return githubProjectEditedLabel.isDisplayed();
    }

    public FreestyleProjectConfigurePage clickAdvancedButton() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });",
                advancedButton);

        advancedButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickQuietPeriodHelpIcon() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });",
                quietPeriodHelpIcon);

        quietPeriodHelpIcon.click();
        return this;
    }

    public boolean isQuietPeriodHelpTextDisplayed() {
        return quietPeriodHelpText.isDisplayed();
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

    public String getAttributeOfHelpDescriptionDiscardOldBuilds() {
        return discardOldBuildsHelpDescription.getAttribute("style");
    }

    public FreestyleProjectConfigurePage clickHelpDescriptionOfDiscardOldBuilds() {
        discardOldBuildsHelpButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickAddParameter() {
        addParameterButton.click();

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

    public boolean isGitRadioButtonSettingsFormDisplayed() {
        return githubProjectSection.isDisplayed();
    }

    public List<String> getAddParameterDropdownText() {

        List<WebElement> listDropDownElements = getDriver().findElements(By.xpath("//li[@index]"));
        List<String> getTextOfDropDownElements = new ArrayList<>();

        for (WebElement element : listDropDownElements) {
            getTextOfDropDownElements.add(element.getText());
        }

        return getTextOfDropDownElements;
    }

    public boolean isDiscardOldBuildsSettingsFieldDisplayed() {
        return discardOldBuildsSettingsField.isDisplayed();
    }

    public String getSavedNotificationMessage() {

        return getWait2().until(ExpectedConditions
                .visibilityOf(savedNotificationMessage)).getText();
    }

    public String getErrorMessageText() {

        return getWait2().until(ExpectedConditions
                .visibilityOf(notAPositiveNumberErrorMessage)).getText();
    }

    public FreestyleProjectConfigurePage clickBuildAfterOtherProjectsAreBuilt() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", buildAfterOtherProjectsCheckboxLabel);

        return this;
    }

    public FreestyleProjectConfigurePage inputUpstreamProject(String projectName) {
        upstreamProjectField.sendKeys(projectName);

        return this;
    }

    public FreestyleProjectConfigurePage clickAlwaysTrigger() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", buildAfterOtherProjectsCheckboxLabel);
        alwaysTriggerRadio.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickAddTimestampsToConsoleOutput() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", addTimestampsToConsoleCheckbox);
        addTimestampsToConsoleCheckbox.click();

        return this;
    }

    public boolean isParametrizedProjectCheckboxSelected() {
        return parameterizedProjectCheckbox.isSelected();
    }

    public boolean isAddParameterButtonDisplayed() {
        return addParameterButton.isDisplayed();
    }

    public FreestyleProjectConfigurePage selectParameterType(String parameterName) {
        getDriver().findElement(By.linkText(parameterName)).click();

        return this;
    }

    public FreestyleProjectConfigurePage setParameterChoices(List<String> parameterChoices) {
        for (int i = 0; i < parameterChoices.size(); i++) {
            if (i != parameterChoices.size() - 1) {
                parameterChoicesTextArea.sendKeys(parameterChoices.get(i) + "\n");
            } else {
                parameterChoicesTextArea.sendKeys(parameterChoices.get(i));
            }
        }

        return this;
    }

    public FreestyleProjectConfigurePage clickAddButton() {
        addButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickJenkinsOption() {
        jenkinsOption.click();
        return this;
    }

    public FreestyleProjectConfigurePage inputUsername(String username) {
        usernameCredentialsProvider.sendKeys(username);
        return this;
    }

    public FreestyleProjectConfigurePage clickAddButtonCredentialsProvider() {
        addButtonCredentialsProvider.click();
        return this;
    }

    public boolean checkIfNewCredentialInTheMenu(String username) {

        Select s = new Select(credentialsOption);

        return s.getOptions()
                .stream()
                .map(x -> x.getText().replaceAll("[^a-zA-Z0-9]", " "))
                .anyMatch(y -> y.contains(username));
    }
}
