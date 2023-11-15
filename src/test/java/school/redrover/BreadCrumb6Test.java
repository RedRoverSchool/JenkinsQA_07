package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BreadCrumb6Test extends BaseTest {

   @Test
    public void testNavigateToDashboard () {
        getDriver().findElement(By.xpath("//span[text() = 'People']"));
        getDriver().findElement(By.xpath("//div[@id = 'breadcrumbBar']//a[@href ='/']")).click();

        String title = getDriver().getTitle();

        Assert.assertEquals(title, "Dashboard [Jenkins]");
    }

}
