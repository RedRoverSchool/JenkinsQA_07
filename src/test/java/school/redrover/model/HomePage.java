package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='computer/new']")
    private WebElement setUpAgent;

    @FindBy(xpath = "//span[contains(text(),'Build History')]/parent::a")
    private WebElement buildHistoryButton;

    @FindBy(xpath = "//div[@id='main-panel']//a[@href='newJob']")
    private WebElement createJob;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameOptionProjectDropdown;

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItemButton;

    @FindBy(xpath = "//div[@class = 'jenkins-table__cell__button-wrapper']/a[contains(@aria-describedby,'tippy')]")
    private WebElement runningBuildIndicator;

    @FindBy(xpath = "//td//a[@href]/span")
    private WebElement multibranchPipelineNameOnHomePage;

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement buttonPeople;

    @FindBy(className = "jenkins_ver")
    private WebElement jenkinsVersionButton;

    @FindBy(xpath = "//td[@class='pane pane-grow']")
    private WebElement buildQueueSection;

    @FindBy(xpath = "//span[contains(text(),'My Views')]/parent::a")
    private WebElement myView;

    @FindBy(xpath = "//table[@id='projectstatus']//td[3]/a")
    private WebElement itemNameInTable;

    @FindBy(xpath = "//h1")
    private WebElement header;

    @FindBy(xpath = "//a[@href = '/manage']")
    private WebElement goManageJenkinsPage;

    @FindBy(css = "a[href='/manage/about']")
    private WebElement aboutJenkinsButton;

    @FindBy(className = "tippy-content")
    private WebElement jenkinsVersionTippyBox;

    @FindBy(css = "a[href='https://www.jenkins.io/participate/']")
    private WebElement getInvolved;

    @FindBy(css = "a[href='https://www.jenkins.io/']")
    private WebElement websiteJenkins;

    @FindBy(css = "a[href='api/']")
    private WebElement restApiButton;

    @FindBy(xpath = "//div[@class='tippy-box']//div//a")
    private WebElement tippyBox;

    @FindBy(className = "addTab")
    private WebElement newViewButton;

    @FindBy(xpath = "//span[contains(text(), 'log out')]")
    private WebElement logOutButton;

    @FindBy(xpath = "//a[contains(@href,'user')]")
    private WebElement currentUserName;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public <T extends BaseProjectPage> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();

        return page;
    }

    public ManageJenkinsPage clickManageJenkins() {
        goManageJenkinsPage.click();

        return new ManageJenkinsPage(getDriver());
    }

    public List<String> getJobList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//tr/td/a[contains(@class, 'jenkins-table__link')]/span[1]"));

        return elementList.stream().map(WebElement::getText).toList();
    }

    public NewItemPage clickNewItem() {
        newItemButton.click();

        return new NewItemPage(getDriver());
    }

    public NodeCreatePage clickSetUpAnAgent() {
        setUpAgent.click();

        return new NodeCreatePage(getDriver());
    }

    public BuildHistoryPage clickBuildHistoryButton() {
        buildHistoryButton.click();

        return new BuildHistoryPage(getDriver());
    }

    public NewItemPage clickCreateAJob() {
        createJob.click();

        return new NewItemPage(getDriver());
    }

    public <T> T clickViewByName(String viewName, T page) {
        getDriver().findElement(By.xpath("//a[contains(text(),'" + viewName + "')]")).click();

        return page;
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

    public HomePage clickJobNameDropdown(String name) {
        WebElement elementToHover = getDriver().findElement(By.xpath("//a[@href='job/" + name + "/']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHover).perform();
        elementToHover.click();

        return this;
    }

    public MultibranchPipelineRenamePage clickRenameDropdownMenu(String name) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + name.replace(" ", "%20") + "/confirm-rename']")).click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public boolean isProjectExist(String projectName) {
        return !getDriver().findElements(By.id("job_" + projectName)).isEmpty();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public String getProjectBuildStatusByName(String projectName) {
        return getDriver().findElement(By.id("job_" + projectName)).findElement(By.className("svg-icon")).getAttribute("tooltip");
    }

    public HomePage clickBuildByGreenArrow(String name) {
        getDriver().findElement(By.xpath("//a[@href='job/" + name.replace(" ", "%20") + "/build?delay=0sec']")).click();

        return this;
    }

    public HomePage clickBuildByGreenArrowWithWait(String name) {
        getDriver().findElement(By.xpath("//a[@href='job/" + name.replace(" ", "%20") + "/build?delay=0sec']")).click();
        getWait5().until(ExpectedConditions.invisibilityOf(runningBuildIndicator));

        return this;
    }

    public <ProjectRenamePage extends RenamePage> ProjectRenamePage clickRenameOrganizationFolderDropdownMenu(String jobName, ProjectRenamePage projectRenamePage) {
        WebElement projectName = getDriver().findElement(By.xpath("//span[text()='" + jobName + "']"));

        new Actions(getDriver()).moveToElement(projectName).click().perform();
        renameOptionProjectDropdown.click();

        return projectRenamePage;
    }

    public <T> T clickRenameInDropdownMenu(String jobName, T page) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//span[contains(text(),'" + jobName + "')]"))).perform();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='job/" + jobName.replace(" ", "%20") + "/']/button")));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//a[@href='job/" + jobName.replace(" ", "%20") + "/']/button"))).click().perform();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/job/" + jobName.replace(" ", "%20") + "/confirm-rename']"))).click();

        return page;
    }

    public String getMultibranchPipelineName() {

        return multibranchPipelineNameOnHomePage.getText();
    }

    public PeoplePage clickPeople() {
        buttonPeople.click();

        return new PeoplePage(getDriver());
    }

    public String getVersion() {
        return jenkinsVersionButton.getText();
    }

    public boolean isJobInBuildQueue(String jobName) {
        return getWait10().until(ExpectedConditions.visibilityOf(buildQueueSection)).getText().contains(jobName);
    }

    public MyViewPage clickMyView() {
        getWait2().until(ExpectedConditions.elementToBeClickable(myView)).click();
        return new MyViewPage(getDriver());
    }

    public String getItemNameInTable() {
        return itemNameInTable.getText();
    }


    public NodeDetailsPage clickOnNodeName(String nodeName) {
        getDriver().findElement(By.xpath("//span[text()='" + nodeName + "']")).click();

        return new NodeDetailsPage(getDriver());
    }

    public boolean isScheduleABuildButtonNotDisplayed(String jobName) {
        return getDriver().findElements(By.xpath("//*[@id='job_" + jobName.replace(" ", "%20") + "']//*[@class='jenkins-table__cell--tight']//a")).isEmpty();
    }

    public HomePage clickJenkinsVersion() {
        jenkinsVersionButton.click();

        return this;
    }

    public AboutJenkinsPage clickAboutJenkins() {
        aboutJenkinsButton.click();

        return new AboutJenkinsPage(getDriver());
    }

    public WebsiteJenkinsIOPage clickGetInvolved() {
        getInvolved.click();

        ArrayList<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        return new WebsiteJenkinsIOPage(getDriver());
    }

    public WebsiteJenkinsIOPage clickWebsite() {
        websiteJenkins.click();

        ArrayList<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        return new WebsiteJenkinsIOPage(getDriver());
    }

    public CreatedUserPage clickUserNameHeader(String userName) {
        getDriver().findElement(By.xpath("//a[@href='/user/" + userName + "']")).click();

        return new CreatedUserPage(getDriver());
    }

    public List<String> getVersionJenkinsTippyBoxText() {
        getWait10().until(ExpectedConditions.visibilityOf(tippyBox));

        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@class='tippy-box']//div//a"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public AboutJenkinsPage moveAboutJenkinsPage() {
        jenkinsVersionButton.click();
        aboutJenkinsButton.click();

        return new AboutJenkinsPage(getDriver());
    }

    public RestAPIPage clickRestApiButton() {
        restApiButton.click();

        return new RestAPIPage(getDriver());
    }

    public NewViewPage clickNewViewButton() {
        newViewButton.click();

        return new NewViewPage(getDriver());
    }

    public LogInPage clickLogOut() {
        logOutButton.click();

        return new LogInPage(getDriver());
    }

    public String getCurrentUserName() {
        return currentUserName.getText();
    }
}
