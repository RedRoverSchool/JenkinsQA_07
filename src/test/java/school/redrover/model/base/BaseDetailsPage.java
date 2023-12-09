package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.util.List;

public abstract class BaseDetailsPage<ProjectConfigurationPage extends BaseConfigurationPage<?, ?>, Self extends BaseDetailsPage<?, ?>> extends BasePage<Self> {

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

    public BaseDetailsPage(WebDriver driver) {
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

    public Self clickBuildNow() {
        buildNowSideMenuOption.click();
        getWait5().until(ExpectedConditions.visibilityOfAllElements(buildLinksInBuildHistory));

        return (Self)this;
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowSideMenuOption.click();

        return new BuildWithParametersPage(getDriver());
    }

    public Self clickBuildNowSeveralTimes(int numOfClicks) {
        for (int i = 0; i < numOfClicks; i++) {
            clickBuildNow();
        }

        return (Self) this;
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
}
