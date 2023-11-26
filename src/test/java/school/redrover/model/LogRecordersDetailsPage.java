package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import school.redrover.model.base.BasePage;

import java.util.List;

public class LogRecordersDetailsPage extends BasePage {

    @FindBy(xpath = "//a[@href = 'configure']")
    private WebElement buttonConfigure;

    @FindBy(xpath = "//*[@id='clear-logrecorder']")
    private WebElement buttonClearThisLog;

    @FindBy(xpath = "//a[@href='/manage/log/']")
    private WebElement breadcrumbSystemLog;

    public LogRecordersDetailsPage(WebDriver driver) {
        super (driver);
    }

    public ConfigureLogRecorderPage clickConfigure() {
        buttonConfigure.click();

        return new ConfigureLogRecorderPage(getDriver());
    }

    public LogRecordersDetailsPage clickClearThisLog() {
        buttonClearThisLog.click();

        return this;
    }

    public SystemLogPage clickSystemLog(){
        breadcrumbSystemLog.click();

        return new SystemLogPage(getDriver());
    }
}
