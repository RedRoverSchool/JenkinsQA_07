package school.redrover.model.users;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.CredentialsPage;
import school.redrover.model.base.BasePage;

public class UserPage extends BasePage<UserPage> {

    @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//dt[text() = 'Users']")
    private WebElement manageUsers;

    @FindBy(xpath = "//*[@href='addUser']")
    private WebElement addUser;

    @FindBy(name = "Submit")
    private WebElement submitNewUser;

    @FindBy(xpath = "//a[contains(@href,'/credentials')]")
    private WebElement credentials;

    @FindBy(id = "description-link")
    private WebElement editDescriptionButton;

    @FindBy(name = "description")
    private WebElement userDescriptionInput;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement descriptionText;


    public UserPage(WebDriver driver) { super(driver); }

    public UserPage goToUserCreateFormPage() {
        manageJenkins.click();
        manageUsers.click();
        addUser.click();

        return new UserPage(getDriver());
    }

    public UserPage createUserSuccess(String userName) {
        goToUserCreateFormPage();

        List<WebElement> valueInputs = getDriver().findElements(
            By.xpath("//*[@class = 'jenkins-input']"));
        for (int i = 0; i < valueInputs.size(); i++) {
            if (i == 0) {
                valueInputs.get(i).sendKeys(userName);
            } else {
                valueInputs.get(i).sendKeys(userName + "@" + userName + ".com");
            }
        }
        submitNewUser.click();

        return new UserPage(getDriver());
    }

    public boolean userIdIsClickable() {
        List<WebElement> userNameList = getDriver().findElements(By.xpath("//tr/td/a"));
        for (WebElement element : userNameList) {
            if (userNameList().isEmpty()) {
                return false;
            }
            if (!element.isDisplayed() && element.isEnabled()) {
                return false;
            } else if (element.isDisplayed() && element.isEnabled()) {
                return true;
            }
        }

        return userIdIsClickable();
    }

    public List<String> userNameList() {
        List<WebElement> userNameList = getDriver().findElements(By.xpath("//tr/td/a"));

        return userNameList.stream().map(WebElement::getText).toList();
    }


    public CredentialsPage clickCredentials() {
        credentials.click();

        return new CredentialsPage(getDriver());
    }

    public UserPage clickEditDescription() {
        editDescriptionButton.click();

        return this;
    }

    public UserPage addDescription(String description) {
        userDescriptionInput.clear();
        userDescriptionInput.sendKeys(description);
        saveButton.click();

        return this;
    }

    public String getText() {
        return descriptionText.getText();
    }
}
