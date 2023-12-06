package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderDetailsPage extends BaseProjectPage<OrganizationFolderConfigurationPage> {

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(linkText = "Delete Organization Folder")
    private WebElement buttonDelete;

    public OrganizationFolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderConfigurationPage createConfigurationPage() {
        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public OrganizationFolderDetailsPage clickDisable() {
        submitButton.click();

        return this;
    }

    public String submitButtonText() {

        return submitButton.getText();
    }

    public HomePage clickDelete() {
        buttonDelete.click();
        submitButton.click();

        return new HomePage(getDriver());
    }
}
