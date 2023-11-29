package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class OrganizationFolderRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement inputNewName;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@class='warning']")
    private WebElement warningMessage;

    public OrganizationFolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderRenamePage enterNewName(String name) {
        inputNewName.clear();
        inputNewName.sendKeys(name);

        return this;
    }

    public OrganizationFolderDetailsPage clickRenameButton() {
        renameButton.click();

        return new OrganizationFolderDetailsPage(getDriver());
    }

    public String getWarningMessageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }
}
