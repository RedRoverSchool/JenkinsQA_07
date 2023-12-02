package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.BuildPage;
import school.redrover.model.RenamePage;

import java.util.List;

public abstract class BaseProjectPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenu;

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
    private WebElement configureSideMenuOption;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public <ProjectRenamePage extends RenamePage> ProjectRenamePage clickRenameOption(ProjectRenamePage projectRenamePage) {
        renameSubmenu.click();

        return projectRenamePage;
    }

    public BaseProjectPage clickDisableButton() {
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

    public <ProjectDetailsPage extends BaseProjectPage> ProjectDetailsPage clickBuildNowSeveralTimes(ProjectDetailsPage projectDetailsPage, int numOfClicks) {
        for (int i = 0; i < numOfClicks; i++) {
            clickBuildNow(projectDetailsPage);
        }

        return projectDetailsPage;
    }

    public List<String> getBuildsInBuildHistoryList() {

        return buildLinksInBuildHistory.stream().map(WebElement::getText).toList();
    }

    public <ProjectConfigurationPage extends BaseConfigurationPage> ProjectConfigurationPage clickConfigure(ProjectConfigurationPage projectConfigurationPage) {
        configureSideMenuOption.click();

        return projectConfigurationPage;
    }

    public BuildPage clickBuildIconInBuildHistory() {
        buildIconInBuildHistory.click();

        return new BuildPage(getDriver());
    }
}
