package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseConfigurationPage;
import school.redrover.model.base.BasePage;

public class NewItemPage extends BasePage {

    @FindBy(name = "name")
    private WebElement inputName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//li[@class = 'hudson_model_FreeStyleProject']")
    private WebElement freeStyleProject;

    @FindBy(xpath = "//span[text()='Pipeline']")
    private WebElement pipeline;

    @FindBy(css = "li[class='hudson_matrix_MatrixProject']")
    private WebElement multiConfigurationProject;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folder;

    @FindBy(xpath = "//li[contains(@class, 'multibranch_Workflow')]")
    private WebElement multibranchPipeline;

    @FindBy(xpath = "//li[@class = 'jenkins_branch_OrganizationFolder']")
    private WebElement organizationFolder;

    @FindBy(id = "itemname-required")
    private WebElement requiredNameErrorMessage;

    @FindBy(id = "itemname-invalid")
    private WebElement invalidNameErrorMessage;

    @FindBy(id = "from")
    private WebElement cloneItemTextField;

    @FindBy(css = "div[class='add-item-name']")
    private WebElement inputValidationMessage;

    @FindBy(xpath = "//span[normalize-space()='Pipeline']")
    private WebElement pipeLineCategory;

    @FindBy(xpath = "//input[@id='from']")
    private WebElement fieldCopyFrom;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeItemName(String name) {
        inputName.sendKeys(name);

        return this;
    }

    public NewItemPage selectFreestyleProject() {
        freeStyleProject.click();

        return this;
    }

    public NewItemPage selectPipelineProject() {
        pipeline.click();

        return this;
    }

    public NewItemPage selectMultiConfigurationProject() {
        multiConfigurationProject.click();

        return this;
    }

    public NewItemPage selectItemFolder() {
        folder.click();

        return this;
    }

    public NewItemPage selectMultibranchPipeline() {
        multibranchPipeline.click();

        return this;
    }

    public NewItemPage selectOrganizationFolder() {
        organizationFolder.click();

        return this;
    }

    public <T extends BaseConfigurationPage> T clickOk(T page) {
        okButton.click();

        return page;
    }

    public <T> T clickOkWithError(T page) {
        okButton.click();

        return page;
    }

    public boolean isOkButtonEnabled() { return okButton.isEnabled(); }

    public String getRequiredNameErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(requiredNameErrorMessage)).getText();
    }

    public String getInvalidNameErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(invalidNameErrorMessage)).getText();
    }

    public FreestyleProjectConfigurePage createFreestyleProject(String projectName) {
        inputName.sendKeys(projectName);
        freeStyleProject.click();
        okButton.click();

        return new FreestyleProjectConfigurePage(getDriver());
    }

    public OrganizationFolderConfigurationPage createOrganizationFolder(String projectName) {
        inputName.sendKeys(projectName);
        organizationFolder.click();
        okButton.click();

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public boolean inputValidationMessage(String errorMessage) {
        inputName.sendKeys(Keys.TAB);

        return getWait2().until(ExpectedConditions.textToBePresentInElement(
                inputValidationMessage, errorMessage));
    }

    public boolean isCloneItemSectionDisplayed() {
        return !getDriver().findElements(By.className("item-copy")).isEmpty();
    }

    public NewItemPage enterExistentItemNameToClone(String itemName) {
        cloneItemTextField.sendKeys(itemName);

        return this;
    }

    public boolean isAutocompleteToCloneSuggested(String projectName) {
        return !getDriver()
                .findElements(By.xpath("//li[contains(text(),'" + projectName + "')]"))
                .isEmpty();
    }

    public PipelineConfigurationPage createPipeline(String projectName) {
        inputName.sendKeys(projectName);
        pipeline.click();
        okButton.click();

        return new PipelineConfigurationPage(getDriver());
    }

    public FolderConfigurationPage createFolder(String folderName) {
        inputName.sendKeys(folderName);
        folder.click();
        okButton.click();

        return new FolderConfigurationPage(getDriver());
    }

    public NewItemPage populateFieldCopyFrom(String multibranchPipelineName) {
        fieldCopyFrom.click();
        fieldCopyFrom.sendKeys(multibranchPipelineName);

        return this;
    }

    public String getRequiredNameErrorMessageColor() {
        return requiredNameErrorMessage.getCssValue("color");
    }
}
