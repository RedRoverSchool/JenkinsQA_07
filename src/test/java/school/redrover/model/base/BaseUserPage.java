package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.*;

public abstract class BaseUserPage extends BasePage{
    @FindBy(css = "a[href$='builds']")
    private WebElement buildSidePanelButton;

    @FindBy(css = "a[href='api/']")
    private WebElement restApiButton;

    @FindBy(css = "a[href$='configure")
    private WebElement configureSidePanelButton;

    @FindBy(css = "a[href$='my-views']")
    private WebElement myViewsSidePanelButton;

    @FindBy(css = "a[href$='credentials']")
    private WebElement credentialsSidePanelButton;

    public BaseUserPage(WebDriver driver) {
        super(driver);
    }

    public RestApiPage goRestApiPage() {
        restApiButton.click();

        return new RestApiPage(getDriver());
    }

    public UserBuildPage goBuildPage() {
        buildSidePanelButton.click();

        return new UserBuildPage(getDriver());
    }

    public UserConfigurationPage goConfigurePage() {
        configureSidePanelButton.click();

        return new UserConfigurationPage(getDriver());
    }

    public MyViewPage goMyViewPage() {
        myViewsSidePanelButton.click();

        return new MyViewPage(getDriver());
    }

    public CredentialsPage goCredentialsPage() {
        credentialsSidePanelButton.click();

        return new CredentialsPage(getDriver());
    }
}


