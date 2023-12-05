package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseUserPage;

public class UserConfigurationPage extends BaseUserPage {

    @FindBy(name = "_.fullName")
    private WebElement fullName;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button[1]")
    private WebElement saveButton;

    @FindBy(name = "_.primaryViewName")
    private WebElement defaultViewTextArea;

    public UserConfigurationPage(WebDriver driver) {
        super(driver);
    }
    
    public UserConfigurationPage sendKeysFullNameUser(String name) {
        fullName.clear();
        fullName.sendKeys(name);
        return this;
    }

    public UserConfigurationPage clickSaveButton() {
        saveButton.click();
        return new UserConfigurationPage(getDriver());
    }

    public CreatedUserPage setDefaultUserViewAndSave(String viewName) {
        defaultViewTextArea.sendKeys(viewName);
        saveButton.click();

        return new CreatedUserPage(getDriver());
    }
}

