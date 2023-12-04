package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigurationPage;

public class MultiConfigurationConfigurePage extends BaseConfigurationPage {
    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(name = "Submit")
    private WebElement buttonSubmit;

    public MultiConfigurationConfigurePage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationConfigurePage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return this;
    }

    public MultiConfigurationDetailsPage buttonSubmit() {
        buttonSubmit.click();

        return new MultiConfigurationDetailsPage(getDriver());
    }
}
