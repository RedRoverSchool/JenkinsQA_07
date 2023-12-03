package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement inputField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage typeNewName(String name) {
        inputField.clear();
        inputField.sendKeys(name);

        return this;
    }

    public FolderDetailsPage clickRename() {
        renameButton.click();

        return new FolderDetailsPage(getDriver());
    }

    public <T> T clickRenameWithError(T page) {
        renameButton.click();

        return page;
    }
}
