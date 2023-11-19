package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

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
    private WebElement numberOfBuildsLocator;


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
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage scrollPage(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(" + x + "," + y + ")");
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage clickGitRadioButton() {
        gitRadioButton.click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage inputGitLink(String url) {
        inputGitLinkField.sendKeys(url);
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage clickDiscardOldBuildsCheckBox() {
        discardOldBuildsCheckBox.click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage inputDaysToKeepBuilds(String num) {
        inputDaysToKeepBuildsField.sendKeys(num);
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage inputMaxNumberOfBuildsToKeep(String num) {
        inputMaxNumberOfBuildsToKeepField.sendKeys(num);
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage clickThrottleBuildsCheckBox() {
        throttleBuildsCheckBox.click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage inputNumberOfBuilds(String num) {
        numberOfBuildsLocator.clear();
        numberOfBuildsLocator.sendKeys(num);
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectConfigurePage selectTimePeriod(String period) {
        getDriver().findElement(By.xpath("//select[@name='_.durationName']")).click();
        getDriver().findElement(By.xpath("//option[@value='" + period + "']")).click();
        return new FreestyleProjectConfigurePage(getDriver());
    }


    public FreestyleProjectConfigurePage clickExecuteConcurrentBuildsIfNecessaryCheckBox() {
        executeConcurrentBuildsIfNecessaryCheckBox.click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

}
