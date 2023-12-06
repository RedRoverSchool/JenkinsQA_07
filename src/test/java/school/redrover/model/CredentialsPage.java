package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CredentialsPage extends BasePage<CredentialsPage> {
    @FindBy(css = "a[href='api/']")
    private WebElement restApiButton;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    public RestApiPage goRestApiPage() {
        restApiButton.click();

        return new RestApiPage(getDriver());
    }
}
