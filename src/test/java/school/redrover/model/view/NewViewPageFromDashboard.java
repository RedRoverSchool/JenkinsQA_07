package school.redrover.model.view;

import org.openqa.selenium.WebDriver;

import school.redrover.model.base.BaseNewViewPage;
import school.redrover.model.base.BaseViewConfigPage;

public class NewViewPageFromDashboard <ViewConfPage extends BaseViewConfigPage> extends BaseNewViewPage {

    private final ViewConfPage viewConfPage;

    public NewViewPageFromDashboard(WebDriver driver, ViewConfPage viewConfPage) {
        super(driver);
        this.viewConfPage = viewConfPage;
    }

    public NewViewPageFromDashboard<ViewConfPage> typeNewViewName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public NewViewPageFromDashboard<ListViewConfigPage> selectListViewType() {
        listViewTypeRadioButton.click();

        return new NewViewPageFromDashboard<>(getDriver(), new ListViewConfigPage(getDriver()));
    }

    public NewViewPageFromDashboard<MyViewConfigPage> selectMyViewType() {
        myViewTypeRadioButton.click();

        return new NewViewPageFromDashboard<>(getDriver(), new MyViewConfigPage(getDriver()));
    }

    public ViewConfPage clickCreateButton() {
        createButton.click();

        return viewConfPage;
    }
}
