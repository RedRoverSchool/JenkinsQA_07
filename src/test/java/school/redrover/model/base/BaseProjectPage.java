package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.util.List;

public abstract class BaseProjectPage<ProjectConfigurationPage extends BaseConfigurationPage<?>> extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameInMenu;

    @FindBy(name = "Submit")
    private WebElement disableButton;

    @FindBy(id = "enable-project")
    private WebElement disableMessage;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'build')]")
    private WebElement buildNowSideMenuOption;

    @FindBy(xpath = "//a[@class='model-link inside build-link display-name']")
    private List<WebElement> buildLinksInBuildHistory;

    @FindBy(xpath = "//span[@class='build-status-icon__outer']")
    private WebElement buildIconInBuildHistory;

    @FindBy(linkText = "Configure")
    private WebElement configureSideMenuItem;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'move')]")
    private WebElement moveSideMenuOption;

    @FindBy(id = "description-link")
    protected WebElement addDescription;

    @FindBy(linkText = "Status")
    private WebElement statusPageLink;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadcrumbChain;

    @FindBy(xpath = "//div[@id='breadcrumbBar']//li[3]/a")
    private WebElement breadcrumbBar;

    @FindBy(xpath = "//li[3]/a/button")
    private WebElement breadcrumbArrow;



    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public <ProjectPage extends BaseProjectPage<?>> RenamePage<?> clickRename(ProjectPage projectPage) {
        renameInMenu.click();

        return new RenamePage<>(getDriver(), projectPage);
    }

    public BaseProjectPage<?> clickDisableButton() {
        disableButton.click();

        return this;
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().substring(0, 46);
    }

    public <ProjectDetailsPage extends BaseProjectPage> ProjectDetailsPage clickBuildNow(ProjectDetailsPage projectDetailsPage) {
        buildNowSideMenuOption.click();
        getWait5().until(ExpectedConditions.visibilityOfAllElements(buildLinksInBuildHistory));

        return projectDetailsPage;
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowSideMenuOption.click();

        return new BuildWithParametersPage(getDriver());
    }

    public <ProjectDetailsPage extends BaseProjectPage> ProjectDetailsPage clickBuildNowSeveralTimes(ProjectDetailsPage projectDetailsPage, int numOfClicks) {
        for (int i = 0; i < numOfClicks; i++) {
            clickBuildNow(projectDetailsPage);
        }

        return projectDetailsPage;
    }

    public List<String> getBuildsInBuildHistoryList() {

        return buildLinksInBuildHistory.stream().map(WebElement::getText).toList();
    }

    protected abstract ProjectConfigurationPage createConfigurationPage();

    public ProjectConfigurationPage clickConfigure() {
        configureSideMenuItem.click();

        return createConfigurationPage();
    }

    public BuildPage clickBuildIconInBuildHistory() {
        buildIconInBuildHistory.click();

        return new BuildPage(getDriver());
    }

    public MovePage clickMove() {
        moveSideMenuOption.click();

        return new MovePage(getDriver());
    }

    public boolean isStatusPageSelected() {
        return statusPageLink.getAttribute("class").contains("active");
    }

    public BaseProjectPage<?> clickProjectBreadcrumbDropDownMenu(){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(breadcrumbBar)
                .moveToElement(breadcrumbArrow)
                .click()
                .perform();

        return this;
    }
}
