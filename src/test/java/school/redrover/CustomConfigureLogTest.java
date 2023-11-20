package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.*;

public class CustomConfigureLogTest extends BaseTest {

    private final static String SYSLOG_NAME = "New";
    private final static String NEW_NAME = "Information";
    private final static String LEVEL_LOG = "INFO";

    private void goSystemLog() {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href ='log']")).click();
    }

    private void createCustomLog() {

        goSystemLog();

        getDriver().findElement(By.xpath("//a[@href = 'new']")).click();
        getDriver().findElement(By.name("name")).sendKeys(SYSLOG_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/log/']")).click();
    }

    @Test
    public void testConfigureCustomLogRecorder() {
        createCustomLog();

        getDriver().findElement(By.xpath("//a[@href = '" + SYSLOG_NAME + "/configure']")).click();

        WebElement nameField = getDriver().findElement(By.xpath("//input[@value = '" + SYSLOG_NAME + "']"));
        nameField.clear();
        nameField.sendKeys(NEW_NAME);

        getDriver().findElement(By.cssSelector("div.repeated-container > button")).click();

        WebElement moveLoggerField = getDriver().findElement(By.xpath("(//input[@name = '_.name'])[last()]"));
        WebElement moveLogger = getDriver().findElement(By.xpath("(//div[@name = 'loggers'])[last()]//li[1]"));
        WebElement moveLogLevel = getDriver().findElement(By.xpath("(//select)[last()]"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(moveLoggerField)
                .click()
                .sendKeys("c")
                .pause(500)
                .moveToElement(moveLogger)
                .click()
                .perform();

        Select select = new Select(moveLogLevel);
        select.selectByVisibleText(LEVEL_LOG);

        List <String> listBeforeSave = List.of(
                NEW_NAME,
                moveLoggerField.getAttribute("value")
        );

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'configure']")).click();

        List <String> listAfterSave = List.of(
                getDriver().findElement(By.xpath("//input[@value = '" + NEW_NAME + "']")).getAttribute("value"),
                getDriver().findElement(By.xpath("(//input[@name = '_.name'])[last()]")).getAttribute("value")
        );

        Assert.assertEquals(listAfterSave, listBeforeSave);
    }
}
