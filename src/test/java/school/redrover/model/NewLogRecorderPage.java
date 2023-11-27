package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NewLogRecorderPage extends BasePage {

    @FindBy(name = "name")
    private WebElement inputName;

    @FindBy(name = "Submit")
    private WebElement buttonCreate;

    public NewLogRecorderPage(WebDriver driver) {
        super(driver);
    }

    public NewLogRecorderPage typeName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public ConfigureLogRecorderPage clickCreate() {
        buttonCreate.click();

        return new ConfigureLogRecorderPage(getDriver());
    }
}
