package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.HomePage;
import school.redrover.model.view.*;

public abstract class BaseViewConfigPage extends BasePage<BaseViewConfigPage>{

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//span[text()='Delete View']")
    private WebElement deleteView;

    public BaseViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public HomePage clickDeleteView() {
        deleteView.click();

        return new HomePage(getDriver());
    }
}
