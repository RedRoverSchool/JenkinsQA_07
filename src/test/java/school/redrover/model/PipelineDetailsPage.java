package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseDetailsPage;

import java.util.ArrayList;
import java.util.List;

public class PipelineDetailsPage extends BaseDetailsPage<PipelineConfigurePage, PipelineDetailsPage> {

    @FindBy(css = "textarea[name ='description']")
    private WebElement descriptionField;

    @FindBy(css = ".permalink-item")
    private List<WebElement> permalinksList;

    @FindBy(xpath = "//tbody[@class='tobsTable-body']//div[@class='duration']")
    private WebElement buildDurationInStageView;

    @FindBy(xpath = "//div[@class='btn btn-small cbwf-widget cbwf-controller-applied stage-logs']")
    private WebElement logsButtonInStageView;

    @FindBy(xpath = "//pre[@class='console-output']")
    private WebElement stageLogsModal;

    @FindBy(xpath = "//th[contains(@class, 'stage-header-name-')]")
    private List<WebElement> stagesNamesList;

    @FindBy(xpath = "//div[@class='build-icon']/a")
    private WebElement buildIcon;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]/../..")
    private WebElement deleteSideMenuOption;

    @FindBy(xpath = "//a[contains(@href, '/build?delay=0sec')]")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[contains(@href, '/1/console')]")
    private WebElement tooltipValue;

    @FindBy(xpath = "//a[contains(@href, 'lastBuild/')]")
    private WebElement lastBuildLink;

    @FindBy(xpath = "//a[contains(@data-url, '/doDelete')]")
    private WebElement deletePipelineButton;

    @FindBy(xpath = "//ul[@class = 'permalinks-list']")
    private WebElement permalinkText;

    @FindBy(css = "div#pipeline-box > div")
    private WebElement stageViewAlertText;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item'][2]//a[@class='model-link']")
    private WebElement folderBreadCrumbs;

    public PipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    public PipelineDetailsPage inputDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    public List<String> getPermalinksList() {
        List<String> permalinks = new ArrayList<>();
        for (WebElement permalink : permalinksList) {
            permalinks.add(permalink.getText().substring(0, permalink.getText().indexOf(",")));
        }

        return permalinks;
    }

    @Override
    protected PipelineConfigurePage createConfigurationPage() {
        return new PipelineConfigurePage(getDriver());
    }

    public PipelineDetailsPage clickLogsInStageView() {
        Actions actions = new Actions(getDriver());
        actions
                .moveToElement(getWait5().until(ExpectedConditions.visibilityOf(buildDurationInStageView)))
                .perform();

        getWait5().until(ExpectedConditions.visibilityOf(logsButtonInStageView)).click();

        return this;
    }

    public String getStageLogsModalText() {
        return getWait5().until(ExpectedConditions.visibilityOf(stageLogsModal)).getText();
    }

    public List<String> getStagesNames() {
        return stagesNamesList.stream().map(WebElement::getText).toList();
    }

    public boolean isBuildIconDisplayed() {

        return getWait2().until(ExpectedConditions.visibilityOf(buildIcon)).isDisplayed();
    }

    public HomePage deleteFromSideMenu() {
        deleteSideMenuOption.click();
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public PipelineDetailsPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public String getTooltipAttributeValue() {
        return tooltipValue.getAttribute("tooltip");
    }

    public BuildPage clickLastBuildLink() {
        lastBuildLink.click();

        return new BuildPage(getDriver());
    }

    public String getLastBuildLinkText() {

        return lastBuildLink.getText();
    }

    public FolderDetailsPage deletePipelineJobInsideOfFolder() {
        deletePipelineButton.click();
        getDriver().switchTo().alert().accept();

        return new FolderDetailsPage(getDriver());
    }

    public boolean isPermalinksEmpty() {
        return permalinkText.getText().isEmpty();
    }

    public String getStageViewAlertText() {

        return stageViewAlertText.getText();
    }

    public FolderDetailsPage clickFolderBreadCrumbs() {
        folderBreadCrumbs.click();
        return new FolderDetailsPage(getDriver());
    }
}
