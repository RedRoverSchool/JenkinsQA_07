package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelinePage extends BasePage {

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'build')]")
    private WebElement buildNowButton;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowButton.click();

        return new BuildWithParametersPage(getDriver());
    }
}
