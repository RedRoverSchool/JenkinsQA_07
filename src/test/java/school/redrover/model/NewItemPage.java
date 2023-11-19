package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public <T> T clickOk (T page) {
        okButton.click();

        return page;
    }

    public MultibranchPipelineConfigurationPage clickOk () {
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
        getDriver().findElement(By.cssSelector("li[class='hudson_matrix_MatrixProject']")).click();

        return this;
    }

    public String inputValidationMessage() {
        return  getDriver().findElement(By.cssSelector("div[class='add-item-name']")).getText();
    }

}
