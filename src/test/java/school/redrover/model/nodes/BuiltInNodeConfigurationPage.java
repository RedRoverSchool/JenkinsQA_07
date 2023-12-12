package school.redrover.model.nodes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class BuiltInNodeConfigurationPage extends BasePage<BuiltInNodeConfigurationPage> {

    public BuiltInNodeConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[contains(@name, 'numExecutors')]")
    private WebElement numberOfExecutorsField;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement saveButton;

    public BuiltInNodeDetailPage inputNumbersOfExecutors(int number) {
        numberOfExecutorsField.clear();
        numberOfExecutorsField.sendKeys(String.valueOf(number));
        saveButton.click();

        return new BuiltInNodeDetailPage(getDriver());
    }
}
