package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeMarkOfflinePage extends BasePage {

    @FindBy(xpath = "//textarea[@name = 'offlineMessage']")
    private WebElement messageInputField;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveChangesButton;

    @FindBy(name = "Submit")
    private WebElement markThisNodeTemporarilyOfflineButton;

    public NodeMarkOfflinePage(WebDriver driver) {
        super(driver);
    }

    public NodeDetailsPage saveChanges() {
        saveChangesButton.click();

        return new NodeDetailsPage(getDriver());
    }

    public NodeDetailsPage takingNewNodeOffline(String reasonMessage) {
       messageInputField.sendKeys(reasonMessage);
       markThisNodeTemporarilyOfflineButton.click();

       return new NodeDetailsPage(getDriver());
    }
}
