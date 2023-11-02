package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;
import static org.testng.AssertJUnit.assertTrue;

public class Breadcrumb2Test extends BaseTest {
    private boolean isBreadcrumbPresent() {
        List<WebElement> breadcrumbs = getDriver().findElements(By.id("breadcrumbBar"));
        return !breadcrumbs.isEmpty() && breadcrumbs.get(0).isDisplayed();
    }
    private boolean thisIsDashboardPage() {
        List<WebElement> dashboardElements = getDriver().findElements(By.id("main-panel"));
        return !dashboardElements.isEmpty() && dashboardElements.get(0).isDisplayed();
    }

    private void createTask() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testBreadcrumbOnNewItemPage() {
        createTask();
        assertTrue(isBreadcrumbPresent());
    }

    @Test
    public void testBreadcrumbOnMenuPages() {
        List<By> pages = List.of(
                By.xpath("//*[@id=\"tasks\"]/div[2]/span/a"),
                By.xpath("//*[@id=\"tasks\"]/div[3]/span/a"),
                By.xpath("//*[@id=\"tasks\"]/div[4]/span/a"),
                By.xpath("//*[@id=\"page-header\"]/div[3]/a[1]"));
        for (By locator: pages) {
            getDriver().findElement(locator).click();
            assertTrue(isBreadcrumbPresent());
        }
    }

    @Test
    public void testReturnToDashboardOnDifferentPages() {
        createTask();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        assertTrue(thisIsDashboardPage());
    }
    @Ignore
    @Test
    public void testReturnToDashboardFromMenuPages() {
        List<By> menuPages = List.of(
                By.xpath("//*[@id=\"tasks\"]/div[2]/span/a"),
                By.xpath("//*[@id=\"tasks\"]/div[3]/span/a"),
                By.xpath("//*[@id=\"tasks\"]/div[4]/span/a"),
                By.xpath("//*[@id=\"page-header\"]/div[3]/a[1]"));

        for (By locator : menuPages) {
            getDriver().findElement(locator).click();
            assertTrue(thisIsDashboardPage());
        }
    }
}