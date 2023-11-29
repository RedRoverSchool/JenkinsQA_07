package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class SystemLogTest extends BaseTest {
    private final static String SYSLOG_NAME = "NewSystemLog";

    @Test
    public void testCreateCustomLogRecorder() {
        TestUtils.clearAllCustomLogRecorders(this);

        String newLogName = new HomePage(getDriver())
            .clickManageJenkins()
            .goSystemLogPage()
            .clickAddRecorder()
            .typeName(SYSLOG_NAME)
            .clickCreate()
            .backToSystemLog()
            .getNameCustomLog();

        Assert.assertEquals(newLogName, SYSLOG_NAME);
    }

    @Test(dependsOnMethods = "testCreateCustomLogRecorder")
    public void testDeleteCustomLogRecorder() {
        List<WebElement> lst = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickCustomLogRecorderName()
                .clickMoreActions()
                .deleteLogRecorder()
                .getListLogRecorders();

        Assert.assertEquals(lst.size(),1);
    }
}
