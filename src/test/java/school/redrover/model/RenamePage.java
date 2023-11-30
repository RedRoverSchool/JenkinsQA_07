package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public abstract class RenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement inputNewName;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@class='warning']")
    private WebElement warningMessage;

    public RenamePage(WebDriver driver) {
        super(driver);
    }

    public RenamePage enterNewName(String name) {
        inputNewName.clear();
        inputNewName.sendKeys(name);

        return this;
    }

    public <ProjectPage extends BaseProjectPage> ProjectPage clickRenameButton(ProjectPage projectPage) {
        renameButton.click();

        return projectPage;
    }

    public String getWarningMessageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }
}
