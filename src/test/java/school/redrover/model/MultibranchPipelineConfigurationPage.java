package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineConfigurationPage extends BasePage {

    @FindBy(xpath = "//a[@class='model-link'][contains(@href, 'job')]")
    private WebElement breadcrumbJobName;

    @FindBy(xpath = "//div[@class ='setting-main']/input")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button")
    private WebElement buttonSubmit;

    @FindBy (xpath = "//h1")
    private WebElement nameH1;

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
}
