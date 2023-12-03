package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class UserDatabasePage extends BasePage {

    @FindBy(css = "a[href = 'addUser']")
    private WebElement createUser;

    @FindBy(xpath = "(//span[@class='hidden-xs hidden-sm'])[1]")
    private WebElement loginUserName;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> users;

    @FindBy(xpath = "//a[contains(@class, 'link inside')]")
    private List<WebElement> userIDs;


    public UserDatabasePage(WebDriver driver) {
        super(driver);
    }

    public String getLoginUserName() {
        return loginUserName
                .getText();
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

    public CreateNewUserPage clickCreateUserButton() {
        createUser.click();

        return new CreateNewUserPage(getDriver());
    }

    public boolean isUserCreated(String userName) {
        return getDriver().findElement(By.linkText(userName)).isDisplayed();
    }

    public String getFullNameByName(String name) {
        String fullName = "";
        int trCounter = 1;

        for (WebElement user:users) {
            if (user.getText().contains(name)) {
                fullName = user.findElement(By.xpath("//tbody/tr["+ trCounter +"]/td[3]")).getText();
                break;
            } else {
                trCounter++;
            }
        }
        return fullName;
    }

    public UserPage clickUserByName(String name) {
        getDriver().findElement(By.cssSelector("a[href='user/" + name + "/']")).click();

        return new UserPage(getDriver());
    }
}
