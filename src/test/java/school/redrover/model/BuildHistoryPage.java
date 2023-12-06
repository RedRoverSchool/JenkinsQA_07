package school.redrover.model;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
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
}