package school.redrover.model.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseNewViewPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseViewConfigPage;

public class NewViewPageFromMyViewsPage<ViewConfPage extends BaseViewConfigPage> extends BaseNewViewPage {

    @FindBy(xpath = "//div/label[@for='hudson.model.ProxyView']")
    private WebElement includeGlobalViewTypeRadioBTN;

    private final ViewConfPage viewConfPage;

    public NewViewPageFromMyViewsPage(WebDriver driver, ViewConfPage viewConfPage) {
        super(driver);
        this.viewConfPage = viewConfPage;
    }

    public ViewPage createMyViewPage() {
        return new ViewPage(getDriver());
    }

    public ListViewConfigPage createListViewConfigPage() {
        return new ListViewConfigPage(getDriver());
    }

    public NewViewPageFromMyViewsPage<ViewConfPage> typeNewViewName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public NewViewPageFromMyViewsPage<ListViewConfigPage> selectListViewType() {
        listViewTypeRadioButton.click();

        return new NewViewPageFromMyViewsPage<>(getDriver(), new ListViewConfigPage(getDriver()));
    }

    public NewViewPageFromMyViewsPage<MyViewConfigPage> selectMyViewType() {
        myViewTypeRadioButton.click();

        return new NewViewPageFromMyViewsPage<>(getDriver(), new MyViewConfigPage(getDriver()));
    }

    public NewViewPageFromMyViewsPage<GlobalViewConfigPage> clickIncludeGlobalViewTypeRadioBTN() {
        includeGlobalViewTypeRadioBTN.click();

        return new NewViewPageFromMyViewsPage<>(getDriver(), new GlobalViewConfigPage(getDriver()));
    }

    public ViewConfPage clickCreateButton() {
        createButton.click();

        return viewConfPage;
    }
}
