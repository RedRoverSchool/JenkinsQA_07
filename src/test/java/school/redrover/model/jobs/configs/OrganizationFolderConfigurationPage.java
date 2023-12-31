package school.redrover.model.jobs.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.jobs.details.OrganizationFolderDetailsPage;
import school.redrover.model.base.BaseConfigurationPage;

public class OrganizationFolderConfigurationPage extends BaseConfigurationPage<OrganizationFolderDetailsPage, OrganizationFolderConfigurationPage> {

    @FindBy(xpath = "//section[2]//label")
    private WebElement periodicallyCheckbox;

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderDetailsPage createProjectPage() {
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
