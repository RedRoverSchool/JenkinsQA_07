package school.redrover.model.views;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class GlobalViewConfigPage extends BasePage<GlobalViewConfigPage> {

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inputName;

    public GlobalViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public GlobalViewConfigPage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public GlobalViewPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new GlobalViewPage(getDriver());
    }
}
