package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;


public class MultibranchPipelineDetailsPage extends BaseProjectPage<MultibranchPipelineConfigurationPage> {

    @FindBy(xpath = "//span[@class='task-link-wrapper ']")
    private List<WebElement> sidebarMenuTasksList;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadcrumbChain;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameButton;

    @FindBy(linkText = "Configure")
    private WebElement configureLink;

    @FindBy (xpath = "//a[contains(@href, 'delete')]")
    private WebElement buttonDelete;

    @FindBy(xpath = "//button[contains(text(), 'Disable Multibranch Pipeline')]")
    private WebElement disableButton;

    @FindBy(xpath = "//button[contains(text(), 'Enable')]")
    private WebElement enableButton;

    @FindBy(id = "enable-project")
    private WebElement disabledStatusMessage;

    public MultibranchPipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultibranchPipelineConfigurationPage createConfigurationPage() {
        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public List<String> getBreadcrumbChain() {
        List<String> breadcrumb = new ArrayList<>();
        for (WebElement element : breadcrumbChain) {
            breadcrumb.add(element.getText());
        }
        return breadcrumb;
    }

    public List<String> getTasksText() {
        WebElement parentElement = getDriver().findElement(By.xpath("//div[@id='tasks']"));
        List<WebElement> childElements = parentElement.findElements(By.xpath("./*"));

        List<String> list = new ArrayList<>();
        for (int i = 1; i <= childElements.size(); i++) {
            list.add(getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + i + "]")).getText());
        }

        return list;
    }

    public MultibranchPipelineRenamePage clickRename() {
        renameButton.click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public List<String> getNameOfTasksFromSidebarMenu() {
        return TestUtils.getTextOfWebElements(getWait2().until(
                ExpectedConditions.visibilityOfAllElements(sidebarMenuTasksList)));
    }

    public MultibranchPipelineDetailsPage clickDisable() {
        disableButton.click();

        return this;
    }

    public MultibranchPipelineDetailsPage clickEnable() {
        enableButton.click();

        return this;
    }

    public String getDisableStatusMessage() {
        return disabledStatusMessage.getText();
    }


    public MultibranchPipelineDeletePage clickButtonDelete() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buttonDelete)).click();
        buttonDelete.click();

        return new MultibranchPipelineDeletePage(getDriver());
    }

    public String getDisableButtonText() {
        return disableButton.getText();
    }
}
