package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineRenamePage extends BasePage {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement inputName;

    @FindBy(xpath = "//button[normalize-space()='Rename']")
    @FindBy(name = "newName")
    private WebElement inputName;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public PipelineRenamePage enterNewName(String newProjectName) {
        inputName.clear();
        inputName.sendKeys(newProjectName);
    public PipelineRenamePage clearInputName() {
        inputName.clear();

        return this;
    }

    public PipelineRenamePage clickRenameButton() {
        renameButton.click();

        return this;
    }

    public PipelinePage clickRenameButton() {
        renameButton.click();

        return new PipelinePage(getDriver());
    }
}
