package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleProjectBuildDetailsPage extends BasePage {
    @FindBy(name = "Submit")
    private WebElement clickSubmitCancel;

    public FreestyleProjectBuildDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectBuildDetailsPage clickDeleteBuildSidePanel() {
        getDriver().findElement(By.cssSelector("a[href$='confirmDelete']")).click();

        return this;
    }

    public FreestyleProjectDetailsPage clickButtonDeleteBuild() {
        clickSubmitCancel.click();

        return new FreestyleProjectDetailsPage(getDriver());
    }
}
