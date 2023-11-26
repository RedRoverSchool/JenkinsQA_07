package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='computer']")
    private WebElement nodeSection;

    @FindBy(xpath = "//a[@href = 'securityRealm/']")
    private WebElement userSection;

    @FindBy(xpath = "//a[@href='log']")
    private WebElement systemLogSection;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcutIcon;

    @FindBy(xpath = "//dl/dt[text()='Plugins']")
    private WebElement plugins;

    @FindBy(css = ".jenkins-search > input")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@class='jenkins-search__results']/p")
    private WebElement searchNoResults;

    @FindAll({@FindBy(xpath = "//div[@class='jenkins-search__results']/a")})
    private List<WebElement> searchResults;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public PluginsPage goPluginsPage() {
        plugins.click();

        return new PluginsPage(getDriver());
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

        if (!shortcutIcon.getAttribute("title").isEmpty()) {
            return false;
        }

        return shortcutTooltipIsVisible;
    }

    public ManageJenkinsPage typeSearchInputField(String request) {
        searchInput.sendKeys(request);

        return this;
    }

    public String getNoResultText() {

        return getWait2().until(ExpectedConditions.visibilityOf(searchNoResults)).getText();
    }

    public <T> T clickResult(String request, T page) {
        getWait10().until(ExpectedConditions.visibilityOfAllElements(searchResults)).stream()
                .filter(el -> el.getText().contains(request))
                .findFirst()
                .ifPresent(WebElement::click);
        return page;
    }

    public List<String> getResultsList() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElements(searchResults)).stream().map(WebElement::getText).toList();
    }
}
