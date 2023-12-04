package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class LogInPage extends BasePage {

    @FindBy(name = "j_username")
    private WebElement userNameTextArea;

    @FindBy(name = "j_password")
    private WebElement passwordTextArea;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public LogInPage(WebDriver driver) {
        super(driver);
    }

    public HomePage inputNewCredentialsAndLogIn(String username, String password) {
        userNameTextArea.clear();
        userNameTextArea.sendKeys(username);
        passwordTextArea.clear();
        passwordTextArea.sendKeys(password);
        submitButton.click();

        return new HomePage(getDriver());
    }
}
