package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class PipelineDetailsPage extends BasePage {
    public PipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "description-link")
    private WebElement addDescription;

    @FindBy(css = "textarea[name ='description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//div[@id = 'description']//button[@name = 'Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div")
    private WebElement description;

    public PipelineDetailsPage clickAddDescription() {
        addDescription.click();

        return this;
    }

    public PipelineDetailsPage inputDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    public PipelineDetailsPage clickSaveButton() {
        saveButton.click();
        getWait2().until(ExpectedConditions.visibilityOf(description));

        return this;
    }

    public String getDescription() {

        return description.getText();
    }
}
