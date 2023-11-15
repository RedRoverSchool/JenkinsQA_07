package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RestApiLink5Test extends BaseTest {
    @Test
    public void testRestApiLinkClickable() {
        getDriver().findElement(By.id("executors")).click();
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),"REST API");

    }
}
