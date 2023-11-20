package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='computer/new']")
    private WebElement setUpAnAgent;

    @FindBy(xpath = "//span[contains(text(),'Build History')]/parent::a")
    public WebElement buildHistoryButton;

    @FindBy(xpath = "//div[@id='main-panel']//a[@href='newJob']")
    private WebElement createAJob;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//*[@id='job_" + name + "']/td[3]/a")).click();

        return page;
    }

    public ManageJenkinsPage clickManageJenkins() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        return new ManageJenkinsPage(getDriver());
    }

    public List<String> getJobList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//tr/td/a[contains(@class, 'jenkins-table__link')]/span[1]"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public NodeCreatePage clickSetUpAnAgent() {
        setUpAnAgent.click();

        return new NodeCreatePage(getDriver());
    }

    public BuildHistoryPage clickBuildHistoryButton() {
        buildHistoryButton.click();

        return new BuildHistoryPage(getDriver());
    }

    public NewViewPage clickNewViewButton() {
        getDriver().findElement(By.xpath("//a[@tooltip='New View']")).click();
        return new NewViewPage(getDriver());
    }

    public NewItemPage clickCreateAJob() {
        createAJob.click();

        return new NewItemPage(getDriver());
    }

    public GlobalViewPage clickViewByName(String viewName) {
        getDriver().findElement(By.xpath("//a[contains(text(),'" + viewName + "')]")).click();

        return new GlobalViewPage(getDriver());
    }

    public List<String> getViewsList() {
        List<WebElement> viewsList = getDriver().findElements(By.xpath("//div[@class='tabBar']/div"));
        List<String> resultList = new ArrayList<>();
        for (WebElement el : viewsList) {
            resultList.add(el.getText());
        }

        return resultList;
    }

    public NodesListPage goNodesListPage() {
        buildExecutorStatus.click();

        return new NodesListPage(getDriver());
    }

    public HomePage clickJobDropdownMenu(String name) {
        WebElement elementToHover = getDriver().findElement(By.xpath("//a[@href='job/" + name + "/']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHover).perform();
        elementToHover.click();

        return this;
    }

    public MultibranchPipelineRenamePage clickRenameDropdownMenu(String name) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + name + "/confirm-rename']")).click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public boolean isProjectExist(String projectName) {
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    public String getJobDisplayName(String name) {
        return getDriver().findElement(By.xpath("//*[@id='job_" + name + "']/td[3]/a/span")).getText();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }


    public String getProjectBuildStatusByName(String projectName) {
        return getDriver()
                .findElement(By.id("job_" + projectName))
                .findElement(By.className("svg-icon"))
                .getAttribute("tooltip");
    }

    public String getHeadLineText() {
        return getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();
    }
  
    public <T> T clickAnyJobCreated(T page) {
        getDriver().findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']")).click();

        return page;
    }

    public boolean isAlertVisible() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public HomePage clickAlertIfVisibleAndGoHomePage() {
        if (isAlertVisible()) {
            getDriver().switchTo().alert().accept();
        }
        return this;
    }

    public HomePage clickBuildByGreenArrow(String name) {
        getDriver().findElement(By.xpath("//a[@href='job/" + name + "/build?delay=0sec']")).click();

        return this;
    }

}
