package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodeCofigurationPage extends BasePage {

    @FindBy(xpath = "//button[@formnovalidate='formNoValidate']")
    private WebElement saveButton;

    @FindBy(name = "_.name")
    private WebElement inputName;

    public NodeCofigurationPage(WebDriver driver) {
        super(driver);
    }

    public <T> T saveButtonClick(T page) {
        saveButton.click();
        return page;
    }

    public NodeCofigurationPage clearAndInputNewName(String newName) {
        inputName.clear();
        inputName.sendKeys(newName);

        return this;
    }

}
