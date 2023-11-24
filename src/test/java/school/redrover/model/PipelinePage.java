package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class PipelinePage extends BasePage {

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'build')]")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@class='build-icon']/a")
    private WebElement buildIcon;

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

    public PipelinePage clickBuildNow() {
        buildNowButton.click();

        return new PipelinePage(getDriver());
    }

    public boolean isBuildIconDisplayed() {

        return getWait2().until(ExpectedConditions.visibilityOf(buildIcon)).isDisplayed();
    }

}
