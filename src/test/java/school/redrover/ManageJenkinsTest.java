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

        Assert.assertNotEquals(
                List.of("Plugins", "Nodes", "Credentials", "Credential", "Providers", "System Information"),
                result
        );
    }
}
