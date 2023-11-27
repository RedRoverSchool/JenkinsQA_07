package school.redrover.model;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class FreestyleProjectRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement inputField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    @FindBy(className = "error")
    private WebElement errorMessage;

    public FreestyleProjectRenamePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectRenamePage clearInputField() {
        inputField.clear();

        return this;
    }

    public FreestyleProjectRenamePage enterName(String newProjectName) {
        inputField.sendKeys(newProjectName);

        return this;
    }

    public FreestyleProjectDetailsPage clickRenameButton() {
        renameButton.click();

        return new FreestyleProjectDetailsPage(getDriver());
    }

    public String getErrorMessage() {
        inputField.sendKeys(Keys.TAB);

        return getWait2().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public RenameErrorPage clickRenameButtonEmptyName() {
        inputField.clear();
        renameButton.click();

        return new RenameErrorPage(getDriver());
    }

    public ErrorPage clickRenameButtonAndRedirectErrorPage() {
        renameButton.click();

        return new ErrorPage(getDriver());
    }
}
