package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseErrorPage extends BaseModel {

    @FindBy(xpath = "//h1")
    WebElement errorNotification;

    public BaseErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorNotification() {
        return errorNotification.getText().trim();
    }
}
