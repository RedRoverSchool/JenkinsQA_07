package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SignInToJenkinsPage extends BasePage{

    @FindBy(xpath = "//input[@name='j_username']")
    private WebElement inputNameField;

    @FindBy(xpath = "//input[@name='j_password']")
    private WebElement inputPasswordField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public SignInToJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public HomePage logInToJenkins(String username, String password) {
        inputNameField.sendKeys(username);
        inputPasswordField.sendKeys(password);
        submitButton.click();

        return new HomePage(getDriver());
    }
}
