package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testReturnWithLogo() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }
}
