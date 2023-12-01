package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class RenameErrorPage extends BaseProjectPage {
    public RenameErrorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='main-panel']/p")
    private WebElement errorText;

    public String getErrorText() {
        return errorText.getText();
    }
}
