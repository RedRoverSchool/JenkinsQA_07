package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.model.jobs.details.FreestyleProjectDetailsPage;
import school.redrover.model.nodes.NodesListPage;
import school.redrover.model.users.UserDatabasePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ManageJenkinsTest extends BaseTest {

    private static final String TOOLTIP = "Press / on your keyboard to focus";
    private static final String PLACEHOLDER = "Search settings";
    private static final String SEARCH_SYSTEM = "System";
    private final static String USER_NAME_CREDENTIAL = "Credentials Provider name";
    private static final String PROJECT_NAME = "NewFreestyleProject";

    @Test
    public void testShortcutTooltipVisibility() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .hoverOverShortcutIcon();

        Assert.assertEquals(manageJenkinsPage.getTooltipText(), TOOLTIP);
        Assert.assertTrue(manageJenkinsPage.isShortcutTooltipVisible(), TOOLTIP + " is not visible");
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
                .moveToSearchFieldUsingShortcut()
                .isSearchFieldActiveElement();

        Assert.assertTrue(searchFieldIsActiveElement, "Search field is not the active element");
    }

    @Test
    public void testSearchFieldTextVisibilityUsingShortcut() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .moveToSearchFieldUsingShortcut()
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

    @Test
    public void testStatusInformationSectionsVisibleAndClickable() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.areStatusInformationSectionsVisible());
        Assert.assertTrue(manageJenkinsPage.areStatusInformationSectionsClickable());
    }

    @Test
    public void testStatusInformationSectionsQuantity() {
        Integer statusInformationSectionsQuantity = new HomePage(getDriver())
                .clickManageJenkins()
                .getStatusInformationSectionsQuantity();

        Assert.assertEquals(statusInformationSectionsQuantity, 4);
    }

    @Test
    public void testTroubleshootingClick() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.isManageOldDataClickable());
    }

    @Test
    public void testVisibilitySecuritySections() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.areSecuritySectionsVisible());
    }

    @Test
    public void testClickabilitySecuritySections() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.areSecuritySectionsClickable());
    }

    @Test
    public void testRedirectionPluginsPage() {
        String urlText = new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage()
                .getCurrentUrl();

        Assert.assertTrue(urlText.contains("pluginManager/"));
    }

    @Test
    public void testSystemInfoPageRedirection() {
        String currentUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSystemInfoSection()
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("systemInfo"), currentUrl + " doesn't contain expected text");
    }

    @Test
    public void testSystemLogPageRedirection() {
        SystemLogPage systemLogPage = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage();

        Assert.assertTrue(systemLogPage.getPageTitle().contains("Log Recorders"));
        Assert.assertTrue(systemLogPage.getCurrentUrl().contains("log"));
    }

    @Test
    public void testVisibilityOfSearchField() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.searchFieldIsVisible());
    }

    @Test
    public void testSearchFieldByClick() {
        final String inputText = "sys";

        boolean searchResultIsClickable = new HomePage(getDriver())
                .clickManageJenkins()
                .clickOnSearchField()
                .typeSearchInputField(inputText)
                .searchResultsAreClickable();

        Assert.assertTrue(searchResultIsClickable);
    }

    @Test
    public void testDefaultRedirectionByEnter() {
        final String inputText = "u";
        final String url = "/manage/pluginManager/";

        String redirectedUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .pressEnterAfterInput(inputText);

        Assert.assertTrue(redirectedUrl.contains(url));
    }

    @Test
    public void testLoadStatisticsRedirection() {
        String currentUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .clickLoadStatisticsSection()
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("load-statistics"));
    }

    @Test
    public void testAboutJenkinsRedirection() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAboutJenkinsSection()
                .getPageTitle();

        Assert.assertTrue(pageTitle.contains("About Jenkins"));
    }

    @Test
    public void testStatusInformationSectionsTitles() {
        List<String> expectedStatusInformationSectionsList = List.of(
                "System Information",
                "System Log",
                "Load Statistics",
                "About Jenkins"
        );

        List<String> statusInformationSectionsList = new HomePage(getDriver())
                .clickManageJenkins()
                .getStatusInformationSectionsTitles();

        Assert.assertEquals(statusInformationSectionsList, expectedStatusInformationSectionsList,
                "Status Information sections titles differ from the expected ones");
    }

    @Ignore
    @Test
    public void testCreateCredentialFromConfigurePage() {

        TestUtils.createFreestyleProject(this, PROJECT_NAME, true);

        boolean credentialsCreated = new HomePage(getDriver())
                .clickProjectStatusByName(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .clickConfigure()
                .clickGitRadioButtonWithScroll()
                .clickAddButton()
                .clickJenkinsOption()
                .inputUsername(USER_NAME_CREDENTIAL)
                .clickAddButtonCredentialsProvider()
                .checkIfNewCredentialInTheMenu(USER_NAME_CREDENTIAL);

        assertTrue(credentialsCreated);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateCredentialFromConfigurePage")
    public void testDeleteCredential() {

        String expectedText = "Global credentials (unrestricted)";

        String actualText = new HomePage(getDriver())
                .clickPeople()
                .clickCurrentUserName()
                .clickCredentials()
                .clickCredentialsByName(USER_NAME_CREDENTIAL)
                .clickDeleteButton()
                .clickYesButton()
                .getTextMainPanel();

        assertEquals(actualText, expectedText);
    }

    @Test
    public void testSecurityPageRedirection() {
        SecurityPage securityPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSecuritySection();

        Assert.assertTrue(securityPage.getPageTitle().contains("Security"));
        Assert.assertTrue(securityPage.getCurrentUrl().contains("configureSecurity"));
    }

    @Test
    public void testCredentialsPageRedirection() {
        CredentialsPage credentialsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickCredentialsSection();

        Assert.assertTrue(credentialsPage.getPageTitle().contains("Credentials"));
        Assert.assertTrue(credentialsPage.getCurrentUrl().contains("credentials"));
    }

    @Test
    public void testCredentialProvidersPageRedirection() {
        CredentialProvidersPage credentialsProvidersPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickCredentialProvidersSection();

        Assert.assertTrue(credentialsProvidersPage.getPageTitle().contains("Credential Providers"));
        Assert.assertTrue(credentialsProvidersPage.getCurrentUrl().contains("configureCredentials"));
    }

    @Test
    public void testUserDatabasePageRedirection() {
        UserDatabasePage usersPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();

        Assert.assertTrue(usersPage.getPageTitle().contains("Users"));
        Assert.assertTrue(usersPage.getCurrentUrl().contains("securityRealm"));
    }

    @Test
    public void testReloadConfigurationAlertDisappearance() {

        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickReloadConfiguration()
                .dismissAlert();

        Assert.assertTrue(manageJenkinsPage.isAlertNotPresent());
        Assert.assertEquals(manageJenkinsPage.getHeadLineText(), "Manage Jenkins");
    }
}
