package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class LogRecordersDetailsPage extends BasePage<LogRecordersDetailsPage> {

    @FindBy(xpath = "//a[@href = 'configure']")
    private WebElement configureButton;

    @FindBy(xpath = "//a[@href='/manage/log/']")
    private WebElement breadcrumbSystemLog;

    @FindBy(id = "clear-logrecorder")
    private WebElement clearThisLogButton;

    public LogRecordersDetailsPage(WebDriver driver) {
        super (driver);
    }

    public ConfigureLogRecorderPage clickConfigure() {
        configureButton.click();

        return new ConfigureLogRecorderPage(getDriver());
    }

    public LogRecordersDetailsPage clickClearThisLog() {
        clearThisLogButton.click();

        return this;
    }

    public String getEmptyHistoryLog() {

        return getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='main-panel']/div[2]"))).getText();
    }
}
