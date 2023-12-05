package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class FolderDetailsPage extends BaseProjectPage {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(className = "jenkins-input")
    private WebElement descriptionTextArea;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//a[contains(@href, '/newJob')]")
    private WebElement newItemButton;

    @FindBy(xpath = "//a[contains(@href,'move')]")
    private WebElement moveJob;

    @FindBy(xpath = "//a[@class='content-block__link']")
    private WebElement createJob;

    @FindBy(xpath = "//a[contains(@class, 'jenkins-table__link')]")
    private List<WebElement> jobsList;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewButton;

    @FindBy(className = "textarea-hide-preview")
    private WebElement previewHideButton;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement descriptionPreview;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement actualFolderDescription;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameButton;

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage clickConfigureFolder() {
        configure.click();

        return new FolderConfigurationPage(getDriver());
    }

    public FolderDetailsPage clickAddOrEditDescription() {
        addDescription.click();

        return this;
    }

    public FolderDetailsPage typeDescription(String description) {
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(description);

        return this;
    }

    public FolderDetailsPage clickSave() {
        submitButton.click();

        return this;
    }

    public String getActualFolderDescription() {

        return actualFolderDescription.getText();
    }

    public NewItemPage clickCreateJob() {
        createJob.click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemButton() {
        newItemButton.click();

        return new NewItemPage(getDriver());
    }

    public FolderConfigurationPage clickConfigureInSideMenu() {
        configure.click();

        return new FolderConfigurationPage(getDriver());
    }

    public MovePage clickMove() {
        moveJob.click();

        return new MovePage(getDriver());
    }

    public List<String> getJobListInsideFolder() {
        return jobsList.stream().map(WebElement::getText).toList();
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();

        return page;
    }

    public FolderDetailsPage clearDescriptionTextArea() {
        descriptionTextArea.clear();

        return this;
    }

    public String getDescriptionButtonText() {

        return getDriver().findElement(By.xpath("//div[@id='description']/div[2]")).getText();
    }

    public boolean isJobInJobsList(String jobName) {

        return getJobListInsideFolder().contains(jobName);
    }

    public FolderDetailsPage clickPreview() {
        previewButton.click();

        return this;
    }

    public String getDescriptionPreview() {

        return descriptionPreview.getText();
    }

    public FolderDetailsPage clickHidePreview() {
        previewHideButton.click();

        return this;
    }
}


