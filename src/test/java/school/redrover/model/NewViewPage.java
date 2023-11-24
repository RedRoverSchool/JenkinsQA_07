package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NewViewPage extends BasePage {
    private By nameInput = By.id("name");
    private By ViewTypeRadioButton = By.xpath("//label[@for='hudson.model.ListView']");

    @FindBy(xpath = "//label[@for='hudson.model.MyView']")
    private WebElement myViewTypeRadioButton;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement createButton;

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
        createButton.click();

        return new NewViewConfigurePage(getDriver());
    }

    public NewViewConfigurePage clickMyViewRadioButton() {
        myViewTypeRadioButton.click();
        return new NewViewConfigurePage(getDriver());
    }

}
