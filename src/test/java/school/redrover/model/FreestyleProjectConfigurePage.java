package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FreestyleProjectConfigurePage extends BasePage {
    @FindBy(css = "a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']")
    private WebElement helpButtonDiscardOldBuilds;

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

    @FindBy(xpath = "//input[@name='_.url']")
    private WebElement inputGitLinkField;

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

    @FindBy(xpath = "//a[@previewendpoint = '/markupFormatter/previewDescription']")
    private WebElement previewDescription;

    @FindBy(xpath = "//a[@class = 'textarea-hide-preview']")
    private WebElement hidePreviewDescription;

    @FindBy(xpath = "//div[@class = 'textarea-preview']")
    private WebElement descriptionPreviewText;

    @FindBy(xpath = "//button[@name = 'Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//label[contains(text(), 'GitHub project')]")
    private WebElement checkboxGitHubProject;

    @FindBy(xpath = "//section[@nameref = 'rowSetStart30']/div[@nameref = 'rowSetStart27']//button")
    private WebElement advancedDropdownGitHubProject;

    @FindBy(xpath = "//input[@name = '_.displayName']")
    private WebElement inputDisplayNameGitHubProject;

    @FindBy(xpath = "//span[@class = 'jenkins-edited-section-label']")
    private WebElement labelEditedInGitHubProject;

    @FindBy (xpath = "//div[@class ='jenkins-form-item tr jenkins-form-item--tight']//button")
    private WebElement advancedButton;

    @FindBy(xpath = "//a[@title='Help for feature: Quiet period']")
    private WebElement quietPeriodToolTip;

    @FindBy(xpath = "//div[@class='tbody dropdownList-container']//div[@class='help']//div")
    private WebElement helpMessage;

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

    public FreestyleProjectConfigurePage clickGitRadioButton() {
        gitRadioButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage inputGitLink(String url) {
        inputGitLinkField.sendKeys(url);
        return this;
    }

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
        getDriver().findElement(By.xpath("//select[@name='_.durationName']")).click();
        getDriver().findElement(By.xpath("//option[@value='" + period + "']")).click();
        return this;
    }


    public FreestyleProjectConfigurePage clickExecuteConcurrentBuildsIfNecessaryCheckBox() {
        executeConcurrentBuildsIfNecessaryCheckBox.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickDisableToggle() {
        disableToggle.click();
        return this;
    }

    public String getInputGitLinkFieldValue() {
        return inputGitLinkField.getAttribute("value");
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

    public List<WebElement> getExecuteConcurrentBuilds() {
        return getDriver().findElements(By.xpath("//div[@class='form-container']"));
    }

    public FreestyleProjectConfigurePage editProjectDescriptionField(String editDescription) {
        inputProjectDescription.clear();
        inputProjectDescription.sendKeys(editDescription);
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage inputDescription(String description) {
        inputProjectDescription.sendKeys(description);

        return this;
    }

    public FreestyleProjectConfigurePage clickPreviewDescription() {
        previewDescription.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickHidePreviewDescription() {
        hidePreviewDescription.click();

        return this;
    }

    public String getPreviewDescriptionText() {
        return descriptionPreviewText.getText();
    }

    public String getCssValue(By locator, String cssValueName) {
        return getDriver().findElement(locator).getCssValue(cssValueName);
    }

    public FreestyleProjectConfigurePage clickApply() {
        applyButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage scrollToElement(By elementLocator) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(elementLocator));

        return this;
    }

    public FreestyleProjectConfigurePage clickCheckboxGitHubProject() {
        checkboxGitHubProject.click();

        return this;
    }

    public FreestyleProjectConfigurePage clickAdvancedDropdownGitHubProject() {
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
        advancedButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickOnQuietPeriodToolTip() {
        quietPeriodToolTip.click();
        return this;
    }

    public boolean helpMessageDisplay() {
        return helpMessage.isDisplayed();
    }
}
