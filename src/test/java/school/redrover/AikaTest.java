package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import static org.openqa.selenium.support.locators.RelativeLocator.with;


public class AikaTest extends BaseTest {

    @Test
    public void testAdminUserDisplayed() {
        getDriver().findElement(By.xpath("//span[text()='People']/parent::a")).click();
        Assert.assertTrue(getDriver().findElement(By.linkText("admin")).isDisplayed());
    }

    @Test
    public void testVerifyJenkinsVersion() {
        String  expectedJenkinsVersion = "2.414.2";

        String jenkinsVersion = getDriver().findElement(By.xpath("//button[contains(text(), 'Jenkins')]")).getText();
        Assert.assertEquals(jenkinsVersion.split(" ")[1], expectedJenkinsVersion);
    }

    @Test
    public void testClickingOnDashboardOpensDashboard() {

        WebElement newItem = getDriver().findElement(By.xpath("//div[@id='tasks']//a[1]"));
        newItem.click();

        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }
}
