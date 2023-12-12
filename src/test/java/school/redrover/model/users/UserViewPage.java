package school.redrover.model.users;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class UserViewPage extends BasePage<UserViewPage> {

    @FindBy(className = "addTab")
    private WebElement addNewViewTab;

    @FindBy(xpath = "//div[@class='tab active']")
    private WebElement userViewActiveTab;

    public UserViewPage(WebDriver driver) {
        super(driver);
    }

    public UserCreateViewPage clickAddMyViews() {
        addNewViewTab.click();

        return new UserCreateViewPage(getDriver());
    }

    public String getUserViewActiveTabName() {
        return userViewActiveTab.getText();
    }
}
