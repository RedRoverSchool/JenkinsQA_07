package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class CustomLogRecorderPage extends BasePage {

    public CustomLogRecorderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@tooltip='More actions']")
    private WebElement moreActions;

    @FindBy(xpath = "//a[@data-post='true']")
    private WebElement deleteLogRecorder;

    public CustomLogRecorderPage clickMoreActions() {
        moreActions.click();

        return this;
    }

    public SystemLogPage deleteLogRecorder() {
        new Actions(getDriver())
                .pause(400)
                .moveToElement(deleteLogRecorder)
                .click()
                .perform();
        getWait5().until(ExpectedConditions.alertIsPresent()).accept();

        return new SystemLogPage(getDriver());
    }
}
