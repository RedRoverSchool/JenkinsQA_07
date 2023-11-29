package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseErrorPage;

public class ErrorPage extends BaseErrorPage {

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1/following-sibling::p")
    private WebElement errorMessage;

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
