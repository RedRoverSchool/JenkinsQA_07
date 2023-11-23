package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineRenamePage extends BasePage {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement inputName;

    @FindBy(xpath = "//button[normalize-space()='Rename']")
    private WebElement renameButton;

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public PipelineRenamePage clearInputName() {
        inputName.clear();

        return this;
    }

    public PipelineRenamePage clickRenameButton() {
        renameButton.click();

        return this;
    }
}
