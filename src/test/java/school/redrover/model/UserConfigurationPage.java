package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class UserConfigurationPage extends BasePage<UserConfigurationPage> {

    @FindBy(name = "_.fullName")
    private WebElement fullName;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button[1]")
    private WebElement saveButton;

    @FindBy(name = "_.primaryViewName")
    private WebElement defaultViewTextArea;

    @FindBy(name = "_.description")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewDescriptionTextArea;

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

    public UserConfigurationPage typeDescription(String descriptionText) {
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(descriptionText);

        return this;
    }

    public UserConfigurationPage clickPreviewDescription() {
        previewButton.click();

        return this;
    }

    public String getPreviewDescriptionText() {
        return previewDescriptionTextArea.getText();
    }
}

