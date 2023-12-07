package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePage extends BaseModel {

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(name = "q")
    private WebElement searchBoxHeader;

    @FindBy(xpath = "//li//a[@href='/']")
    private WebElement dashboardBreadCrumb;

    @FindBy(xpath = "//div[@id='breadcrumbBar']/ol/li/a")
    private List<WebElement> breadcrumbBarItemsList;

    @FindBy(className = "jenkins_ver")
    private WebElement jenkinsVersionButton;

    @FindBy(css = "a[href='/manage/about']")
    private WebElement aboutJenkinsButton;

    @FindBy(css = "a[href='https://www.jenkins.io/participate/']")
    private WebElement getInvolved;

    @FindBy(xpath = "//div[@class='tippy-box']//div//a")
    private WebElement tippyBox;

    @FindBy(xpath = "//div[@class='tippy-box']//div//a")
    private List<WebElement> tippyBoxList;

    @FindBy(css = "a[href='https://www.jenkins.io/']")
    private WebElement websiteJenkins;

    @FindBy(id = "jenkins-home-link")
    private WebElement homeLink;

    @FindBy(xpath = "//a[@href = 'api/']")
    private WebElement restApiButton;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
       homeLink.click();

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
        searchBoxHeader.sendKeys(Keys.ENTER);

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

    public WebElement getSearchBoxWebElement() {
        return searchBoxHeader;
    }

    public <T> T waitAndRefresh(T page) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("setTimeout(function(){\n" +
                "    location.reload();\n" +
                "}, 500);");

        return page;
    }

    public boolean isItemExistInBreadcrumbBar(String item) {

        return breadcrumbBarItemsList.stream().map(WebElement::getText).anyMatch(e -> e.equals(item));
    }

    public HomePage clickDashboardBreadCrumb() {
        dashboardBreadCrumb.click();

        return new HomePage(getDriver());
    }

    public String getVersionJenkinsButton() {
        return jenkinsVersionButton.getText();
    }

    public CreatedUserPage clickUserNameHeader(String userName) {
        getDriver().findElement(By.xpath("//a[@href='/user/" + userName + "']")).click();

        return new CreatedUserPage(getDriver());
    }

    public <T> T clickJenkinsVersionButton(T page) {
        jenkinsVersionButton.click();

        return page;
    }

    public WebsiteJenkinsPage goGetInvolvedWebsite() {
        jenkinsVersionButton.click();
        getInvolved.click();

        List<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        return new WebsiteJenkinsPage(getDriver());
    }

    public WebsiteJenkinsPage goWebsiteJenkins() {
        jenkinsVersionButton.click();
        websiteJenkins.click();

        List<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        return new WebsiteJenkinsPage(getDriver());
    }

    public List<String> getVersionJenkinsTippyBoxText() {
        getWait10().until(ExpectedConditions.visibilityOf(tippyBox));

        return tippyBoxList.stream().map(WebElement::getText).toList();
    }

    public AboutJenkinsPage goAboutJenkinsPage() {
        jenkinsVersionButton.click();
        aboutJenkinsButton.click();

        return new AboutJenkinsPage(getDriver());
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public RestApiPage goRestApiPage() {
        restApiButton.click();
        return new RestApiPage(getDriver());
    }
}
