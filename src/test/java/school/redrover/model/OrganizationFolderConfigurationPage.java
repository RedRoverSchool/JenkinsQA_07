package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigurationPage extends BasePage {
    @FindBy(name = "Submit")
    private WebElement buttonSubmit;

    @FindBy(xpath = "//section[2]//label")
    private WebElement periodicallyCheckbox;

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderDetailsPage clickSave() {
        buttonSubmit.click();
        return new OrganizationFolderDetailsPage(getDriver());
    }

    public OrganizationFolderConfigurationPage clickPeriodicallyCheckbox() {
        new Actions(getDriver())
                .moveToElement(periodicallyCheckbox)
                .click()
                .perform();

        return this;
    }
}
