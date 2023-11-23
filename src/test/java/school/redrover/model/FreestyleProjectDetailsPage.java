package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.time.Duration;

public class FreestyleProjectDetailsPage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '/build?delay=0sec')]")
    private WebElement buildNowButton;

    @FindBy(name = "Submit")
    private WebElement enableDisableButton;

    @FindBy(linkText = "Configure")
    private WebElement configureLink;

    @FindBy(className = "warning")
    private WebElement warningMessage;

    @FindBy(linkText = "Status")
    private WebElement statusPageLink;

    @FindBy(linkText = "Rename")
    private WebElement renamePageLink;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuild;

    @FindBy(css = "ul[class='permalinks-list']")
    private WebElement listPermalinks;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//a[@href='editDescription']")
    private WebElement addOrEditDescriptionButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement projectDescriptionInputField;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(), 'Delete Project')]/..")
    private WebElement deleteProject;

    @FindBy(xpath = "//a[contains(@href,'configure')]")
    private WebElement configureButton;

    public FreestyleProjectDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectDetailsPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage goToConfigureFromSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureButton)).click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public FreestyleProjectDetailsPage clickEnableDisableButton() {
        enableDisableButton.click();
        return this;
    }

    public FreestyleProjectConfigurePage clickConfigure() {
        configureLink.click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public boolean isEnabled() {
        return getTextEnableDisableButton().equals("Disable Project");
    }

    public String getWarningMessageWhenDisabled() {
        return warningMessage.getText().split("\n")[0].trim();
    }

    public String getTextEnableDisableButton() {
        return enableDisableButton.getText();
    }

    public boolean isStatusPageSelected() {
        return statusPageLink.getAttribute("class").contains("active");
    }

    public FreestyleProjectRenamePage clickRenameLink() {
        renamePageLink.click();
        return new FreestyleProjectRenamePage(getDriver());
    }

    public WorkspacePage goToWorkspaceFromSideMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/ws/']")).click();
        return new WorkspacePage(getDriver());
    }

    public FreestyleProjectRenamePage clickRenameItem() {
        renamePageLink.click();

        return new FreestyleProjectRenamePage(getDriver());
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public FreestyleProjectDetailsPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public boolean isJobExist() {

        return getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).isDisplayed();
    }

    public FreestyleProjectDetailsPage refreshPage() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        getDriver().navigate().refresh();

        return this;
    }

    public FreestyleProjectBuildDetailsPage clickPermalinkLastBuild() {
        lastBuild.click();

        return new FreestyleProjectBuildDetailsPage(getDriver());
    }

    public String getPermalinksText() {

        return listPermalinks.getText();
    }

    public FreestyleProjectDetailsPage clickAddOrEditDescriptionButton() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public FreestyleProjectDetailsPage insertDescriptionText(String description) {
        projectDescriptionInputField.sendKeys(description);

        return this;
    }

    public FreestyleProjectDetailsPage deleteDescriptionText() {
        projectDescriptionInputField.clear();

        return this;
    }

    public HomePage deleteProject() {
        deleteProject.click();
        getDriver().switchTo().alert().accept();
        return new HomePage(getDriver());
    }
}
