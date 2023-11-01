package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Breadcrumb3Test extends BaseTest {

    @Test
    public void testBreadcrumbDashboardMenuItemsSameAsSideMenu() {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[text()='Dashboard']")))
                .pause(Duration.ofMillis(300))
                .perform();

        WebElement dashboardChevron = getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']//a[text()='Dashboard']/button"));
        new Actions(getDriver())
                .moveToElement(dashboardChevron, 7, 7)
                .pause(500)
                .click()
                .perform();

        List<WebElement> itemsListBreadcrumb = getDriver().findElements(By.id("tippy-3"));

        Assert.assertTrue(itemsListBreadcrumb.size() > 0);

        List<String> nameListBreadcrumb = new ArrayList<>();
        for(WebElement element : itemsListBreadcrumb) {
            nameListBreadcrumb.add(element.getText());
        }

        List<WebElement> itemsListSideMenu = getDriver().findElements(By.id("tasks"));

        Assert.assertTrue(itemsListSideMenu.size() > 0);

        List<String> nameListSideMenu = new ArrayList<>();
        for(WebElement element : itemsListSideMenu) {
            nameListSideMenu.add(element.getText());
        }

        Assert.assertEquals(nameListBreadcrumb, nameListSideMenu);
    }
}
