package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ConfigureLogRecorderPage extends BasePage {

    @FindBy(xpath = "(//div[@class = 'setting-main'])[1]/input")
    private WebElement name;

    @FindBy(xpath = "//div[@class='repeated-container']/button")
    private WebElement buttonAdd;

    @FindBy(xpath = "(//input[@name = '_.name'])[last()]")
    private WebElement lastLoggerField;

    @FindBy(xpath = "//div[@name='loggers'])[last()]//li[1]")
    private WebElement loggerDropDownList;

    @FindBy(xpath = "(//select)[last()")
    private WebElement lastLogLevel;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buttonSave;

    @FindBy(xpath = "(//select)[last()]/option[@selected='true']")
    private WebElement selectedLogLevel;

    @FindBy(xpath = "//a[@href='/manage/log/']")
    private WebElement breadcrumbSystemLog;

    public ConfigureLogRecorderPage(WebDriver driver) {
        super(driver);
    }

    public ConfigureLogRecorderPage clickAdd() {
        buttonAdd.click();

        return this;
    }

    public ConfigureLogRecorderPage chooseLastLogger(String loggerName) {
       WebElement lastLoggerField = getDriver().findElement(By.xpath("(//input[@name = '_.name'])[last()]"));
       WebElement loggerDropDownList = getDriver().findElement(By.xpath("(//div[@name = 'loggers'])[last()]//li[1]"));

        new Actions(getDriver())
                .moveToElement(lastLoggerField)
                .click()
                .sendKeys(loggerName)
                .pause(500)
                .moveToElement(loggerDropDownList)
                .click()
                .perform();

        return this;
    }

    public ConfigureLogRecorderPage chooseLastLogLevel(String level) {
        WebElement lastLogLevel = getDriver().findElement(By.xpath("(//select)[last()]"));
        Select select = new Select(lastLogLevel);
        select.selectByVisibleText(level);

        return this;
    }

    public LogRecordersDetailsPage clickSave() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return new LogRecordersDetailsPage(getDriver());
    }

    public SystemLogPage clickSystemLog(){
        breadcrumbSystemLog.click();

        return new SystemLogPage(getDriver());
    }

    public List<String> getLoggersAndLevelsSavedList() {
        List<String> resultlist = List.of(
                name.getAttribute("value"),
                lastLoggerField.getAttribute("value"),
                selectedLogLevel.getText());

        return resultlist;
    }

    public ConfigureLogRecorderPage clickDeleteLogger() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@name='loggers']/div/button"));
        for (WebElement el: elementList) {
            getWait2().until(ExpectedConditions.elementToBeClickable(el)).click();
        }

        return this;
    }

    public boolean getLogHistoryEmpty() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@name='loggers']"));

        return elementList.isEmpty();
    }
}
