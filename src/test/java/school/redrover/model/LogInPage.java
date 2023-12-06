package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class LogInPage extends BasePage<LogInPage> {

    @FindBy(name = "j_username")
    private WebElement userNameTextArea;

    @FindBy(name = "j_password")
    private WebElement passwordTextArea;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(css = ".app-sign-in-register__error")
    private WebElement errorMessage;

    public LogInPage(WebDriver driver) {
        super(driver);
    }

    public HomePage logIn(String username, String password) {
        userNameTextArea.clear();
        userNameTextArea.sendKeys(username);
        passwordTextArea.clear();
        passwordTextArea.sendKeys(password);
        submitButton.click();

        return new HomePage(getDriver());
    }

    public LogInPage logInWithError(String username, String password) {
        userNameTextArea.clear();
        userNameTextArea.sendKeys(username);
        passwordTextArea.clear();
        passwordTextArea.sendKeys(password);
        submitButton.click();

        return this;
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

}
