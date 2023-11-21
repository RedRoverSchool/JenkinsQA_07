package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class UserDatabasePage extends BasePage {

    @FindBy(css = "a[href = 'addUser']")
    private WebElement createUser;

    public UserDatabasePage(WebDriver driver) {
        super(driver);
    }

    public String getLoginUserName() {
        return getDriver().findElement(By.xpath("(//span[@class='hidden-xs hidden-sm'])[1]"))
                .getText();
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

    public CreateNewUserPage createUser() {
        createUser.click();

        return new CreateNewUserPage(getDriver());
    }

    public boolean isUserCreated(String userName) {
        return getDriver().findElement(By.linkText(userName)).isDisplayed();
    }
}
