package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MyViewPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(),'New Item')]/parent::a")
    private WebElement newItem;

    @FindBy(className = "addTab")
    private WebElement plusNewViewButton;

    @FindBy(xpath = "//li/a[contains(@href,'/user/admin/my-views/view/')]")
    private WebElement myViewsName;


    public MyViewPage(WebDriver driver) {super(driver);}

    public String getActiveViewName() {

        return getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
    }

    public NewItemPage clickNewItem() {
        newItem.click();
        return new NewItemPage(getDriver());
    }

    public NewViewPage clickPlusNewViewButton() {
        plusNewViewButton.click();
        return new NewViewPage(getDriver());
    }

    public String getMyViewsName() {
        return myViewsName.getText();
    }
}
