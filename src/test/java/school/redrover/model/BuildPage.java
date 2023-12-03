package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class BuildPage extends BasePage {
    @FindBy(name = "Submit")
    private WebElement clickSubmitCancel;

    @FindBy(css = "a[href$='confirmDelete']")
    private WebElement deleteBuild;

    @FindBy(xpath = "//pre[@class='console-output']//span[@class='timestamp']")
    private List<WebElement> timestampsList;

    public BuildPage(WebDriver driver) {
        super(driver);
    }

    public BuildPage clickDeleteBuildSidePanel() {
        deleteBuild.click();

        return this;
    }

    public <ProjectDetailsPage extends BaseProjectPage> ProjectDetailsPage clickButtonDeleteBuild(ProjectDetailsPage projectDetailsPage) {
        clickSubmitCancel.click();

        return projectDetailsPage;
    }

    public List<String> getTimestampsList() {
        return timestampsList.stream().map(WebElement::getText).toList();
    }
}
