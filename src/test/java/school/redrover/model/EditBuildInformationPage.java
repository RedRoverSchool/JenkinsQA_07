package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class EditBuildInformationPage extends BasePage {

    @FindBy (xpath = "//input[@name = 'displayName']")
    private WebElement fieldDisplayName;

    @FindBy (xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescription;

    @FindBy (xpath = "//button[@name = 'Submit']")
    private WebElement buttonSave;

    public EditBuildInformationPage(WebDriver driver) {
        super(driver);
    }

    public EditBuildInformationPage setDisplayName() {
        fieldDisplayName.sendKeys("Edited name");
        return this;
    }

    public EditBuildInformationPage setFieldDescription() {
        fieldDescription.sendKeys("Edited description");
        return this;
    }

    public BuildInfoPage clickSaveButton() {
        buttonSave.click();

        return new BuildInfoPage(getDriver());
    }
}
