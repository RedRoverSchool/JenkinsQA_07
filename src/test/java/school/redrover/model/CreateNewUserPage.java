package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNewUserPage extends BasePage {

    @FindBy(name = "username")
    private WebElement userName;

    @FindBy(name = "password1")
    private WebElement password;

    @FindBy(name = "password2")
    private WebElement passwordConfirm;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public CreateNewUserPage(WebDriver driver) {

        super(driver);
    }

    public  String getLabelText(String labelName){
        WebElement labelElement = getDriver().findElement(By.xpath("//div[text() = '" + labelName + "']"));
        return labelElement.getText();
    }

    public  WebElement getInputField(String labelName) {
        return getDriver().findElement(By.xpath("//div[@class='jenkins-form-label help-sibling'][text() = '"
                + labelName + "']/following-sibling::div/input"));
    }

    public CreateNewUserPage inputUserName(String userName) {
        this.userName.sendKeys(userName);

        return this;
    }

    public CreateNewUserPage inputPassword(String password) {
        this.password.sendKeys(password);

        return this;
    }

    public CreateNewUserPage inputPasswordConfirm(String password) {
        passwordConfirm.sendKeys(password);

        return this;
    }

    public CreateNewUserPage inputEmail(String email) {
        this.email.sendKeys(email);

        return this;
    }

    public UserDatabasePage clickSubmit() {
        submitButton.click();

        return new UserDatabasePage(getDriver());
    }

}

