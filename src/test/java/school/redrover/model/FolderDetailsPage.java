package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderDetailsPage extends BasePage {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(id = "description-link")
    private WebElement addDescription;

    @FindBy(className = "jenkins-input")
    private WebElement descriptionTextArea;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FolderRenamePage(getDriver());
    }

    public ConfigurationPage clickConfigure() {
        configure.click();

        return new ConfigurationPage(getDriver());
    }

    public FolderConfigurationPage clickConfigureFolder() {
        configure.click();
        return new FolderConfigurationPage(getDriver());
    }

    public FolderDetailsPage clickAddDescription() {
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
        return getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
    }

    public NewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//a[@class='content-block__link']")).click();
        return new NewItemPage(getDriver());
    }
}
