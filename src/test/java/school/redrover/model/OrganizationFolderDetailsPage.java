package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderDetailsPage extends BaseProjectPage {

    public OrganizationFolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Delete Organization Folder")
    private WebElement buttonDelete;

    @FindBy(name = "Submit")
    private WebElement buttonSubmit;

    public HomePage clickDelete() {
        buttonDelete.click();
        buttonSubmit.click();

        return new HomePage(getDriver());
    }
}
