package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ListViewPage extends BasePage {
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

    public String getMainPanelText() {
        return mainPanelContent.getText();
    }

    public List<String> getJobList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//a[@class = 'jenkins-table__link model-link inside']"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public ListViewConfigurationPage clickEditView() {
        editViewLink.click();

        return new ListViewConfigurationPage(getDriver());
    }

    public ListViewConfigurationPage clickAddJobsFromMainPanel() {
        addJobsLinkFromMainPanel.click();

        return new ListViewConfigurationPage(getDriver());
    }
}
