package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleProjectDetailsPage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '/build?delay=0sec')]")
    private WebElement buildNowButton;

    public FreestyleProjectDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectDetailsPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public FreestyleProjectConfigurePage goToConfigureFromSideMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href = '/job/" + projectName + "/configure']")).click();
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public WorkspacePage goToWorkspaceFromSideMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/ws/']")).click();
        return new WorkspacePage(getDriver());
    }

    public FreestyleProjectRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FreestyleProjectRenamePage(getDriver());
    }
}
