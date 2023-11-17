package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NewItemPage extends BasePage {

    @FindBy(name = "name")
    private WebElement inputName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//li[@class = 'hudson_model_FreeStyleProject']")
    private WebElement freeStyleProject;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeItemName(String name) {
        inputName.sendKeys(name);

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

    public ConfigurationPage clickOk() {
        okButton.click();

        return new ConfigurationPage(getDriver());
    }

    public <T> T clickOk(T page) {
        okButton.click();

        return page;
    }

    public FreestyleProjectConfigurePage createFreestyleProject(String projectName) {
        inputName.sendKeys(projectName);
        freeStyleProject.click();
        okButton.click();

        return new FreestyleProjectConfigurePage(getDriver());
    }
}
