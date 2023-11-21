package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigurationPage extends BasePage {

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderConfigurationPage clickSave() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        return this;
    }

    public HomePage clickDelete() {
        getDriver().findElement(By.linkText("Delete Organization Folder"))
                .click();
        getDriver().findElement(By.xpath("//button[@name='Submit']"))
                .click();

        return new HomePage(getDriver());
    }
}
