package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderMovePage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement moveButton;

    public FolderMovePage(WebDriver driver) {
        super(driver);
    }

    public FolderMovePage clickArrowDropDownMenu() {
        getDriver().findElement(By.name("destination")).click();

        return this;
    }

    public FolderMovePage clickFolderByName(String name) {
        getDriver().findElement( By.xpath("//*[contains(@value,'" + name + "')]")).click();

        return this;
    }

    public HomePage clickMove(){
        moveButton.click();

        return new HomePage(getDriver());
    }
}
