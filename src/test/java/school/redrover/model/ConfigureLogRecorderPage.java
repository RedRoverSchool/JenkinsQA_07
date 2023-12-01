package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ConfigureLogRecorderPage extends BasePage {

    @FindBy(xpath = "(//div[@class='setting-main'])[1]/input")
    private WebElement name;

    @FindBy(xpath = "//div[@class='repeated-container']/button")
    private WebElement addButton;

    @FindBy(xpath = "(//input[@name='_.name'])[last()]")
    private WebElement lastLoggerField;

    @FindBy(xpath = "//div[@name='loggers'])[last()]//li[1]")
    private WebElement loggerDropDownList;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "(//select)[last()]/option[@selected='true']")
    private WebElement selectedLogLevel;

    @FindBy(xpath = "//a[@href='/manage/log/']")
    private WebElement systemLog;

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[5]/a")
    private WebElement backToSystemLogButton;

    public ConfigureLogRecorderPage(WebDriver driver) {
        super(driver);
    }

    public ConfigureLogRecorderPage clickAdd() {
        addButton.click();

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
            new Actions(getDriver())
                    .pause(300)
                    .moveToElement(saveButton)
                    .click()
                    .perform();

        return new LogRecordersDetailsPage(getDriver());
    }

    public SystemLogPage clickSystemLog(){
        systemLog.click();

        return new SystemLogPage(getDriver());
    }

    public SystemLogPage backToSystemLog() {
        backToSystemLogButton.click();

        return new SystemLogPage(getDriver());
    }

    public List<String> getLoggersAndLevelsSavedList() {

        List<String> resultList = List.of(
                name.getAttribute("value"),
                lastLoggerField.getAttribute("value"),
                selectedLogLevel.getText());

        return resultList;
    }

    public ConfigureLogRecorderPage changeLogger(String name) {
        lastLoggerField.clear();
        lastLoggerField.sendKeys(name);

        return this;
    }

    public ConfigureLogRecorderPage clickDeleteLogger() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@name='loggers']/div/button"));
        for (WebElement el: elementList) {
            el.click();
        }

        return this;
    }

    public int getEmptyLoggers() {
         int elementList = getDriver().findElements(By.name("loggers")).size();

        return elementList;
    }
}
