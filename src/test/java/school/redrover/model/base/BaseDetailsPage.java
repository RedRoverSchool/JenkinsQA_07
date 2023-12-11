package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.builds.BuildPage;
import school.redrover.model.builds.BuildWithParametersPage;

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

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement projectDescriptionInputField;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@class='jenkins-button jenkins-button--primary ']")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildPermalink;

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

    public String getDisableEnableButtonText() {
        return disableEnableButton.getText();
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().split("\n")[0].trim();
    }

    public boolean isProjectDisabled() {
        return disableMessage.isEnabled();
    }

    public boolean isProjectEnabled() {
        return getDisableEnableButtonText().equals("Disable Project");
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
        for (int i = 1; i <= numOfClicks; i++) {
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

    public Self clickSaveDescriptionButton() {
        saveDescriptionButton.click();

        return (Self) this;
    }

    public BuildPage clickLastBuild() {
        lastBuildPermalink.click();

        return new BuildPage(getDriver());
    }

    public Self clickBuildNowSeveralTimesAndWait(int numOfClicks, String projectName) {
        for (int i = 1; i <= numOfClicks; i++) {
            clickBuildNow();
            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/job/" + projectName + "/" + i + "/']")));
        }
        getDriver().navigate().refresh();

        return (Self) this;
    }

    public Self enterDescriptionText(String description) {
        projectDescriptionInputField.sendKeys(description);

        return (Self)this;
    }

    public Self clickSaveButton() {
        saveButton.click();

        return (Self)this;
    }
}
