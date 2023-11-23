package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.SeleniumUtils;

import java.util.List;

public class PluginsPage extends BasePage {

    @FindBy(xpath = "//a[@href = '/manage/pluginManager/installed']")
    private WebElement installedPlugins;

    @FindBy(xpath = "//a[starts-with(@href, 'https://plugins.jenkins.io')]")
    private List<WebElement> installedPluginsLinks;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public PluginsPage jsClickInstalledPlugins() {
        SeleniumUtils.jsClick(getDriver(), installedPlugins);
        return new PluginsPage(getDriver());
    }

    public List<String> installedPluginsList() {
        return installedPluginsLinks.stream()
                .map(WebElement::getText)
                .toList();
    }
}
