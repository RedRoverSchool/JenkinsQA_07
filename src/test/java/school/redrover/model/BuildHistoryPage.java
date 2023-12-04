package school.redrover.model;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    @FindBy(id = "main-panel")
    private WebElement mainPanel;

    @FindBy(id = "button-icon-legend")
    private WebElement iconLegendButton;

    @FindBy(xpath = "//h2[contains(text(),'Status')]/following::dl")
    private List<WebElement> iconLegendHeaders;

    @FindBy(xpath = "//tr[1]/td//button")
    private  WebElement lastTimeSinceInLineBuild;

    @FindBy(css = "div[class='tippy-content']")
    private WebElement visibleTippyBox;

    @FindBy(css = "#timeline-band-1")
    private WebElement timelineBand;

    @FindBy(xpath = "//a[@class = 'jenkins-table__link jenkins-table__badge model-link inside']")
    private WebElement firstBuildButton;

    @FindBy(xpath = "//a[@class = 'jenkins-table__link jenkins-table__badge model-link inside']/button[@class = " +
            "'jenkins-menu-dropdown-chevron'][1]")
    private WebElement buildDropDownMenu;

    @FindBy(xpath = "//span[text() = 'Edit Build Information']")
    private WebElement editBuildInformationDropDownItem;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMainPanel() {
        return mainPanel;
    }

    public List<WebElement> getIconLegendHeaders() {
        return iconLegendHeaders;
    }

    public BuildHistoryPage clickIconLegendButton() {
        iconLegendButton.click();
        return this;
    }

    public String getTextLastTimeSinceInLineBuild() {
        new Actions(getDriver()).
                moveToElement(lastTimeSinceInLineBuild).
                perform();

        return visibleTippyBox.getText();
    }

    public BuildHistoryPage clickLastTimeSinceInLineBuild() {
        lastTimeSinceInLineBuild.click();

        return this;
    }

    public BuildHistoryPage scrollTimelineBuildHistory() {
        new Actions(getDriver())
                .clickAndHold(timelineBand)
                .moveByOffset(-500,0)
                .release()
                .pause(1000)
                .perform();

        return this;
    }

    public Point getPointLocation() {

        return timelineBand.getLocation();
    }

    public BuildHistoryPage getBuildDropdownMenu() {
        int deltaX = firstBuildButton.getSize().getWidth() / 2;
        int deltaY = firstBuildButton.getSize().getHeight() / 2;

        new Actions(getDriver())
                .moveToElement(firstBuildButton)
                .scrollByAmount(deltaX, deltaY)
                .moveToElement(buildDropDownMenu)
                .click()
                .perform();                ;

        return this;
    }

    public EditBuildInformationPage clickBuildDropDownMenu() {
        getWait2().until(ExpectedConditions.visibilityOf(editBuildInformationDropDownItem)).click();

        return new EditBuildInformationPage(getDriver());
    }
}