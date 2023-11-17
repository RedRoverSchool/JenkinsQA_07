package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class NewViewPage extends BasePage {
    private By nameInput = By.id("name");
    private By ViewTypeRadioButton = By.xpath("//label[@for='hudson.model.ListView']");
    private By createButton = By.name("Submit");

    public NewViewPage(WebDriver driver) {super(driver);}

    public NewViewPage typeNewViewName(String name) {
        getDriver().findElement(nameInput).click();
        getDriver().findElement(nameInput).sendKeys(name);

        return this;
    }

    public NewViewPage selectListViewType() {
        getDriver().findElement(ViewTypeRadioButton).click();

        return this;
    }

    public NewViewConfigurePage clickCreateButton() {
        getDriver().findElement(createButton).click();

        return new NewViewConfigurePage(getDriver());
    }
}
