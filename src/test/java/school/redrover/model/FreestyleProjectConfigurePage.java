package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement inputDescription;

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

    public FreestyleProjectConfigurePage inputDescription(String description) {
        new Actions(getDriver())
                .moveToElement(inputDescription)
                .click()
                .sendKeys(description)
                .perform();

        return this;
    }

    public void clickPreviewDescription() {
        getDriver().findElement(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']")).click();
    }

    public void clickHidePreviewDescription() {
        getDriver().findElement(By.xpath("//a[@class = 'textarea-hide-preview']")).click();
    }

    public String getPreviewDescriptionText() {
        return getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']")).getText();
    }

    public String getCssValue(By locator, String cssValueName) {
        return getDriver().findElement(locator).getCssValue(cssValueName);
    }

    public FreestyleProjectConfigurePage dismissAlertAndStay() {
        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        return this;
    }
}
