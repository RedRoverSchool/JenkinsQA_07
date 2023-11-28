package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class PipelineDetailsPage extends BasePage {

    public PipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "description-link")
    private WebElement addDescription;

    @FindBy(css = "textarea[name ='description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//div[@id = 'description']//button[@name = 'Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div")
    private WebElement description;

    @FindBy(css = ".permalink-item")
    private List<WebElement> permalinksList;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'configure')]")
    private WebElement configureSideMenuOption;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'build')]")
    private WebElement buildNowSideMenuOption;

    @FindBy(xpath = "//tbody[@class='tobsTable-body']//div[@class='duration']")
    private WebElement buildDurationInStageView;

    @FindBy(xpath = "//span[@class='badge']/a[text()='#1']")
    private WebElement buildNumInStageView;

    @FindBy(xpath = "//div[@class='btn btn-small cbwf-widget cbwf-controller-applied stage-logs']")
    private WebElement logsButtonInStageView;

    @FindBy(xpath = "//pre[@class='console-output']")
    private WebElement stageLogsModal;

    @FindBy(xpath = "//th[contains(@class, 'stage-header-name-')]")
    private List<WebElement> stagesNamesList;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSideMenuOption;

    @FindBy(xpath = "//div[@class='build-icon']/a")
    private WebElement buildIcon;

    @FindBy(xpath = "//a[contains(@href, '/build?delay=0sec')]")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[contains(@href, '/1/console')]")
    private WebElement tooltipValue;

    @FindBy(xpath = "//a[contains(@href, 'lastBuild/')]")
    private WebElement lastBuildLink;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'replay')]")
    private WebElement replayButtonSideMenu;

    @FindBy(xpath = "//a[contains(@data-url, '/doDelete')]")
    private WebElement deletePipelineButton;

    public PipelineDetailsPage clickAddDescription() {
        addDescription.click();

        return this;
    }

    public PipelineDetailsPage inputDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    public PipelineDetailsPage clickSaveButton() {
        saveButton.click();
        getWait2().until(ExpectedConditions.visibilityOf(description));

        return this;
    }

    public String getDescription() {

        return description.getText();
    }

    public List<String> getPermalinksList() {
        List<String> permalinks = new ArrayList<>();
        for (WebElement permalink : permalinksList) {
            permalinks.add(permalink.getText().substring(0, permalink.getText().indexOf(",")));
        }

        return permalinks;
    }

    public PipelineConfigurationPage clickConfigure() {
        configureSideMenuOption.click();

        return new PipelineConfigurationPage(getDriver());
    }

    public PipelineDetailsPage clickBuildNow() {
        buildNowSideMenuOption.click();

        return this;
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        buildNowSideMenuOption.click();

        return new BuildWithParametersPage(getDriver());
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

    public PipelineRenamePage clickRenameInSideMenu() {
        renameSideMenuOption.click();

        return new PipelineRenamePage(getDriver());
    }

    public boolean isBuildIconDisplayed() {

        return getWait2().until(ExpectedConditions.visibilityOf(buildIcon)).isDisplayed();
    }

    public PipelineDetailsPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public String getTooltipAttributeValue() {
        return tooltipValue.getAttribute("tooltip");
    }

    public PipelineDetailsPage clickLastBuildLink(){
        lastBuildLink.click();

        return new PipelineDetailsPage(getDriver());
    }

    public ReplayBuildPipelinePage clickReplaySideMenu(){
        replayButtonSideMenu.click();

        return new ReplayBuildPipelinePage(getDriver());
    }

    public String getLastBuildLinkText(){

        return lastBuildLink.getText();
    }

    public FolderDetailsPage deletePipelineJobInsideOfFolder() {
        deletePipelineButton.click();
        getDriver().switchTo().alert().accept();

        return new FolderDetailsPage(getDriver());
    }
}
