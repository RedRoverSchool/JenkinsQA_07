package school.redrover.model;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class UserPage extends BasePage {

    @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//dt[text() = 'Users']")
    private WebElement manageUsers;

    @FindBy(xpath = "//*[@href='addUser']")
    private WebElement addUser;

    @FindBy(name = "Submit")
    private WebElement submitNewUser;

    @FindBy (xpath = "//tbody/tr[2]/td[2]/a[1]")
    private WebElement createdUserName;

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

    public String getCreatedUserName() {

        return getWait2().until(ExpectedConditions.visibilityOf(createdUserName)).getText();
    }

    public boolean userIdIsClickable() {

        return createdUserName.isEnabled() && createdUserName.isDisplayed();
    }
}
