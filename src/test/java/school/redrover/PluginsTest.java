package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ManageJenkinsPage;
import school.redrover.model.PluginsPage;
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
                .isPluginNamePresent(pluginName);

        Assert.assertTrue(pluginNamePresents);
    }

    @Test
    public void testUpdateButtonIsDisabledByDefault() {
        boolean updateButtonIsEnabled = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .updateButtonIsEnabled();

        Assert.assertFalse(updateButtonIsEnabled);
    }

    @Test
    public void testUpdateButtonIsEnabled() {
        boolean updateButtonIsEnabled = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .selectFirstCheckbox()
                .updateButtonIsEnabled();

        Assert.assertTrue(updateButtonIsEnabled);
    }

    @Test
    public void testAllUpdatesPluginsAreSelectedFromTitle() {
        boolean areAllCheckboxesSelected = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .selectAllCheckboxesFromTitle()
                .areAllCheckboxesSelected();

        Assert.assertTrue(areAllCheckboxesSelected);
    }

    @Test
    public void testMatchesNumberPluginsForUpdate() {
        String numberUpdatesPluginsFromManagePage = new HomePage(getDriver())
                .clickManageJenkins()
                .getNumberUpdatesPlugins();

        String numberUpdatesPluginsFromPluginsPage = new ManageJenkinsPage(getDriver())
                .goPluginsPage()
                .getNumberPluginsForUpdates();

        Assert.assertEquals(numberUpdatesPluginsFromPluginsPage, numberUpdatesPluginsFromManagePage);
    }

    @Test
    public void testSortingPluginsForUpdateInAscendingOrderByDefault() {
        List<String> updatesPluginsList = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .getUpdatesPluginsList();

        List<String> sortedUpdatesPluginsList = new PluginsPage(getDriver())
                .getSortedUpdatesPluginsListInAscendingOrder();

       Assert.assertEquals(sortedUpdatesPluginsList, updatesPluginsList);
    }

    @Test
    public void testSortingPluginsForUpdateInDescendingOrder() {
        List<String> updatesPluginsList = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .sortingByNameFromTitle()
                .getUpdatesPluginsList();

        List<String> sortedUpdatesPlaginsList = new PluginsPage(getDriver())
                .getSortedUpdatesPluginsListInDescendingOrder();

        Assert.assertEquals(sortedUpdatesPlaginsList, updatesPluginsList);
    }

}
