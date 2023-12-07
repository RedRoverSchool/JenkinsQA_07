package school.redrover.model;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
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

    @FindBy(css = "a[href='api/']")
    private WebElement restApiButton;

    @FindBy(xpath = "//a[@class = 'jenkins-table__link jenkins-table__badge model-link inside']")
    private WebElement firstBuildButton;

    @FindBy(xpath = "//a[@href = '/job/FreestyleProject/1/']")
    private WebElement buildName;
    @FindBy(xpath = "//a[@class = 'jenkins-table__link jenkins-table__badge model-link inside']/button")
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

    public RestApiPage goRestApi() {
        restApiButton.click();

        return new RestApiPage(getDriver());
    }


    public BuildHistoryPage getBuildDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(buildName)
                .build()
                .perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(buildDropDownMenu)).click();

        new Actions(getDriver())
                .moveToElement(buildDropDownMenu)
                .click()
                .perform();

        return this;
    }

    public EditBuildInformationPage clickBuildDropDownMenu() {

        getWait2().until(ExpectedConditions.visibilityOf(editBuildInformationDropDownItem)).click();

        return new EditBuildInformationPage(getDriver());
    }
}