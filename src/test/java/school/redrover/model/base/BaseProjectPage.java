package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.OrganizationFolderRenamePage;
import school.redrover.model.RenamePage;

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

    public <ProjectRenamePage extends RenamePage> ProjectRenamePage clickRenameOption(ProjectRenamePage projectRenamePage) {
        renameSubmenu.click();

        return projectRenamePage;
    }

    public BaseProjectPage clickDisableButton() {
        disableButton.click();

        return this;
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().substring(0, 46);
    }
}
