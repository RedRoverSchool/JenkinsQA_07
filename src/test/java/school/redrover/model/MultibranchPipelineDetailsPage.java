package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import org.openqa.selenium.WebElement;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;


public class MultibranchPipelineDetailsPage extends BasePage {

    @FindBy(xpath = "//span[@class='task-link-wrapper ']")
    private List<WebElement> sidebarMenuTasksList;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadcrumbChain;

    @FindBy(tagName = "h1")
    private WebElement pageTitle;

    public MultibranchPipelineDetailsPage(WebDriver driver) {
        super(driver);
    }


    public String getTitle() {
        return pageTitle.getText();
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
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public List<String> getNameOfTasksFromSidebarMenu() {
        return TestUtils.getTextOfWebElements(getWait2().until(
                ExpectedConditions.visibilityOfAllElements(sidebarMenuTasksList)));
    }
}
