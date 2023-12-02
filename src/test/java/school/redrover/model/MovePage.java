package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MovePage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement destinationDropdown;

    public MovePage(WebDriver driver) {
        super(driver);
    }

    public MovePage clickArrowDropDownMenu() {
        destinationDropdown.click();

        return this;
    }

    public MovePage clickFolderByName(String folderName) {
        getDriver().findElement(By.xpath("//*[contains(@value,'" + folderName + "')]")).click();

        return this;
    }

    public <ProjectPage extends BasePage> ProjectPage clickMove(ProjectPage projectPage) {
        moveButton.click();

        return projectPage;
    }
}
