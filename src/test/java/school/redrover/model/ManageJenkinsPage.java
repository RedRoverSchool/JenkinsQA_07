package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='computer']")
    private WebElement nodeSection;

    @FindBy(xpath = "//a[@href = 'securityRealm/']")
    private WebElement userSection;


    @FindBy(xpath = "//dl/dt[text()='Plugins']")
    private WebElement plugins;

    @FindBy(xpath = "//a[@href='log']")
    private WebElement systemLogSection;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public UserDatabasePage goUserDatabasePage() {
        userSection.click();

        return new UserDatabasePage(getDriver());
    }

    public NodesListPage goNodesListPage() {
        nodeSection.click();

        return new NodesListPage(getDriver());
    }


    public PluginsPage goPluginsPage() {
        plugins.click();

        return new PluginsPage(getDriver());

    public SystemLogPage goSystemLogPage() {
        systemLogSection.click();

        return new SystemLogPage(getDriver());
    }
}
