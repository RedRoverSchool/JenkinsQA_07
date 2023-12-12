package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.HomePage;
import school.redrover.model.view.*;

public abstract class BaseViewConfigPage extends BasePage<BaseViewConfigPage>{

    @FindBy(name = "Submit")
    protected WebElement createButton;

    @FindBy(xpath = "//span[text()='Delete View']")
    private WebElement deleteView;

    public BaseViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public ViewPage clickOkButton() {
        createButton.click();

        return new ViewPage(getDriver());
    }

    public HomePage clickDeleteView() {
        deleteView.click();

        return new HomePage(getDriver());
    }
}
