package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.*;

public class CustomConfigureLogTest extends BaseTest {

    private final static String LOG_NAME = "Inform";

    private final static String LOGGER_NAME = "com";

    private final static String LEVEL_LOG = "INFO";

    @Test
    public void testConfigureCustomLogRecorder() {

        List<String> loggersAndLevels = List.of(
                LOG_NAME,
                LOGGER_NAME,
                LEVEL_LOG
        );

        List <String> loggersAndLevelsSavedList = new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage()
                .clickAddRecorder()
                .typeName(LOG_NAME)
                .clickCreate()
                .clickSystemLog()
                .clickGearIcon(LOG_NAME)
                .clickAdd()
                .chooseLastLogger(LOGGER_NAME)
                .chooseLastLogLevel(LEVEL_LOG)
                .clickSave()
                .clickConfigure()
                .getLoggersAndLevelsSavedList()
        ;

        Assert.assertEquals(loggersAndLevelsSavedList, loggersAndLevels);
    }
}
