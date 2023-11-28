package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class FolderMovePage extends BasePage {
    public FolderMovePage(WebDriver driver) {
        super(driver);
    }
    public FolderMovePage clickArrowDropDownMenu() {
        getDriver().findElement(By.name("destination")).click();

        return this;
    }
    public FolderMovePage clickFolderByName(String name) {
        getDriver().findElement( By.xpath("//*[contains(@value,'" + name + "')]")).click();
        //       //*[contains(@value,'Renamed')] return page;

        return this;

    }
    public HomePage clickMove(){
        getDriver().findElement(By.name("Submit")).click();

        return new HomePage(getDriver());
    }
}
