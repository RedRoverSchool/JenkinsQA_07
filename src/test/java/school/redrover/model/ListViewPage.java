package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ListViewPage extends BasePage {
    @FindBy(xpath = "//a[@id = 'description-link']")
    private WebElement addOrEditDescriptionButton;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement description;

    public ListViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewPage clickAddOrEditDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public ListViewPage typeNewDescription(String newDescriptionForTheView) {
        descriptionField.clear();
        descriptionField.sendKeys(newDescriptionForTheView);

        return this;
    }

    public ListViewPage clearDescriptionField() {
        descriptionField.clear();

        return this;
    }

    public ListViewPage clickSaveDescription() {
        saveDescriptionButton.click();

        return this;
    }

    public String getDescription() {
        return description.getText();
    }
}
