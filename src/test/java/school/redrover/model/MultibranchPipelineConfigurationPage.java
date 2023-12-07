package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigurationPage;

public class MultibranchPipelineConfigurationPage extends BaseConfigurationPage<MultibranchPipelineDetailsPage> {

    @FindBy(xpath = "//a[@class='model-link'][contains(@href, 'job')]")
    private WebElement breadcrumbJobName;

    @FindBy(xpath = "//div[@class ='setting-main']/input")
    private WebElement nameField;

    @FindBy (xpath = "//h1")
    private WebElement error;

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement disableEnableToggle;

    public MultibranchPipelineConfigurationPage(WebDriver driver) {

        super(driver);
    }

    @Override
    protected MultibranchPipelineDetailsPage createProjectPage() {
        return new MultibranchPipelineDetailsPage(getDriver());
    }

    public String getJobNameFromBreadcrumb() {

        return breadcrumbJobName.getText();
    }

    public  MultibranchPipelineConfigurationPage clearField() {
        nameField.clear();

        return this;
    }

    public MultibranchPipelineConfigurationPage inputName(String name) {
        nameField.sendKeys(name);

        return this;
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
