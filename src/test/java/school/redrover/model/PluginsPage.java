package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.SeleniumUtils;

import java.util.List;

public class PluginsPage extends BasePage<PluginsPage> {

    @FindBy(xpath = "//a[@href = '/manage/pluginManager/installed']")
    private WebElement installedPlugins;

    @FindBy(xpath = "//a[starts-with(@href, 'https://plugins.jenkins.io')]")
    private List<WebElement> installedPluginsLinks;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchField;

    @FindBy(id = "button-update")
    private WebElement updateButton;

    @FindBy(xpath = "//table[@id='plugins']//tr[1]//label")
    private WebElement firstCheckbox;

    @FindBy(xpath = "//table[@id='plugins']/thead//button")
    private WebElement checkboxFromTitle;

    @FindBy(xpath = "//table[@id='plugins']//input")
    private List <WebElement> allCheckboxesList;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public PluginsPage clickInstalledPlugins() {
        SeleniumUtils.jsClick(getDriver(), installedPlugins);
        return this;
    }

    public List<String> installedPluginsList() {
        return installedPluginsLinks.stream()
                .map(WebElement::getText)
                .toList();
    }

    public PluginsPage typePluginNameIntoSearchField(String pluginName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.sendKeys(pluginName);
        return this;
    }

    public boolean isPluginNamePresent(String pluginName) {
        if (installedPluginsLinks.size() != 0) {
            return installedPluginsLinks.stream().anyMatch(text -> text.getText().contains(pluginName));
        }
        return false;
    }

    public boolean updateButtonIsEnabled() {

        return updateButton.isEnabled();
    }

    public PluginsPage selectFirstCheckbox() {
        firstCheckbox.click();

        return this;
    }

    public PluginsPage selectAllCheckboxesFromTitle() {
        checkboxFromTitle.click();

        return this;
    }

    public Boolean areAllCheckboxesSelected() {

        return allCheckboxesList.stream().allMatch(WebElement::isSelected);
    }

}
