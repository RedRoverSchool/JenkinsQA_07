package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(xpath = "//li//a[@href='/']")
    private WebElement dashboard;

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

    public <T> T acceptAlert(T page) {
        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

        return page;
    }

    public String getHeadLineText() {

        return heading.getText();
    }

    public <T> T goSearchBox(String searchText, T page) {
        searchBoxHeader.click();
        searchBoxHeader.sendKeys(searchText);

        new Actions(getDriver())
                .sendKeys(Keys.ENTER)
                .perform();

        return page;
    }

    public <T> T getHotKeysFocusSearch(T page) {
        new Actions(getDriver())
                .keyDown(Keys.CONTROL)
                .sendKeys("k")
                .keyUp(Keys.CONTROL)
                .perform();

        return page;
    }

    public WebElement getSearchBox() {

        return searchBoxHeader;
    }

    public <T> T waitAndRefresh(T page) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("setTimeout(function(){\n" +
                "    location.reload();\n" +
                "}, 500);");

        return page;
    }

    public HomePage clickDashboardBreadCrumb() {
        dashboard.click();

        return new HomePage(getDriver());
    }
}
