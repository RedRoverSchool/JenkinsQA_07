package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.*;

public class SystemLogTest extends BaseTest {

    private final static String SYSLOG_NAME = "NewSystemLog";

    private void openSyslogPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//a[@href='log']")));
        getDriver().findElement(By.xpath("//a[@href='log']")).click();
    }

    @Test
    public void testCreateCustomLogRecorder() {
        openSyslogPage();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.cssSelector("input[checkurl='checkNewName']")))
                .click()
                .perform();
        getDriver().findElement(By.cssSelector("input[checkurl='checkNewName']")).sendKeys(SYSLOG_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='logRecorders']/tbody/tr[2]/td[1]/a"))
                .getText(), SYSLOG_NAME);
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
        Assert.assertEquals(lst.size(),1);
    }

    @Test
    public void testDeleteAllCustomLogRecorders() {
        List<WebElement> lst = new ArrayList<>();
        openSyslogPage();

        lst = getDriver().findElements(By.className("jenkins-table__link"));
        if(lst.size() > 1) {
            do {
                lst.get(1).click();
                getDriver().findElement(By.xpath("//button[@tooltip='More actions']")).click();
                Actions actions = new Actions(getDriver());
                actions.pause(400)
                        .moveToElement(getDriver()
                        .findElement(By.xpath("//a[@data-post='true']")))
                        .click()
                        .perform();
                getWait5().until(ExpectedConditions.alertIsPresent()).accept();
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();
                lst = getDriver().findElements(By.className("jenkins-table__link"));
            } while (lst.size() > 1);
        }
        Assert.assertEquals(lst.size(),1);
    }
}
