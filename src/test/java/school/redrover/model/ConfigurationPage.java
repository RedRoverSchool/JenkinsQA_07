package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ConfigurationPage extends BasePage {

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement inputDisplayName;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public ConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationPage typeDisplayName(String displayName) {
        inputDisplayName.sendKeys(displayName);

        return this;
    }

    public FolderDetailsPage clickSave() {
        saveButton.click();

        return new FolderDetailsPage(getDriver());
    }
}
