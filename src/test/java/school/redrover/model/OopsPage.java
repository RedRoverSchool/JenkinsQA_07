package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.BaseTest;

public class OopsPage extends BasePage {
    public OopsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[@style = 'text-align: center']")
    WebElement errorMessage;

    public String getMessage() {

        return getWait5().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }
}
