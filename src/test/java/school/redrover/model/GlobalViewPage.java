package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class GlobalViewPage extends BasePage<GlobalViewPage> {

    public GlobalViewPage(WebDriver driver) {
        super(driver);
    }

    public GlobalViewConfigPage clickEditView() {
        getDriver().findElement(By.xpath("//a[contains(@href,'/configure')]")).click();

        return new GlobalViewConfigPage(getDriver());
    }
}
