package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineRenamePage extends BasePage {

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

        return this;
    }

    public PipelinePage clickRenameButton() {
        renameButton.click();

        return new PipelinePage(getDriver());
    }
}
