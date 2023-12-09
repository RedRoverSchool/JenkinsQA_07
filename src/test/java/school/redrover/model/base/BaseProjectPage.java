package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.util.List;

public abstract class BaseProjectPage<ProjectConfigurationPage extends BaseConfigurationPage<?, ?>, Self extends BaseProjectPage<?, ?>> extends BasePage<Self> {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenu;

    @FindBy(name = "Submit")
    private WebElement disableEnableButton;

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
    private WebElement addDescription;

    @FindBy(linkText = "Status")
    private WebElement statusPageLink;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadcrumbChain;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//div[@id='breadcrumbBar']//li[3]/a")
    private WebElement breadcrumbProjectName;

    @FindBy(xpath = "//a[@class='jenkins-dropdown__item'][normalize-space()='Rename']")
    private WebElement renameInBreadcrumb;

    @FindBy(xpath = "(//div[@id='breadcrumbBar']//button)[2]")
    private WebElement arrowToOpenDropDown;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public RenamePage<Self> clickRename() {
        renameSubmenu.click();

        return new RenamePage<>(getDriver(), (Self)this);
    }

    public Self clickDisableButton() {
        disableEnableButton.click();

        return (Self)this;
    }

    public Self clickEnableButton() {
        disableEnableButton.click();

        return (Self)this;
    }

    public Self clickAddOrEditDescription() {
        addDescription.click();

        return (Self)this;
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public String getAddDescriptionButtonText() {
       return addDescription.getText();
    }

    public String getDisableButtonText() {
        return disableEnableButton.getText();
    }

    public String getEnableButtonText() {
        return disableEnableButton.getText();
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().substring(0, 46);
    }

    public <ProjectDetailsPage extends BaseProjectPage<?, ?>> ProjectDetailsPage clickBuildNow(ProjectDetailsPage projectDetailsPage) {
        buildNowSideMenuOption.click();
        getWait5().until(ExpectedConditions.visibilityOfAllElements(buildLinksInBuildHistory));

        return projectDetailsPage;
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowSideMenuOption.click();

        return new BuildWithParametersPage(getDriver());
    }

    public <ProjectDetailsPage extends BaseProjectPage<?, ?>> ProjectDetailsPage clickBuildNowSeveralTimes(ProjectDetailsPage projectDetailsPage, int numOfClicks) {
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

    public RenamePage<Self> clickRenameFromBreadcrumb() {
        Actions actions = new Actions(getDriver());

        actions
                .moveToElement(breadcrumbProjectName)
                .scrollByAmount(breadcrumbProjectName.getSize().getWidth() / 2,
                        breadcrumbProjectName.getSize().getHeight() / 2)
                .moveToElement(arrowToOpenDropDown)
                .click(arrowToOpenDropDown)
                .perform();

        actions
                .moveToElement(renameInBreadcrumb)
                .click()
                .build()
                .perform();

        return new RenamePage<>(getDriver(), (Self) this);
    }
}
