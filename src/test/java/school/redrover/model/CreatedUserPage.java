package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreatedUserPage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(name = "description")
    private WebElement userDescriptionInputField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id = 'description']/div[1]")
    private WebElement descriptionText;

    @FindBy(className = "jenkins_ver")
    private WebElement jenkinsVersionButton;

    @FindBy(css = "a[href='/manage/about']")
    private WebElement aboutJenkinsButton;

    public CreatedUserPage(WebDriver driver) {
        super(driver);
    }

    public CreatedUserPage clickAddDescription() {
        addDescriptionButton.click();

        return this;
    }

    public CreatedUserPage addAUserDescription(String description) {
        this.userDescriptionInputField.sendKeys(description);

        return this;
    }

    public CreatedUserPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public String getJenkinsVersion() {

        return jenkinsVersionButton.getText();
    }

    public CreatedUserPage clickVersionJenkins() {
        jenkinsVersionButton.click();

        return this;
    }

    public AboutJenkinsPage clickAboutJenkins() {
        aboutJenkinsButton.click();

        return new AboutJenkinsPage(getDriver());
    }
}
