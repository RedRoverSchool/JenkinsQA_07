package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class NewItemPage extends BasePage {

    @FindBy(name = "name")
    private WebElement inputName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//li[@class = 'hudson_model_FreeStyleProject']")
    private WebElement freeStyleProject;

    @FindBy(id = "itemname-required")
    private WebElement requiredNameErrorMessage;

    @FindBy(id = "itemname-invalid")
    private WebElement invalidNameErrorMessage;

    @FindBy(tagName = "h2")
    private WebElement requestErrorMessage;

    @FindBy(tagName = "p")
    private WebElement noNameErrorMessage;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folder;

    @FindBy(xpath = "//span[text()='Pipeline']")
    private WebElement pipeline;

    @FindBy(id = "from")
    private WebElement cloneItemTextField;

    @FindBy(css = "div[class='add-item-name']")
    private WebElement inputValidationMessage;

    @FindBy(css = "li[class='hudson_matrix_MatrixProject']")
    private WebElement MultiConfigurationProject;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeItemName(String name) {
        inputName.sendKeys(name);

        return this;
    }

    public NewItemPage selectMultibranchPipelineOption() {
        getDriver().findElement(By.xpath("//li[contains(@class, 'multibranch_Workflow')]")).click();

        return this;
    }

    public NewItemPage selectOrganizationFolder() {
        getDriver().findElement(By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']")).click();

        return this;
    }

    public NewItemPage selectFreestyleProject() {
        freeStyleProject.click();

        return this;
    }

    public <T> T clickOk(T page) {
        okButton.click();

        return page;
    }

    public MultibranchPipelineConfigurationPage clickOk() {
        okButton.click();

        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public String getRequiredNameErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(requiredNameErrorMessage)).getText();
    }

    public String getInvalidNameErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(invalidNameErrorMessage)).getText();
    }

    public String getRequestErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(requestErrorMessage)).getText();
    }

    public String getNoNameErrorMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(noNameErrorMessage)).getText();
    }

    public FreestyleProjectConfigurePage createFreestyleProject(String projectName) {
        inputName.sendKeys(projectName);
        freeStyleProject.click();
        okButton.click();

        return new FreestyleProjectConfigurePage(getDriver());
    }

    public NewItemPage selectItemFolder() {
        folder.click();

        return this;
    }

    public NewItemPage selectMultiConfigurationProject() {
        MultiConfigurationProject.click();

        return this;
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

    public NewItemPage selectPipelineProject() {
        pipeline.click();

        return this;
    }

    public FolderConfigurationPage createFolder(String folderName) {
        inputName.sendKeys(folderName);
        folder.click();
        okButton.click();

        return new FolderConfigurationPage(getDriver());
    }
}
