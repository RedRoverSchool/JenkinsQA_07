package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PluginsTest extends BaseTest {

    @Test
    public void testInstalledPluginsContainsAnt() {
        List<String> pluginsNames = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .clickInstalledPlugins()
                .installedPluginsList();
        Assert.assertFalse(pluginsNames.isEmpty(), "No elements in the List");
        Assert.assertTrue(pluginsNames.stream().anyMatch(text -> text.contains("Ant Plugin")), "Ant Plugin was not found in the list of installed plugins.");
    }

    @Test
    public void testInstalledPluginsSearch() {
        final String pluginName = "Build Timeout";
        boolean pluginNamePresents = new HomePage(getDriver())
                .clickManageJenkins()
                .clickOnGoToPluginManagerButton()
                .clickInstalledPlugins()
                .typePluginNameIntoSearchField(pluginName)
                .isPluginNamePresents(pluginName);

        Assert.assertTrue(pluginNamePresents);
    }
}