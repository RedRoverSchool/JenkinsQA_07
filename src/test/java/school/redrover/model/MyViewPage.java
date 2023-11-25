package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MyViewPage extends BasePage {


    @FindBy(xpath = "//a[contains(@href,'me')]")
    private WebElement setUpAgent;

    @FindBy(xpath = "//span[contains(text(),'New Item')]/parent::a")
    private WebElement newItem;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folderCategory;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    public MyViewPage(WebDriver driver) {super(driver);}

    public String getActiveViewName() {

        return getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
    }

    public MyViewPage clickNewItem() {
        newItem.click();
        return this;
    }

    public MyViewPage enterItemName(String fieldName) {
        nameField.sendKeys(fieldName);
        return this;
    }

    public MyViewPage clickFolderCategory() {
        folderCategory.click();
        return this;
    }

    public MyViewPage clickOkButton() {
        okButton.click();
        return this;
    }
}
