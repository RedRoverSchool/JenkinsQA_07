package school.redrover.model.errors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseErrorPage;

public class ErrorPage extends BaseErrorPage<ErrorPage> {

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "p")
    private WebElement errorMessage;

    @FindBy(tagName = "h2")
    private WebElement errorMessageFromOopsPage;

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getErrorMessageFromOopsPage() {
        return errorMessageFromOopsPage.getText();
    }
}
