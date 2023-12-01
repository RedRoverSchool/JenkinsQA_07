package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;

public abstract class BasePage extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(name = "q")
    private WebElement searchBoxHeader;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();

        return new HomePage(getDriver());
    }

    public <T> T refreshPage(T page) {
        getDriver().navigate().refresh();

        return page;
    }

    public <T> T getSearchBox(T page, String str) {
        new Actions(getDriver())
                .keyDown(Keys.CONTROL)
                .sendKeys("k")
                .keyUp(Keys.CONTROL)
                .sendKeys(str)
                .keyDown(Keys.ENTER)
                .perform();

        return page;
    }

    public <T> T acceptAlert(T page) {
        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        return page;
    }

    public String getHeadLineText() {

        return heading.getText();
    }

    public <T> T setSearchBoxText(String searchText, T page) {
        searchBoxHeader.click();
        searchBoxHeader.sendKeys(searchText);
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();

        return page;
    }

    public WebElement getSearchBox() {
        return searchBoxHeader;
    }
}
