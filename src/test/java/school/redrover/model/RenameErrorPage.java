package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class RenameErrorPage extends BasePage {
    public RenameErrorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "p")
    private WebElement errorText;

    public String getErrorText() {
        return errorText.getText();
    }
}
