package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class MyViewPage extends BasePage<MyViewPage> {

    @FindBy(xpath = "//span[contains(text(),'New Item')]/parent::a")
    private WebElement newItem;

    @FindBy(className = "addTab")
    private WebElement newViewButton;

    @FindBy(xpath = "//div/ol/li/a[contains(@href,'/user/admin/my-views/view')]")
    private WebElement myViewName;

    @FindBy(xpath = "//span[text()='Delete View']")
    private WebElement deleteView;

    @FindBy(css = ".tab > a")
    private List<WebElement> listOfViews;

    @FindBy(css = "a[href='api/']")
    private WebElement restApiButton;

    public MyViewPage(WebDriver driver) {super(driver);}

    public String getActiveViewName() {

        return getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
    }

    public NewItemPage clickNewItem() {
        newItem.click();
        return new NewItemPage(getDriver());
    }

    public NewViewPage clickNewViewButton() {
        newViewButton.click();
        return new NewViewPage(getDriver());
    }

    public String getMyViewName(){
        return myViewName.getText();
    }

    public MyViewPage clickDeleteView(){
        deleteView.click();

        return this;
    }

    public boolean isViewExists(String viewName) {

        return listOfViews.stream().anyMatch(element -> element.getText().contains(viewName));
    }

    public RestApiPage goRestApi() {
        restApiButton.click();

        return new RestApiPage(getDriver());
    }
}
