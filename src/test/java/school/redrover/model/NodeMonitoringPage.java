package school.redrover.model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeMonitoringPage extends BasePage {

    @FindBy(name = "_.freeSpaceThreshold")
    private WebElement freeSpaceThresholdPlaceholder;

    @FindBy(xpath = "//button[@formnovalidate = 'formNoValidate']")
    private WebElement submitButtonNodeMonitoring;

    public NodeMonitoringPage(WebDriver driver) {
        super(driver);
    }

    public NodeMonitoringPage clearSpaceThresholdPlaceholder() {
        freeSpaceThresholdPlaceholder.clear();

        return this;
    }

    public NodeMonitoringPage sendSpaceThreshold(int size, String unit) {
        freeSpaceThresholdPlaceholder.sendKeys(size + unit);

        return this;
    }

    public NodesListPage clickSubmitButtonNodeMonitoring() {
        submitButtonNodeMonitoring.click();

        return new NodesListPage(getDriver());
    }

    public String getFreeSpaceThreshold() {

        return freeSpaceThresholdPlaceholder.getAttribute("value");

    }
}
