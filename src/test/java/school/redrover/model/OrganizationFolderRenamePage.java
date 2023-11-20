package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement inputNewName;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public OrganizationFolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderRenamePage enterNewName(String name) {
        inputNewName.clear();
        inputNewName.sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigurationPage clickSubmitButton() {
        submitButton.click();

        return new OrganizationFolderConfigurationPage(getDriver());
    }
}
