package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.LogRecordersDetailsPage;
import school.redrover.runner.BaseTest;
import java.util.*;

public class ConfigureCustomLogTest extends BaseTest {

    private final static String LOG_NAME = "Inform";

    private final static String LOGGER_NAME = "com";

    private final static String LEVEL_LOG = "INFO";

    @Test
    public void testAddNewLogger() {

        List<String> loggersAndLevels = List.of(
                LOG_NAME,
                LOGGER_NAME,
                LEVEL_LOG);

        List <String> loggersAndLevelsSavedList = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickAddRecorder()
                .typeName(LOG_NAME)
                .clickCreate()
                .clickSystemLog()
                .clickGearIcon(LOG_NAME)
                .clickAdd()
                .chooseLastLoggerFromDropDown(LOGGER_NAME)
                .chooseLastLogLevel(LEVEL_LOG)
                .clickSave()
                .clickConfigure()
                .getLoggersAndLevelsSavedList();

        Assert.assertEquals(loggersAndLevelsSavedList, loggersAndLevels);
    }

    @Test(dependsOnMethods = "testAddNewLogger")

    public void testClearCustomLog() {
        LogRecordersDetailsPage clearLog = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickGearIcon(LOG_NAME)
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
