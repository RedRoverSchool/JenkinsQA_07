package school.redrover.model.errors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseErrorPage;

public class AngryErrorPage extends BaseErrorPage<AngryErrorPage> {

    @FindBy(xpath = "//h2")
    private WebElement errorMessage;

    public AngryErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
