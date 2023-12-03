package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NewViewPage extends BasePage {
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(xpath = "//label[@for='hudson.model.ListView']")
    private WebElement listViewTypeRadioButton;

    @FindBy(xpath = "//div/label[@for='hudson.model.MyView']")
    private WebElement myViewTypeRadioButton;

    @FindBy(name = "Submit")
    private WebElement createButton;

    @FindBy(xpath = "//div/label[@for='hudson.model.ProxyView']")
    private WebElement includeGlobalViewTypeRadioBTN;

    public NewViewPage(WebDriver driver) {super(driver);}

    public NewViewPage typeNewViewName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public NewViewPage selectListViewType() {
        listViewTypeRadioButton.click();

        return this;
    }

    public NewViewPage selectMyViewType() {
        myViewTypeRadioButton.click();

        return this;
    }

    public <T> T clickCreateButton(T page) {
        createButton.click();

        return page;
    }

    public NewViewPage clickIncludeGlobalViewTypeRadioBTN() {
        includeGlobalViewTypeRadioBTN.click();

        return new NewViewPage(getDriver());
    }
}
