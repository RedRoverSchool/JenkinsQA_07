package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleProjectRenamePage extends BasePage {
    @FindBy(xpath = "//input[@name='newName']")
    private WebElement inputName;

    public FreestyleProjectRenamePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectRenamePage typeNewName(String name) {
        inputName.clear();
        inputName.sendKeys(name);

        return this;
    }

    public FreestyleProjectDetailsPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new FreestyleProjectDetailsPage(getDriver());
    }
}
