package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.LogRecordersDetailsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class SystemLogTest extends BaseTest {
    private final static String SYSLOG_NAME = "NewSystemLog";

    private final static String LOGGER_NAME = "com";

    private final static String LEVEL_LOG = "INFO";

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

    @Test(dependsOnMethods = {"testCreateCustomLogRecorder", "testAddNewLogger", "testClearCustomLog"})
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

    @Test(dependsOnMethods = "testCreateCustomLogRecorder")
    public void testAddNewLogger() {

        List<String> loggersAndLevels = List.of(
                SYSLOG_NAME,
                LOGGER_NAME,
                LEVEL_LOG);

        List <String> loggersAndLevelsSavedList = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickGearIcon(SYSLOG_NAME)
                .clickAdd()
                .chooseLastLogger(LOGGER_NAME)
                .chooseLastLogLevel(LEVEL_LOG)
                .clickSave()
                .clickConfigure()
                .getLoggersAndLevelsSavedList();

        Assert.assertEquals(loggersAndLevelsSavedList, loggersAndLevels);
    }

    @Test(dependsOnMethods = {"testCreateCustomLogRecorder", "testAddNewLogger"})

    public void testClearCustomLog() {
        LogRecordersDetailsPage clearLog = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickGearIcon(SYSLOG_NAME)
                .changeLogger("")
                .chooseLastLogLevel("FINE")
                .clickSave()
                .clickConfigure()
                .chooseLastLogLevel(LEVEL_LOG)
                .clickSave()
                .clickClearThisLog();

        Assert.assertEquals(getWait2().until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[@id='main-panel']/div[2]"))).getText(), "No logs available");
    }
}
