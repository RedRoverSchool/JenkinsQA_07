package school.redrover.model.nodes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeDetailsPage extends BasePage<NodeDetailsPage> {

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement markThisNodeTemporarilyOfflineButton;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__content']/h1")
    private WebElement nodeName;

    @FindBy(xpath = "//span/a[contains(@href, '/configure')]")
    private WebElement configure;

    @FindBy(xpath = "//div[@id='tasks']/div[2]/span/a")
    private WebElement deleteButton;

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//div/button[@name = 'Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id= 'description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//span/a[contains(@href, '/configure')]")
    private WebElement configureButton;

    @FindBy(xpath = "//a[contains(@href, 'label')]")
    private WebElement labelText;

    @FindBy(xpath = "//form[@action = 'setOfflineCause']/button")
    private WebElement updateOfflineReasonButton;

    @FindBy(xpath = "//div[@class='message']")
    private WebElement offlineReasonByMessage;

    public NodeDetailsPage(WebDriver driver) {
        super(driver);
    }

    public NodesListPage goNodesListPage() {
        buildExecutorStatus.click();

        return new NodesListPage(getDriver());
    }

    public String getNodeName() {
        return nodeName.getText();
    }

    public NodeMarkOfflinePage clickMarkOffline() {
        markThisNodeTemporarilyOfflineButton.click();

        return new NodeMarkOfflinePage(getDriver());
    }

    public <T> T clickConfigure(T page) {
        configure.click();

        return page;
    }

    public NodeDetailsPage clickDeleteAgentButton() {
        deleteButton.click();

        return this;
    }

    public boolean elementIsNotPresent(String xpath) {
        return getDriver().findElements(By.xpath(xpath)).isEmpty();
    }

    public String switchToAlertAndGetText() {
        return getDriver().switchTo().alert().getText();
    }

    public NodeDetailsPage inputDescription(String description) {
        descriptionButton.click();
        descriptionField.clear();
        descriptionField.sendKeys(description);
        saveButton.click();

        return this;
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public String getLabelText() {
        return labelText.getText();
    }

    public NodeUpdateOfflineReasonPage clickUpdateOfflineReason() {
        updateOfflineReasonButton.click();

        return new NodeUpdateOfflineReasonPage(getDriver());
    }

    public String offlineReasonMessage() {
        return offlineReasonByMessage.getText().split(":")[1].trim();
    }
}