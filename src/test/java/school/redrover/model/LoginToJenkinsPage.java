package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class LoginToJenkinsPage extends BasePage {

    @FindBy(id = "j_username")
    private WebElement fieldUserName;

    @FindBy(id = "j_password")
    private WebElement fieldPassword;

    @FindBy(name = "Submit")
    private WebElement signInButton;

    @FindBy(xpath = "//div[contains(text(), 'Invalid')]")
    private WebElement errorMassage;

    public LoginToJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public LoginToJenkinsPage inputUserName(String userName) {
        fieldUserName.sendKeys(userName);

        return this;
    }

    public LoginToJenkinsPage inputPassword(String password) {
        fieldPassword.sendKeys(password);

        return this;
    }

    public LoginToJenkinsPage clickSignIn() {
        signInButton.click();

        return this;
    }

    public String getErrorMassage() {
        return errorMassage.getText();
    }
}

