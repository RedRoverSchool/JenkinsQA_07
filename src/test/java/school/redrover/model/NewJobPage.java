package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NewJobPage extends BasePage {

    @FindBy(id = "name")
    private WebElement name;

    @FindBy(className = "hudson_model_FreeStyleProject")
    private WebElement freestyleProject;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div/h1")
    private WebElement createdJobName;

    public NewJobPage(WebDriver driver) {
        super(driver);
    }

    public NewJobPage createFreestyleProject(String projectName) {
        name.sendKeys(projectName);
        freestyleProject.click();
        okButton.click();

        return new NewJobPage(getDriver());
    }

    public NewJobPage clickSaveButton() {
        submitButton.click();

        return this;
    }

    public String getCreatedJobName() {

        return getWait2().until(ExpectedConditions.visibilityOf(createdJobName)).getText();

    }
}