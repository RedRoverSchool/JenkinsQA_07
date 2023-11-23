package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

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

    @FindBy(css = ".permalink-item")
    private List<WebElement> permalinksList;


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

    public List<String> getPermalinksList() {
        List<String> permalinks = new ArrayList<>();

        for (WebElement permalink : permalinksList) {
            permalinks.add(permalink.getText().substring(0, permalink.getText().indexOf(",")));
        }

        return permalinks;
    }
}
