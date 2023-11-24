package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ManageJenkinsPage;
import school.redrover.runner.BaseTest;

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
}
