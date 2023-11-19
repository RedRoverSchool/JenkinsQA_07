package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderConfigurationPage extends BasePage {

    @FindBy(name="_.description")
    private WebElement descriptionTextField;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewSwitch;

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }


    public FolderConfigurationPage typeDescription(String description) {
        descriptionTextField.sendKeys(description);
        return this;
    }

    public FolderConfigurationPage clickPreviewDescription() {
        previewSwitch.click();
        return this;
    }

    public String getFolderDescription() {
        return getDriver().findElement(By.className("textarea-preview")).getText();
    }
}