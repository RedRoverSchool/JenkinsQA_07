package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeCreatePage extends BasePage {

    @FindBy(id = "name")
    private WebElement inputName;

    @FindBy(css = ".jenkins-radio__label")
    private WebElement PermanentAgentCheckbox;

    @FindBy(name = "Submit")
    private WebElement createButton;

    public NodeCreatePage(WebDriver driver) {
        super(driver);
    }

    public NodeCreatePage sendKeys(String nodeName) {
        inputName.sendKeys(nodeName);
        return this;
    }

    public NodeCreatePage clickPermanentAgentCheckbox () {
        PermanentAgentCheckbox.click();
        return this;
    }

    public NodeCofigurationPage clickCreateButton () {
        createButton.click();
        return new NodeCofigurationPage(getDriver());
    }
}
