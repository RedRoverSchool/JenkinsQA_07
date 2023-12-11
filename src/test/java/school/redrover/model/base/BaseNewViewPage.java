package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.view.*;

public abstract class BaseNewViewPage extends BasePage {

    @FindBy(id = "name")
    protected WebElement nameInput;

    @FindBy(xpath = "//label[@for='hudson.model.ListView']")
    protected WebElement listViewTypeRadioButton;

    @FindBy(xpath = "//div/label[@for='hudson.model.MyView']")
    protected WebElement myViewTypeRadioButton;

    @FindBy(name = "Submit")
    protected WebElement createButton;

    @FindBy(xpath = "//div/label[@for='hudson.model.ProxyView']")
    private WebElement includeGlobalViewTypeRadioBTN;

    public BaseNewViewPage(WebDriver driver) {
        super(driver);
    }
}
