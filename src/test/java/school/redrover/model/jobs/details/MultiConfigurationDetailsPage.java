package school.redrover.model.jobs.details;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.jobs.configs.MultiConfigurationConfigurePage;
import school.redrover.model.base.BaseDetailsPage;

public class MultiConfigurationDetailsPage extends BaseDetailsPage<MultiConfigurationConfigurePage, MultiConfigurationDetailsPage> {

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(css = "a[data-message]")
    private WebElement taskLinkDeleteMultiConfigurationProject;

    public MultiConfigurationDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationConfigurePage createConfigurationPage() {
        return new MultiConfigurationConfigurePage(getDriver());
    }

    public MultiConfigurationDetailsPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return this;
    }

    public MultiConfigurationDetailsPage clearDescription() {
        inputDescription.clear();

        return this;
    }

    public MultiConfigurationDetailsPage taskLinkDeleteMultiConfigurationProject() {
        taskLinkDeleteMultiConfigurationProject.click();

        return this;
    }

    public MultiConfigurationDetailsPage cancelDelete() {
        getDriver().switchTo().alert().dismiss();

        return this;
    }

    public String getProjectHeadLineText() {

        return getDriver().findElement(By.tagName("h1")).getText();
    }
}
