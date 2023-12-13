package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.NewItemPage;

import java.util.List;

public class BaseViewPage<Self extends BaseViewPage<?>> extends BasePage<BaseViewPage<?>> {

    @FindBy(xpath = "//span[contains(text(),'New Item')]/parent::a")
    private WebElement newItem;

    @FindBy(xpath = "//a[@id = 'description-link']")
    private WebElement addOrEditDescriptionButton;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement description;

    @FindBy(xpath = "//div[@id = 'main-panel']")
    private WebElement mainPanelContent;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a")
    private List<WebElement> columnNames;

    @FindBy(xpath = "//div[@class='tab active']")
    private WebElement activeViewTab;

    @FindBy(xpath = "//a[@class = 'jenkins-table__link model-link inside']")
    private List<WebElement> elementList;

    public BaseViewPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        newItem.click();
        return new NewItemPage(getDriver());
    }

    public Self clickAddOrEditDescription() {
        addOrEditDescriptionButton.click();

        return (Self) this;
    }

    public Self typeNewDescription(String description) {
        descriptionField.clear();
        descriptionField.sendKeys(description);

        return (Self) this;
    }

    public Self clearDescriptionField() {
        descriptionField.clear();

        return (Self) this;
    }

    public Self clickSaveDescription() {
        saveDescriptionButton.click();

        return (Self) this;
    }

    public String getDescription() {
        return description.getText();
    }

    public String getMainPanelText() {
        return mainPanelContent.getText();
    }

    public List<String> getColumnNamesList() {

        return columnNames.stream().map(WebElement::getText).toList();
    }

    public String getActiveViewName() {

        return activeViewTab.getText();
    }

    public List<String> getJobList() {
        return elementList.stream().map(WebElement::getText).toList();
    }
}
