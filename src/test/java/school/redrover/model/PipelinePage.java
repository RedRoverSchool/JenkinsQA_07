package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelinePage extends BasePage {

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'build')]")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameButton;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowButton.click();

        return new BuildWithParametersPage(getDriver());
    }

    public PipelineRenamePage clickRenameOnSideMenu() {
        renameButton.click();

        return new PipelineRenamePage(getDriver());
    }

    public PipelineRenamePage clickRename() {
        renameButton.click();

        return new PipelineRenamePage(getDriver());
    }
}
