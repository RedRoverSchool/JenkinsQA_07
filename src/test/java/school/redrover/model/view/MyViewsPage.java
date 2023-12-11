package school.redrover.model.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.NewItemPage;
import school.redrover.model.RestApiPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseViewConfigPage;

import java.util.List;

public class MyViewsPage extends BasePage<MyViewsPage> {

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

    public MyViewsPage(WebDriver driver) {super(driver);}

    public String getActiveViewName() {

        return getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
    }

    public NewItemPage clickNewItem() {
        newItem.click();
        return new NewItemPage(getDriver());
    }

    public NewViewPageFromMyViewsPage<?> clickNewViewButton() {
        newViewButton.click();
        return new NewViewPageFromMyViewsPage<>(getDriver(), new BaseViewConfigPage(getDriver()) {
        });
    }

    public String getMyViewName(){
        return myViewName.getText();
    }

    public MyViewsPage acceptAlert() {
        getWait2().until(ExpectedConditions.alertIsPresent()).accept();

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
