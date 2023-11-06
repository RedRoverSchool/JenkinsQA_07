package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.AssertJUnit.assertTrue;

public class BreadCrumb3Test extends BaseTest {

    @Test
    private void testReturnOnMainPageFromAnyPageByClickDashboard () {

        getDriver().findElement(By.xpath("//body/div[@id='page-body']/div[@id='side-panel']/div[@id='tasks']/div[2]/span[1]/a[1]")).click();
        getDriver().findElement(By.xpath("//body/div[@id='breadcrumbBar']/ol[@id='breadcrumbs']/li[1]/a[1]")).click();

    }
}
