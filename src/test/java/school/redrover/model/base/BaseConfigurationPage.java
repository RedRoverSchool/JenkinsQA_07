package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigurationPage<ProjectPage extends BaseDetailsPage<?, ?>,Self extends BaseConfigurationPage<?, ?>> extends BasePage<Self> {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    public BaseConfigurationPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectPage createProjectPage();

    public ProjectPage clickSaveButton() {
        saveButton.click();

        return createProjectPage();
    }
}
