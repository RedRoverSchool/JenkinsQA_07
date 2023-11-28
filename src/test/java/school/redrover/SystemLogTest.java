package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.ArrayList;
import java.util.List;

public class SystemLogTest extends BaseTest {

    private final static String SYSLOG_NAME = "NewSystemLog";

    private void openSyslogPage() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .goSystemLogPage();
    }

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
        List<WebElement> lst = new ArrayList<>();
        openSyslogPage();

        do {
            getDriver().findElement(By.xpath("//a[@href='" + SYSLOG_NAME + "/'][1]")).click();
            getDriver().findElement(By.xpath("//button[@tooltip='More actions']")).click();
            Actions actions = new Actions(getDriver());
            actions.pause(400)
                .moveToElement(getDriver()
                    .findElement(By.xpath("//a[@data-post='true']")))
                .click()
                .perform();
            getWait5().until(ExpectedConditions.alertIsPresent()).accept();

            lst = getDriver().findElements(By.className("jenkins-table__link"));
            getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();
        } while (lst.size() > 1);
        Assert.assertEquals(lst.size(), 1);
    }
}
