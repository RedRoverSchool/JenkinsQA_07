package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Breadcrumb5Test extends BaseTest {

    private final String MAIN_PAGE = "Main Page";

    private void createMainPage() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).sendKeys(MAIN_PAGE);
        getDriver().findElement(By.xpath("//button[@name='Submit' and contains(@class, 'jenkins-button jenkins-button--primary')]")).click();
    }

    private void searchDashboardOnBreadcrumbBar() {

        List<WebElement> breadcrumbItems = getDriver().findElement(By.id("breadcrumbBar")).findElements(By.cssSelector("li.jenkins-breadcrumbs__list-item"));

        for (WebElement item : breadcrumbItems) {
            if (item.getText().equals("Dashboard")) {
                getDriver().findElement(By.xpath("//a[@href='/' and contains(@class, 'model-link')]")).click();
                break;
            }
        }
    }

    private int isElementExistInBreadcrumb(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            return 1;
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    @Test
    public void testOnBuiltInNodePageAndReturnOnMainPage() {

        createMainPage();

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
        getDriver().findElement(By.xpath("//a[@href = '../computer/(built-in)/']")).click();

        searchDashboardOnBreadcrumbBar();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(MAIN_PAGE));
    }

    @Test (dependsOnMethods = "testOnBuiltInNodePageAndReturnOnMainPage")
    public void testOnConfigPageAndReturnOnMainPage() {

        createMainPage();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("Project Name");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        searchDashboardOnBreadcrumbBar();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(MAIN_PAGE));
    }

    @Test (dependsOnMethods = "testOnConfigPageAndReturnOnMainPage")
    public void testOnAdminPageAndReturnOnMainPage() {

        createMainPage();

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'user/admin/']")).click();

        searchDashboardOnBreadcrumbBar();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(MAIN_PAGE));
    }

    @Test (dependsOnMethods = "testOnAdminPageAndReturnOnMainPage")
    public void testDashboardReturn() {

        final By dashboard = By.xpath("//div//li[@class='jenkins-breadcrumbs__list-item'][1]");
        final String expectedTitle = "Dashboard [Jenkins]";
        final int expectedDashboardCount = 4;

        int dashboardItemCounter = isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(By.xpath("//div//a[@href='/manage']")).click();

        dashboardItemCounter += isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(By.xpath("//div//a[@href= 'pluginManager']/dl")).click();

        dashboardItemCounter += isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(dashboard).click();
        dashboardItemCounter += isElementExistInBreadcrumb(dashboard);

        Assert.assertEquals(dashboardItemCounter, expectedDashboardCount);
        Assert.assertEquals(getDriver().getTitle(), expectedTitle);
    }
}