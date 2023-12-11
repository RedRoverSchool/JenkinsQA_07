package school.redrover.model.users;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class UserConfigurationPage extends BasePage<UserConfigurationPage> {

    @FindBy(name = "_.fullName")
    private WebElement fullName;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(name = "_.primaryViewName")
    private WebElement defaultViewTextArea;

    @FindBy(name = "_.description")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewDescriptionTextArea;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//*[@tooltip]")
    private List<WebElement> allTooltipsList;

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

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public List<String> getTooltipTitleText() {

        return allTooltipsList
                .stream()
                .map(tooltip -> tooltip.getAttribute("tooltip"))
                .toList();
    }
}

