package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseDetailsPage;

import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectDetailsPage extends BaseDetailsPage<FreestyleProjectConfigurePage, FreestyleProjectDetailsPage> {

    @FindBy(name = "Submit")
    private WebElement enableDisableButton;

    @FindBy(className = "warning")
    private WebElement warningMessage;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuild;

    @FindBy(css = "ul[class='permalinks-list']")
    private WebElement listPermalinks;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement projectDescriptionInputField;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@id=\"tasks\"]/div[6]/span/a/span[2]")
    private WebElement deleteProject;

    @FindBy(xpath = "//a[contains(@href,'ws')]")
    private WebElement workspaceButton;

    @FindBy(xpath = "//a//span[2]")
    private List<WebElement> itemsSidePanel;

    @FindBy(className = "warning")
    private WebElement projectDisabledWarning;

    @FindBy(xpath = "//ul[@style='list-style-type: none;']/li/a")
    private List<WebElement> upstreamProjectsList;

    public FreestyleProjectDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectConfigurePage createConfigurationPage() {
        return new FreestyleProjectConfigurePage(getDriver());
    }

    public boolean isEnabled() {
        return getTextEnableDisableButton().equals("Disable Project");
    }

    public String getWarningMessageWhenDisabled() {
        return warningMessage.getText().split("\n")[0].trim();
    }

    public String getTextEnableDisableButton() {
        return enableDisableButton.getText();
    }

    public WorkspacePage goToWorkspaceFromSideMenu() {
        workspaceButton.click();

        return new WorkspacePage(getDriver());
    }

    public FreestyleProjectDetailsPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public boolean isJobExist() {
        return getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).isDisplayed();
    }

    public BuildPage clickPermalinkLastBuild() {
        lastBuild.click();

        return new BuildPage(getDriver());
    }

    public String getPermalinksText() {
        return listPermalinks.getText();
    }

    public FreestyleProjectDetailsPage insertDescriptionText(String description) {
        projectDescriptionInputField.sendKeys(description);

        return this;
    }

    public FreestyleProjectDetailsPage deleteDescriptionText() {
        projectDescriptionInputField.clear();

        return this;
    }

    public HomePage deleteProject() {
        deleteProject.click();
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public List<String> getTextItemsSidePanel() {
        List<String> textValue = new ArrayList<>();
        for (WebElement item : itemsSidePanel) {
            textValue.add(item.getText());
        }

        return textValue;
    }

    public boolean isProjectDisabled() {
        return projectDisabledWarning.isEnabled();
    }

    public List<String> getUpstreamProjectsList() {
        return upstreamProjectsList.stream().map(WebElement::getText).toList();
    }
}
