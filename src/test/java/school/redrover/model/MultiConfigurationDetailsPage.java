package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultiConfigurationDetailsPage extends BasePage {
    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement descriptionText;

    @FindBy(css = "#description-link")
    private WebElement buttonEditDescription;

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(name = "Submit")
    private WebElement buttonSaveDescription;

    @FindBy(css = "a[data-message]")
    private WebElement taskLinkDeleteMultiConfigurationProject;

    public MultiConfigurationDetailsPage(WebDriver driver) {
        super(driver);
    }


    public String getDescriptionText() {

        return descriptionText.getText();
    }

    public MultiConfigurationDetailsPage buttonEditDescription() {
        buttonEditDescription.click();

        return this;
    }

    public MultiConfigurationDetailsPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return this;
    }

    public MultiConfigurationDetailsPage buttonSaveDescription() {
        buttonSaveDescription.click();

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

    public HomePage acceptAlert() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public MultiConfigurationDetailsPage cancelDelete() {
        getDriver().switchTo().alert().dismiss();

        return this;
    }

    public String getProjectHeadLineText() {

        return getDriver().findElement(By.tagName("h1")).getText();
    }
}
