package school.redrover.model.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.BreadcrumbPage;
import school.redrover.model.OrganizationFolderRenamePage;
import school.redrover.model.RenamePage;

public abstract class BaseProjectPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenu;

    @FindBy(name = "Submit")
    private WebElement disableButton;

    @FindBy(id = "enable-project")
    private WebElement disableMessage;

    @FindBy(xpath = "//div[@id='breadcrumbBar']//li/a[contains(@href, '/job')]")
    private WebElement jobDropdownBreadcrumb;

    @FindBy(xpath = "//div[@id='breadcrumbBar']//li/a[contains(@href, '/job')]/button")
    private WebElement jobChevronBreadcrumb;

    @FindBy(xpath = "//div[@class='tippy-content']//a[contains(@href, '/confirm-rename')]")
    private WebElement renameSubmenuBreadcrumb;

    public BaseProjectPage(WebDriver driver) {super(driver);}

    public String getProjectName() {

        return projectName.getText();
    }

    public <ProjectRenamePage extends RenamePage> ProjectRenamePage clickRenameOption(ProjectRenamePage projectRenamePage) {
        renameSubmenu.click();

        return projectRenamePage;
    }

    public BaseProjectPage clickDisableButton() {
        disableButton.click();

        return this;
    }

    public String getDisabledMessageText() {

        return disableMessage.getText().substring(0, 46);
    }

    public BaseProjectPage getJobDropdownMenuBreadcrumb() {

        new Actions(getDriver()).moveToElement(jobDropdownBreadcrumb)
                .scrollByAmount(jobDropdownBreadcrumb.getSize().getWidth()/2, jobDropdownBreadcrumb.getSize().getHeight()/2)
                .perform();

        new Actions(getDriver())
                .moveToElement(jobChevronBreadcrumb)
                .pause(500)
                .perform();
        jobChevronBreadcrumb.sendKeys(Keys.RETURN);

        return this;
    }

    public <ProjectRenamePage extends RenamePage> ProjectRenamePage clickRenameOptionDropdownBreadcrumb(ProjectRenamePage projectRenamePage) {
        getWait5().until(ExpectedConditions.visibilityOf(renameSubmenuBreadcrumb)).click();

        return projectRenamePage;
    }
}
