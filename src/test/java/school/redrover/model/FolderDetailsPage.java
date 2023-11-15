package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class FolderDetailsPage extends BasePage {

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FolderRenamePage(getDriver());
    }

}
