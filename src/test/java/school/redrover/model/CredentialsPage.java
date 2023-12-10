package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CredentialsPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'Delete')]/ancestor::a")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement yesButton;

    @FindBy(xpath = "//div[@id = 'main-panel']//h1")
    private WebElement globalCredentialsText;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    public CredentialsPage clickCredentialsByName(String credentialName) {
        getDriver().findElement(By.xpath("//a[contains(text(), '" + credentialName + "')]")).click();
        return this;
    }

    public CredentialsPage clickDeleteButton() {
        deleteButton.click();
        return this;
    }

    public CredentialsPage clickYesButton() {
        yesButton.click();
        return this;
    }

    public String getTextMainPanel() {
        return globalCredentialsText.getText();
    }
}
