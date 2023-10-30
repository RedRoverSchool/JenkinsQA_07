package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RestApiLink2Test extends BaseTest {

    @Test
    public void testRestApiLinkRedirection() {
      getDriver().findElement(By.xpath("//a[@href='api/']")).click();

      Assert.assertEquals(
        getDriver().getTitle(),
        "Remote API [Jenkins]"
      );

      Assert.assertEquals(
        getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
        "REST API"
      );
    }
}
