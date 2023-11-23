package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class ErrorPage extends BasePage {

    @FindBy(id = "main-panel")
    WebElement errorMessage;

    @FindBy(tagName = "h2")
    private WebElement requestErrorMessage;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public String getRequestErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(requestErrorMessage)).getText();
    }
}
