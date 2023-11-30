package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectBuildDetailsPage extends BaseProjectPage {
    @FindBy(name = "Submit")
    private WebElement clickSubmitCancel;

    @FindBy(css = "a[href$='confirmDelete']")
    private WebElement deleteBuild;

    public FreestyleProjectBuildDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectBuildDetailsPage clickDeleteBuildSidePanel() {
        deleteBuild.click();

        return this;
    }

    public FreestyleProjectDetailsPage clickButtonDeleteBuild() {
        clickSubmitCancel.click();

        return new FreestyleProjectDetailsPage(getDriver());
    }
}
