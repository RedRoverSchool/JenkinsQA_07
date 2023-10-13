package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupBrainBuildersTest extends BaseTest {

    @Test
    public void testTitleCheck() {
        getDriver().get("https://www.championat.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Чемпионат.com: новости спорта - Чемпионат");
    }
}
