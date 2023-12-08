package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigurationPage;

public class MultiConfigurationConfigurePage extends BaseConfigurationPage<MultiConfigurationDetailsPage, MultiConfigurationConfigurePage> {
    @FindBy(name = "description")
    private WebElement inputDescription;

    public MultiConfigurationConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationDetailsPage createProjectPage() {
        return new MultiConfigurationDetailsPage(getDriver());
    }

    public MultiConfigurationConfigurePage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return this;
    }
}
