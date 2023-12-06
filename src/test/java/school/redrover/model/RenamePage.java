package school.redrover.model;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseConfigurationPage;
import school.redrover.model.base.BaseProjectPage;

public class RenamePage <ProjectPage extends BaseProjectPage<?, ?>> extends BaseProjectPage<BaseConfigurationPage<?, ?>, RenamePage<?>> {

    @FindBy(name = "newName")
    private WebElement inputNewName;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@class='warning']")
    private WebElement warningMessage;

    @FindBy(className = "error")
    private WebElement errorMessage;

       ProjectPage projectPage;

    public RenamePage(WebDriver driver, ProjectPage projectPage) {
        super(driver);
        this.projectPage = projectPage;
    }

    public RenamePage<ProjectPage> clearInputField() {
        inputNewName.clear();

        return this;
    }

    public RenamePage<ProjectPage> enterNewName(String name) {
        inputNewName.clear();
        inputNewName.sendKeys(name);

        return this;
    }

    public RenamePage<ProjectPage> addCharsToExistingName(String chars) {
        inputNewName.sendKeys(chars);

        return this;
    }

    public ProjectPage clickRenameButton() {
        renameButton.click();

        return projectPage;
    }

    public RenameErrorPage clickRenameWithError() {
        renameButton.click();

        return new RenameErrorPage(getDriver());
    }

    public String getWarningMessageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    public RenamePage<ProjectPage> enterName(String newProjectName) {
        inputNewName.clear();
        inputNewName.sendKeys(newProjectName);

        return this;
    }

    public String getErrorMessage() {
        inputNewName.sendKeys(Keys.TAB);

        return getWait2().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public RenameErrorPage clickRenameButtonEmptyName() {
        inputNewName.clear();
        renameButton.click();

        return new RenameErrorPage(getDriver());
    }

    public RenamePage<?> clickBlank() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(inputNewName).moveByOffset(100, 50).click().build().perform();

        return this;
    }

    @Override
    protected BaseConfigurationPage<?,?> createConfigurationPage() {
        return null;
    }
}
