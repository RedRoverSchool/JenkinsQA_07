package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderDetailsPage extends BaseProjectPage {

    public OrganizationFolderDetailsPage(WebDriver driver) {
        super(driver);
    }


    public HomePage clickDelete() {
        getDriver().findElement(By.linkText("Delete Organization Folder"))
                .click();
        getDriver().findElement(By.xpath("//button[@name='Submit']"))
                .click();

        return new HomePage(getDriver());
    }
}
