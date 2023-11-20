package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

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

}
