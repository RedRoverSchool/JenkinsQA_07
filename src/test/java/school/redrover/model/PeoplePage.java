package school.redrover.model;

import org.bouncycastle.est.LimitedSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class PeoplePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(xpath ="//a[@href='/view/all/builds']" )
    private WebElement buildHistory;

    @FindBy(xpath = "//a[@href='/manage'] ")
    private WebElement manageJenkins;

    @FindBy(xpath = "//a[@href='/me/my-views']")
    private WebElement myViews;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//div[@class='searchbox hidden-xs']")
    private WebElement searchField;

    @FindBy(xpath = "//div[@id='visible-am-insertion']")
    private WebElement bell;

    @FindBy(xpath = "//div[@id='visible-sec-am-insertion']")
    private WebElement shield;

    @FindBy(xpath = "//a[@href='/user/admin']")
    private WebElement admin;

    @FindBy(css = ".jenkins-menu-dropdown-chevron")
    private WebElement dropDownMenuUser;

    @FindBy(xpath = "//a[@href='https://www.jenkins.io/redirect/search-box']")
    private WebElement questionMark;

    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logOut;

    @FindBy(xpath = "//a[@href='/iconSize?16x16']")
    private WebElement iconSmall;

    @FindBy(xpath = "//a[@href='/iconSize?24x24']")
    private WebElement iconMedium;

    @FindBy(css =".jenkins-icon-size__items-item")
    private WebElement iconLarge;

    @FindBy(xpath = "//a[@href='api/']")
    private WebElement restApi;

    @FindBy (xpath = "//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")
    private WebElement jenkinsMenu;

    public PeoplePage(WebDriver driver) { super(driver); }

    public <T> T clickUserName(String name, T page) {
        getDriver().findElement(By.xpath("//a[@href='/user/" + name)).click();
        return page;
    }

    public NewItemPage clickNewItem() {
        newItem.click();
        return new NewItemPage(getDriver());
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistory.click();
        return new BuildHistoryPage(getDriver());
    }

    public ManageJenkinsPage clickManageJenkins() {
        manageJenkins.click();
        return new ManageJenkinsPage(getDriver());
    }

    public MyViewPage clickMyViews() {
        myViews.click();
        return new MyViewPage(getDriver());
    }

    public NodesListPage clickBuildExecutorStatus() {
        buildExecutorStatus.click();
        return new NodesListPage(getDriver());
    }

    public PeoplePage clickBell() {
        bell.click();
        return this;
    }

    public PeoplePage clickShield() {
        shield.click();
        return this;
    }

    public PeoplePage clickQuestionMark() {
        questionMark.click();
        return this;
    }

    public PeoplePage clickIconSmall() {
        iconSmall.click();
        return this;
    }

    public PeoplePage clickIconMedium() {
        iconMedium.click();
        return this;
    }

    public PeoplePage clickIconLarge() {
        iconLarge.click();
        return this;
    }

    public List <String> getPeopleList() {
        List<WebElement> elementList = getDriver().findElements(By.cssSelector(".jenkins-table__link"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

//    public List<WebElement> getIcon() {
//        List<WebElement> buttonList = (List<WebElement>) getDriver().findElement(By.xpath("//div[@class='jenkins-table__cell__button-wrapper']"));
//        List<String> result = buttonList.stream().map(WebElement::getText).toList();
//        return result;
//    }
}
