package school.redrover.model.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;
import school.redrover.model.SignInToJenkinsPage;

import java.util.List;

import static school.redrover.runner.SeleniumUtils.jsClick;

public abstract class BasePage extends BaseModel {

    @FindBy(css = "a[href*='user']")
    private WebElement user;

    @FindBy(xpath = "//a[@id = 'jenkins-home-link']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[@href ='/logout']")
    private WebElement logout;

    @FindBy(xpath = "//button[@class='jenkins-menu-dropdown-chevron'][1]")
    private WebElement userDropdown;

    @FindAll({@FindBy(xpath = "//a[@class='jenkins-dropdown__item']")})
    public List<WebElement> dropDownItems;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        homeLink.click();

        return new HomePage(getDriver());
    }

    public boolean userNameIsDisplayed() {

        return user.isDisplayed();
    }

    public HomePage clickUserDropdown() {
        new Actions(getDriver()).moveToElement(userDropdown, 20, 0).click().perform();

        return new HomePage(getDriver());
    }

    public SignInToJenkinsPage clickLogout() {
        logout.click();

        return new SignInToJenkinsPage(getDriver());
    }

    public List<String> getDropDownItems() {

        return dropDownItems.stream().map(WebElement::getText).toList();
    }



}
