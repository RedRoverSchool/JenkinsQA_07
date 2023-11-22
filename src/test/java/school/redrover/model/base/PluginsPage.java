package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.runner.SeleniumUtils;

public class PluginsPage extends BasePage {

    @FindBy(xpath = "//a[@href = '/manage/pluginManager/installed']")
    private WebElement installedPlugins;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public void jsClickInstalledPlugins() {
        SeleniumUtils.jsClick(getDriver(), installedPlugins);
    }
}
