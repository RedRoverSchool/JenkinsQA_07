package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.OrganizationFolderRenamePage;

public abstract class BaseProjectPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenu;

    @FindBy(name = "Submit")
    private WebElement disableButton;

    @FindBy(id = "enable-project")
    private WebElement disableMessage;

    public BaseProjectPage(WebDriver driver) {super(driver);}

    public String getProjectName() {

        return projectName.getText();
    }

    public OrganizationFolderRenamePage clickRenameOptionFromLeftSideMenu() {
        renameSubmenu.click();

        return new OrganizationFolderRenamePage(getDriver());
    }

    public BaseProjectPage clickDisableButton() {
        disableButton.click();

        return this;
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().substring(0, 46);
    }
}
