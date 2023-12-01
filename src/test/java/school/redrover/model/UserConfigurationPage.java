package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class UserConfigurationPage extends BasePage {

    @FindBy(xpath = "//*[@id='people']/tbody/tr[2]/td[2]/a")
    private WebElement username;

    @FindBy(xpath = "//*[@id='tasks']/div[4]/span/a")
    private WebElement configPage;

    @FindBy(xpath = "//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")
    private WebElement userFull;

    @FindBy(xpath = "//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")
    private WebElement fullUser;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button[1]")
    private WebElement saveButton;

    @FindBy(name = "_.primaryViewName")
    private WebElement defaultViewTextArea;

    final String fullName = "User User";

    public UserConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public UserConfigurationPage clickUsername() {
        username.click();
        return this;
    }

    public UserConfigurationPage clickConfigurationPage() {
        configPage.click();
        return this;
    }

    public UserConfigurationPage clearUserFull() {
        userFull.clear();
        return this;
    }

    public UserConfigurationPage sendKeysFullNameUser() {
        fullUser.sendKeys(fullName);
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

