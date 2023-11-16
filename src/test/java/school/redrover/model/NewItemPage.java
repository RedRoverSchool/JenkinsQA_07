package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NewItemPage extends BasePage {

    @FindBy(name = "name")
    private WebElement inputName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeItemName(String name) {
        inputName.sendKeys(name);

        return this;
    }

    public NewItemPage selectItemType(String type) {
        getDriver().findElement(By.xpath("//span[text()='" + type + "']")).click();

        return this;
    }

    public ConfigurationPage clickOk() {
        okButton.click();

        return new ConfigurationPage(getDriver());
    }
}
