package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigurationPage extends BasePage {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    public BaseConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public <ProjectPage extends BaseProjectPage> ProjectPage clickSaveButton(ProjectPage projectPage) {
        saveButton.click();

        return projectPage;
    }
}
