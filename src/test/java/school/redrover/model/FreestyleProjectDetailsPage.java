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

    public FreestyleProjectDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectDetailsPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage goToConfigureFromSideMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href = '/job/" + projectName + "/configure']")).click();
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

    public FreestyleProjectRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FreestyleProjectRenamePage(getDriver());
    }

    public FreestyleProjectDetailsPage refreshPage() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        getDriver().navigate().refresh();

        return this;
    }

    public FreestyleProjectBuildDetailsPage clickPermalinkLastBuild() {
        getDriver().findElement(By.xpath("//a[@href='lastBuild/']")).click();

        return new FreestyleProjectBuildDetailsPage(getDriver());
    }

    public String getPermalinksText() {

        return getDriver().findElement(By.cssSelector("ul[class='permalinks-list']")).getText();
    }

    public String getNewDescriptionText() {
        return getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText();
    }

    public FreestyleProjectDetailsPage clickSaveButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        return new FreestyleProjectDetailsPage(getDriver());
    }

    public boolean isJobExist() {

        return getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).isDisplayed();
    }
}
