package school.redrover.model.users;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

import static org.testng.Assert.assertFalse;

public class UserDatabasePage extends BasePage<UserDatabasePage> {

    @FindBy(css = "a[href = 'addUser']")
    private WebElement addUserButton;

    @FindBy(xpath = "(//span[@class='hidden-xs hidden-sm'])[1]")
    private WebElement loginUserName;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> users;

    @FindBy(xpath = "//a[contains(@class, 'link inside')]")
    private List<WebElement> userIDs;


    @FindBy(xpath = "//tr/td[5]//a")
    private List<WebElement> deleteIcon;

    @FindBy(xpath = "//tr//td[4]//a")
    private List<WebElement> configureIcon;


    public UserDatabasePage clickDeleteIcon (int n) {
        deleteIcon.get(n).click();
        return this;
    }

    public UserConfigurationPage clickConfigureIcon (int n) {
        configureIcon.get(n).click();
        return new UserConfigurationPage(getDriver());
    }


    public UserDatabasePage(WebDriver driver) {
        super(driver);
    }

    public String getLoginUserName() {
        return loginUserName
                .getText();
    }

    public String getUserFullName(String username) {
        return getDriver().findElement(By.xpath("(//td/a[@href='user/" + username + "/']/following::td[1])")).getText();
    }

    public String getUserID(int n) {
        return userIDs.get(n).getText();
    }

    public boolean deleteLoggedUser() {
        boolean doDelete = true;
        String logUsername = getLoginUserName();
        try {
            getDriver().findElement(
                    By.xpath("//tr[.//td[contains(text(), '" + logUsername + "')]]/td[last()]/*"));
        } catch (Exception e) {
            doDelete = false;
        }
        return doDelete;
    }

    public CreateNewUserPage clickAddUserButton() {
        addUserButton.click();

        return new CreateNewUserPage(getDriver());
    }

    public boolean isUserCreated(String userName) {
        return getDriver().findElement(By.linkText(userName)).isDisplayed();
    }

    public String getFullNameByName(String name) {
        String fullName = "";
        int trCounter = 1;

        for (WebElement user : users) {
            if (user.getText().contains(name)) {
                fullName = user.findElement(By.xpath("//tbody/tr[" + trCounter + "]/td[3]")).getText();
                break;
            } else {
                trCounter++;
            }
        }
        return fullName;
    }

    public CreatedUserPage clickUserByName(String name) {
        getDriver().findElement(By.cssSelector("a[href='user/" + name.toLowerCase() + "/']")).click();

        return new CreatedUserPage(getDriver());
    }


    public boolean listOfUserIDsContainsName(String name) {
        assertFalse(userIDs.isEmpty(), "List of UserIDs is empty");

        boolean isNewUserIDPresent = false;
        for (WebElement webElement : userIDs) {
            if (webElement.getText().contains(name)) {
                isNewUserIDPresent = true;
                break;
            }
        }
        return isNewUserIDPresent;
    }
}
