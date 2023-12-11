package school.redrover.model.builds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseDetailsPage;

import java.util.List;

public class BuildPage extends BasePage<BuildPage> {
    @FindBy(name = "Submit")
    private WebElement clickSubmitCancel;

    @FindBy(css = "a[href$='confirmDelete']")
    private WebElement deleteBuild;

    @FindBy(xpath = "//pre[@class='console-output']//span[@class='timestamp']")
    private List<WebElement> timestampsList;

    @FindBy(xpath = "//a[@class='task-link ' and contains(@href, 'replay')]")
    private WebElement replayButtonSideMenu;

    @FindBy(xpath = "//*[text()='Edit Build Information']/ancestor::a")
    private WebElement editBuildInformationSideMenu;

    public BuildPage(WebDriver driver) {
        super(driver);
    }

    public BuildPage clickDeleteBuildSidePanel() {
        deleteBuild.click();

        return this;
    }

    public <ProjectDetailsPage extends BaseDetailsPage<?, ?>> ProjectDetailsPage clickButtonDeleteBuild(ProjectDetailsPage projectDetailsPage) {
        clickSubmitCancel.click();

        return projectDetailsPage;
    }

    public List<String> getTimestampsList() {
        return timestampsList.stream().map(WebElement::getText).toList();
    }

    public ReplayBuildPipelinePage clickReplaySideMenu() {
        replayButtonSideMenu.click();

        return new ReplayBuildPipelinePage(getDriver());
    }

    public BuildEditInformationPage clickEditBuildInformationSideMenu(){
        editBuildInformationSideMenu.click();

        return new BuildEditInformationPage(getDriver());
    }

}
