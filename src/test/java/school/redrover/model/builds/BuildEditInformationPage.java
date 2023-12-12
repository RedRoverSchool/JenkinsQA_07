package school.redrover.model.builds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class BuildEditInformationPage extends BasePage<BuildEditInformationPage> {

    @FindBy(name = "Submit")
    private WebElement saveButton;
    @FindBy(xpath = "//input[@name='displayName']")
    private WebElement displayNameArea;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    public BuildEditInformationPage(WebDriver driver) {
        super(driver);
    }

    public BuildPage clickSaveButton() {
        saveButton.click();

        return new BuildPage(getDriver());
    }

    public BuildEditInformationPage enterDisplayName(String buildDisplayName) {
        displayNameArea.sendKeys(buildDisplayName);

        return new BuildEditInformationPage(getDriver());
    }

    public BuildEditInformationPage addDescriptionForBuild(String descriptionText){
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(descriptionText);

        return new BuildEditInformationPage(getDriver());
    }
}
