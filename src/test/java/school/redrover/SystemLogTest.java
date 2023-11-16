package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SystemLogTest extends BaseTest {

    private final static String SYSLOG_NAME = "NewSystemLog";

    @Test
    public void testCreateCustomLogRecorder() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).click();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//*[@id='main-panel']/section[4]/div/div[2]/a")));
        getDriver().findElement(By.xpath("//*[@id='main-panel']/section[4]/div/div[2]/a")).click();

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div/div[2]/a[1]")).click();

        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.cssSelector("input[checkurl='checkNewName']")))
                .click();
        getDriver().findElement(By.cssSelector("input[checkurl='checkNewName']")).sendKeys(SYSLOG_NAME);
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[2]/div/button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id=\"logRecorders\"]/tbody/tr[2]/td[1]/a"))
                .getText(),SYSLOG_NAME);
    }
}
