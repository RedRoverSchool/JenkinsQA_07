package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderDetailsPage extends BaseProjectPage {

    @FindBy(css = "a[href$='configure']")
    private WebElement configureButtonSideMenu;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public OrganizationFolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderConfigurationPage clickConfigureSideMenu() {
        configureButtonSideMenu.click();

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public OrganizationFolderDetailsPage clickDisableSubmit() {
        submitButton.click();

        return this;
    }

    public String submitButtonText() {

        return submitButton.getText();
    }
}
