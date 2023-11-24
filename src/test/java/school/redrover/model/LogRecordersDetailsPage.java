package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class LogRecordersDetailsPage extends BasePage {

    @FindBy(xpath = "//a[@href = 'configure']")
    private WebElement buttonConfigure;

    public LogRecordersDetailsPage(WebDriver driver) {
        super (driver);
    }

    public ConfigureLogRecorderPage clickConfigure() {
        buttonConfigure.click();

        return new ConfigureLogRecorderPage(getDriver());
    }
}