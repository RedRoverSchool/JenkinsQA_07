package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='computer']")
    private WebElement nodeSection;

    @FindBy(xpath = "//a[@href = 'securityRealm/']")
    private WebElement userSection;

    @FindBy(xpath = "//a[@href='log']")
    private WebElement systemLogSection;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcutIcon;

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

    public SystemLogPage goSystemLogPage() {
        systemLogSection.click();

        return new SystemLogPage(getDriver());
    }

    public ManageJenkinsPage hoverOverShortcutIcon() {
        new Actions(getDriver())
                .moveToElement(shortcutIcon)
                .perform();

        return this;
    }

    public String getTooltipText() {
        return shortcutIcon.getAttribute("tooltip");
    }

    public boolean shortcutTooltipIsVisible() {
        boolean shortcutTooltipIsVisible = true;

        new Actions(getDriver())
                .moveToElement(shortcutIcon)
                .perform();

        if (!shortcutIcon.getAttribute("title").isEmpty()) {
            return false;
        }

        return shortcutTooltipIsVisible;
    }
}
