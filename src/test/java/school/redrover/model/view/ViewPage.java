package school.redrover.model.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseViewPage;

public class ViewPage extends BaseViewPage<ViewPage> {

    @FindBy(linkText = "Edit View")
    private WebElement editViewLink;

    @FindBy(linkText = "add some existing jobs")
    private WebElement addJobsLinkFromMainPanel;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage clickEditView() {
        editViewLink.click();

        return new ListViewConfigPage(getDriver());
    }

    public ListViewConfigPage clickAddJobsFromMainPanel() {
        addJobsLinkFromMainPanel.click();

        return new ListViewConfigPage(getDriver());
    }
}
