package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import java.util.Arrays;

public class BreadcrumbTest extends BaseTest {

    private int isElementExistInBreadcrumb(By element) {

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            return 1;
        }

        catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    @Test
    public void testDashboardReturn () {

        final By dashboard = By.xpath("//div//li[@class='jenkins-breadcrumbs__list-item'][1]");
        final String expectedTitle = "Dashboard [Jenkins]";
        final int expectedDashboardCount = 4;

        int dashboardItemCounter = isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(By.xpath("//div//a[@href='/manage']")).click();

        dashboardItemCounter+= isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(By.xpath("//div//a[@href= 'pluginManager']/dl")).click();

        dashboardItemCounter+= isElementExistInBreadcrumb(dashboard);
        getDriver().findElement(dashboard).click();
        dashboardItemCounter+= isElementExistInBreadcrumb(dashboard);

        Assert.assertEquals(dashboardItemCounter, expectedDashboardCount);
        Assert.assertEquals(getDriver().getTitle(), expectedTitle);

    }
}
