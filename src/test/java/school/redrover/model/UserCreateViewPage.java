package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class UserCreateViewPage extends BasePage {

    @FindBy(css = "#name")
    private WebElement viewNameTextArea;

    @FindBy(xpath = "//label[@for='hudson.model.MyView']")
    private WebElement myViewTypeCheckBox;

    @FindBy(name = "Submit")
    private WebElement createButton;

    public UserCreateViewPage(WebDriver driver) {
        super(driver);
    }

    public UserCreateViewPage createUserViewAndSave(String viewName) {
        viewNameTextArea.sendKeys(viewName);
        myViewTypeCheckBox.click();
        createButton.click();

        return this;
    }
}
