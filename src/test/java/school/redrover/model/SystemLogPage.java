package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class SystemLogPage extends BasePage<SystemLogPage> {

    @FindBy(xpath = "//a[@href = 'new']")
    private WebElement addRecorderButton;

    @FindBy(xpath = "//*[@id='logRecorders']/tbody/tr[2]/td[1]/a")
    private WebElement customLogName;

    @FindBy(xpath = "//*[@id='logRecorders']/tbody/tr[2]/td[1]/a")
    private WebElement customLogRecorderName;

    public SystemLogPage(WebDriver driver) { super(driver); }

    public NewLogRecorderPage clickAddRecorder() {
        addRecorderButton.click();

        return new NewLogRecorderPage(getDriver());
    }

    public ConfigureLogRecorderPage clickGearIcon(String nameLogRecoder) {
        getDriver().findElement(By.xpath("//a[@href = '" + nameLogRecoder + "/configure']")).click();

        return new ConfigureLogRecorderPage(getDriver());
    }

    public String getNameCustomLog() {
        String logName;
        logName = customLogName.getText();

        return logName;
    }

    public CustomLogRecorderPage clickCustomLogRecorderName() {
        customLogName.click();

        return new CustomLogRecorderPage(getDriver());
    }

    public List<WebElement> getListLogRecorders() {
        List<WebElement> list = new ArrayList<>();
        list = getDriver().findElements(By.className("jenkins-table__link"));

        return list;
    }
}
