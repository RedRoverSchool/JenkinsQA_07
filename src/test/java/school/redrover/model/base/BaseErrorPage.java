package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseErrorPage<Self extends BaseErrorPage<?>> extends BasePage<Self> {

    @FindBy(xpath = "//h1")
    private WebElement errorNotification;

    @FindBy(id = "main-panel")
    private WebElement mainPanel;

    public BaseErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorNotification() {
        return errorNotification.getText().trim();
    }

    public String getErrorFromMainPanel() {
        return mainPanel.getText();
    }
}
