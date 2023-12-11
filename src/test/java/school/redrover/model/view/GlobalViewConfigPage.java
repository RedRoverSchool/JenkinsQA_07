package school.redrover.model.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseViewConfigPage;

public class GlobalViewConfigPage extends BaseViewConfigPage {

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inputName;

    @FindBy(xpath = "//span[text()='Delete View']")
    private WebElement deleteView;

    public GlobalViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public GlobalViewConfigPage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public ViewPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new ViewPage(getDriver());
    }
}
