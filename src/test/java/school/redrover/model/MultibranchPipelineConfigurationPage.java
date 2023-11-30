package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineConfigurationPage extends BasePage {

    @FindBy(xpath = "//a[@class='model-link'][contains(@href, 'job')]")
    private WebElement breadcrumbJobName;

    @FindBy(xpath = "//div[@class ='setting-main']/input")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button[1]")
    private WebElement buttonSubmit;

    @FindBy (xpath = "//h1")
    private WebElement nameH1;

    @FindBy (xpath = "//a[contains(@href, 'delete')]")
    private WebElement buttonDelete;

    @FindBy (xpath = "//h1")
    private WebElement error;

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement disableEnableToggle;

    public MultibranchPipelineConfigurationPage(WebDriver driver) {

        super(driver);
    }

    public String getJobNameFromBreadcrumb() {

        return breadcrumbJobName.getText();
    }

    public String getJobName() {

        return breadcrumbJobName.getText();
    }
    public MultibranchPipelineConfigurationPage confirmRename(String name) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + name + "/confirm-rename']")).click();

        return this;
    }

    public  MultibranchPipelineConfigurationPage clearField() {
        nameField.clear();

        return this;
    }

    public MultibranchPipelineConfigurationPage inputName(String name) {
        nameField.sendKeys(name);

        return this;
    }

    public MultibranchPipelineConfigurationPage buttonSubmit() {
        buttonSubmit.click();

        return this;
    }

    public String headerName() {

        return nameH1.getText();
    }

    public MultibranchPipelineDeletePage clickButtonDelete() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buttonDelete)).click();
        buttonDelete.click();

        return new MultibranchPipelineDeletePage(getDriver());
    }

    public String error() {

        return error.getText();
    }

    public MultibranchPipelineConfigurationPage clickDisableToggle() {
        disableEnableToggle.click();

        return this;
    }

    public String getDisableToggleText() {
        return disableEnableToggle.getText();
    }
}
