package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeDetailsPage extends BasePage {

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement markThisNodeTemporarilyOfflineButton;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__content']/h1")
    private WebElement nodeName;

    public NodeDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "message")
    private WebElement message;

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

    public String getMessage() {
        return message.getText();
    }

}
