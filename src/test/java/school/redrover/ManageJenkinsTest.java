package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ManageJenkinsPage;
import school.redrover.model.NodesListPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private static final String TOOLTIP = "Press / on your keyboard to focus";

    private static final String PLACEHOLDER = "Search settings";

    private static final String SEARCH_SYSTEM = "System";

    @Test
    public void testShortcutTooltipVisibility() {

          ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                  .clickManageJenkins()
                  .hoverOverShortcutIcon();

            Assert.assertEquals(manageJenkinsPage.getTooltipText(), TOOLTIP);
            Assert.assertTrue(manageJenkinsPage.shortcutTooltipIsVisible(), TOOLTIP + " is not visible");
    }

    @Test
    public void testNoResultsTextVisibility() {

        String resultText = new HomePage(getDriver())
                .clickManageJenkins()
                .typeSearchInputField("Test")
                .getNoResultText();

        Assert.assertEquals(resultText, "No results");
    }

    @Test
    public void testRedirectPage() {
        final String request = "Nodes";

        String url = new HomePage(getDriver())
                .clickManageJenkins()
                .typeSearchInputField(request)
                .clickResult(request, new NodesListPage(getDriver()))
                .getCurrentURL();

        Assert.assertTrue(url.contains("manage/computer/"));
    }

    @Test
    public void testListOfResultsVisibility() {

        List<String> result = new HomePage(getDriver())
                .clickManageJenkins()
                .typeSearchInputField("N")
                .getResultsList();

        Assert.assertEquals(
                List.of("Plugins", "Nodes", "Credentials", "Credential Providers", "System Information"),
                result
        );
    }

    @Test
    public void testPlaceholderVisibility() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertEquals(manageJenkinsPage.getPlaceholderText(), PLACEHOLDER);
        Assert.assertTrue(manageJenkinsPage.isPlaceholderVisible(), PLACEHOLDER + " is not visible");
    }

    @Test
    public void testSearchFieldBecomesActiveAfterUsingShortcut() {

        boolean searchFieldIsActiveElement = new HomePage(getDriver())
                .clickManageJenkins()
                .goToSearchFieldUsingShortcut()
                .isSearchFieldActiveElement();

        Assert.assertTrue(searchFieldIsActiveElement, "Search field is not the active element");
    }

    @Test
    public void testSearchFieldTextVisibilityUsingShortcut() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .goToSearchFieldUsingShortcut()
                .typeTextBeingInSearchFieldWithoutLocator(SEARCH_SYSTEM);

        Assert.assertEquals(manageJenkinsPage.getSearchFieldText(), SEARCH_SYSTEM);
        Assert.assertTrue(manageJenkinsPage.isSearchTextAfterShortcutVisible(), SEARCH_SYSTEM + " is not visible");
    }

    @Test
    public void testReloadConfigurationAlertText() {

        String reloadConfigurationAlertText = new HomePage(getDriver())
                .clickManageJenkins()
                .clickReloadConfiguration()
                .getAlertText();

        Assert.assertEquals(reloadConfigurationAlertText, "Reload Configuration from Disk: are you sure?");
    }

    @Test
    public void testSettingsSectionsQuantity() {

        Integer settingsSectionsQuantity = new HomePage(getDriver())
                .clickManageJenkins()
                .getSettingsSectionsQuantity();

        Assert.assertEquals(settingsSectionsQuantity, 18);
    }

    @Test
    public void testTroubleshootingVisibility() {

        String manageOldData = new HomePage(getDriver())
                .clickManageJenkins()
                .getManageOldDataText();

        Assert.assertEquals(manageOldData, "Manage Old Data");
    }
}
