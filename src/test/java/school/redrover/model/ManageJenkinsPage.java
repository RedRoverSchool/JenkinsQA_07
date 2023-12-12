package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import school.redrover.model.base.BasePage;
import school.redrover.model.nodes.NodesListPage;
import school.redrover.model.users.UserDatabasePage;

import java.time.Duration;
import java.util.List;

public class ManageJenkinsPage extends BasePage<ManageJenkinsPage> {

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

    @FindBy(xpath = "//button[@name='fix']")
    private WebElement goToPluginManagerButton;

    @FindBy(id = "settings-search-bar")
    private WebElement searchInput;

    @FindBy(className = "jenkins-search__results__no-results-label")
    private WebElement searchNoResults;

    @FindAll({@FindBy(xpath = "//div[@class='jenkins-search__results']/a")})
    private List<WebElement> searchResults;

    @FindBy(xpath = "//a[@data-url='reload']")
    private WebElement reloadConfigurationSection;

    @FindBy(className = "jenkins-section__item")
    private List<WebElement> settingsSections;

    @FindBy(xpath = "//a[contains (@href, 'OldData')]//dt")
    private WebElement manageOldData;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[3]//dt")
    private List<WebElement> statusInformationSectionsList;

    @FindAll({@FindBy(xpath = "(//div[2]/div[2]/section[3]/div/div/a/dl/dt)")})
    private List<WebElement> securitySectionsList;

    @FindBy(xpath = "//a[@href='systemInfo']")
    private WebElement systemInfoSection;

    @FindBy(xpath = "//a[@href='load-statistics']")
    private WebElement loadStatisticsSection;

    @FindBy(xpath = "//a[@href='about']")
    private WebElement aboutJenkinsSection;

    @FindBy(xpath = "//a[@href='pluginManager']/div/div")
    private WebElement numberPlugins;

    @FindBy(xpath = "//a[@href='configureSecurity']")
    private WebElement securitySection;

    @FindBy(xpath = "//a[@href='credentials']")
    private WebElement credentialsSection;

    @FindBy(xpath = "//a[@href='configureCredentials']")
    private WebElement credentialProvidersSection;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    Wait<WebDriver> wait = new FluentWait<>(getDriver())
            .withTimeout(Duration.ofSeconds(2))
            .pollingEvery(Duration.ofMillis(300))
            .ignoring(JavascriptException.class);

    public PluginsPage goPluginsPage() {
        plugins.click();

        return new PluginsPage(getDriver());
    }

    public PluginsPage clickOnGoToPluginManagerButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(goToPluginManagerButton)).click();
        return new PluginsPage(getDriver());
    }

    public UserDatabasePage clickUsersButton() {
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

    public boolean isShortcutTooltipVisible() {
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
        return wait.until(ExpectedConditions.visibilityOf(searchNoResults)).getText();
    }

    public <T> T clickResult(String request, T page) {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults))
                .stream()
                .filter(el -> el.getText().contains(request))
                .findFirst()
                .ifPresent(WebElement::click);
        return page;
    }

    public List<String> getResultsList() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(searchResults))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getPlaceholderText() {
        return searchInput.getAttribute("placeholder");
    }

    public boolean isPlaceholderVisible() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String typedInSearchText = (String) js.executeScript("return arguments[0].value;", searchInput);

        if (!getPlaceholderText().isEmpty() && typedInSearchText.isEmpty()) {
            return true;
        }

        return false;
    }

    public ManageJenkinsPage moveToSearchFieldUsingShortcut() {
        getDriver().switchTo().activeElement().sendKeys("/");

        return this;
    }

    public boolean isSearchFieldActiveElement() {

        return searchInput.equals(getDriver().switchTo().activeElement());
    }

    public ManageJenkinsPage typeTextBeingInSearchFieldWithoutLocator(String setting) {
        getDriver().switchTo().activeElement().sendKeys(setting);

        return this;
    }

    public String getSearchFieldText() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript("return arguments[0].value;", searchInput);
    }

    public boolean isSearchTextAfterShortcutVisible() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String typedInSearchText = (String) js.executeScript("return arguments[0].value;", searchInput);

        return !typedInSearchText.isEmpty();
    }

    public ManageJenkinsPage clickReloadConfiguration() {
        reloadConfigurationSection.click();

        return this;
    }

    public String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }

    public Integer getSettingsSectionsQuantity() {
        return settingsSections.size();
    }

    public String getManageOldDataText() {
        return manageOldData.getText();
    }

    public boolean isManageOldDataClickable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(manageOldData));
        return true;
    }

    public boolean areStatusInformationSectionsVisible() {
        for (WebElement section : statusInformationSectionsList) {
            return section.isDisplayed();
        }
        return false;
    }

    public boolean areStatusInformationSectionsClickable() {
        for (WebElement section : statusInformationSectionsList) {
            return section.isEnabled();
        }
        return false;
    }

    public Integer getStatusInformationSectionsQuantity() {
        return statusInformationSectionsList.size();
    }

    public boolean areSecuritySectionsVisible() {
        return securitySectionsList.stream().allMatch(WebElement::isDisplayed);
    }

    public boolean areSecuritySectionsClickable() {
        return securitySectionsList.stream().allMatch(WebElement::isEnabled);
    }

    public SystemInfoPage clickSystemInfoSection() {
        systemInfoSection.click();

        return new SystemInfoPage(getDriver());
    }

    public boolean searchFieldIsVisible() {
        return searchInput.isDisplayed();
    }

    public ManageJenkinsPage clickOnSearchField() {
        searchInput.click();

        return this;
    }

    public boolean searchResultsAreClickable() {

        return searchResults.get(1).isEnabled();
    }

    public String pressEnterAfterInput(String inputText) {
        searchInput.sendKeys(inputText);

        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));

        searchInput.sendKeys(Keys.ENTER);

        return getCurrentUrl();
    }

    public LoadStatisticsPage clickLoadStatisticsSection() {
        loadStatisticsSection.click();

        return new LoadStatisticsPage(getDriver());
    }
    public AboutJenkinsPage clickAboutJenkinsSection() {
        aboutJenkinsSection.click();

        return new AboutJenkinsPage(getDriver());
    }

    public List<String> getStatusInformationSectionsTitles() {
        return statusInformationSectionsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getNumberUpdatesPlugins() {

        return numberPlugins.getText();
    }

    public SecurityPage clickSecuritySection() {
        securitySection.click();

        return new SecurityPage(getDriver());
    }

    public CredentialsPage clickCredentialsSection() {
        credentialsSection.click();

        return new CredentialsPage(getDriver());
    }

    public CredentialProvidersPage clickCredentialProvidersSection() {
        credentialProvidersSection.click();

        return new CredentialProvidersPage(getDriver());
    }

    public UserDatabasePage clickUsersSection() {
        userSection.click();

        return new UserDatabasePage(getDriver());
    }
}
