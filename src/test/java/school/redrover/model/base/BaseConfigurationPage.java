package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.FreestyleProjectConfigurePage;

public abstract class BaseConfigurationPage<ProjectPage extends BaseDetailsPage<?, ?>,Self extends BaseConfigurationPage<?, ?>> extends BasePage<Self> {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(name = "_.description")
    private WebElement descriptionInputFiled;

    public BaseConfigurationPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectPage createProjectPage();

    public ProjectPage clickSaveButton() {
        saveButton.click();

        return createProjectPage();
    }

    public Self enterDescription(String description) {
        descriptionInputFiled.sendKeys(description);

        return (Self)this;
    }
}
