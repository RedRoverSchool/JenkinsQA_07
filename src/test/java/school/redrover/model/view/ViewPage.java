package school.redrover.model.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ViewPage extends BasePage<ViewPage> {
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

    @FindBy(linkText = "Edit View")
    private WebElement editViewLink;

    @FindBy(linkText = "add some existing jobs")
    private WebElement addJobsLinkFromMainPanel;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a")
    private List<WebElement> columnNames;

    @FindBy(xpath = "//div[@class='tab active']")
    private WebElement activeViewTab;

    @FindBy(xpath = "//span[text()='Delete View']")
    private WebElement deleteView;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ViewPage clickAddOrEditDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public ViewPage typeNewDescription(String newDescriptionForTheView) {
        descriptionField.clear();
        descriptionField.sendKeys(newDescriptionForTheView);

        return this;
    }

    public ViewPage clearDescriptionField() {
        descriptionField.clear();

        return this;
    }

    public ViewPage clickSaveDescription() {
        saveDescriptionButton.click();

        return this;
    }

    public String getDescription() {
        return description.getText();
    }

    public String getMainPanelText() {
        return mainPanelContent.getText();
    }

    public List<String> getJobList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//a[@class = 'jenkins-table__link model-link inside']"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public ListViewConfigPage clickEditView() {
        editViewLink.click();

        return new ListViewConfigPage(getDriver());
    }

    public ListViewConfigPage clickAddJobsFromMainPanel() {
        addJobsLinkFromMainPanel.click();

        return new ListViewConfigPage(getDriver());
    }

    public List<String> getColumnNamesList() {

        return columnNames.stream().map(WebElement::getText).toList();
    }

    public String getActiveViewName() {

        return activeViewTab.getText();
    }

    public ViewPage clickDeleteView() {
        deleteView.click();

        return this;
    }
}
