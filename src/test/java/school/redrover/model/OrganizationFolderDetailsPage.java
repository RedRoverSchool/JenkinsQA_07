package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderDetailsPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenu;

    public OrganizationFolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public OrganizationFolderRenamePage clickRenameOptionFromLeftSideMenu() {
        renameSubmenu.click();

        return new OrganizationFolderRenamePage(getDriver());
    }
}
