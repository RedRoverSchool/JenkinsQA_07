package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;

public class PluginsPage extends BasePage {

    @FindBy(xpath = "//a[@href = '/manage/pluginManager/installed']")
    private WebElement installedPlugins;

    @FindBy(xpath = "//a[starts-with(@href, 'https://plugins.jenkins.io')]")
    private List<WebElement> installedPluginsLinks;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchField;

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

    public boolean isPluginNamePresents(String pluginName) {
        if (installedPluginsLinks.size() != 0) {
            return installedPluginsLinks.stream().anyMatch(text -> text.getText().contains(pluginName));
        }
        return false;
    }
}