package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SystemLogPage extends BasePage {

    @FindBy(xpath = "//a[@href = 'new']")
    private WebElement buttonAddRecorder;

    public SystemLogPage(WebDriver driver) { super(driver); }

    public NewLogRecorderPage clickAddRecorder() {
        buttonAddRecorder.click();

        return new NewLogRecorderPage(getDriver());
    }

    public ConfigureLogRecorderPage clickGearIcon(String nameLogRecoder) {
        getDriver().findElement(By.xpath("//a[@href = '" + nameLogRecoder + "/configure']")).click();

        return new ConfigureLogRecorderPage(getDriver());
    }
}
