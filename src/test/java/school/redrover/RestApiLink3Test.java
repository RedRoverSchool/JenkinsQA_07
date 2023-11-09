package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
public class RestApiLink3Test extends BaseTest {

    @Ignore
    @Test
    public void testVerifyRestApiLink() {
        final String apiLink = "REST API";

        WebElement restApiLink = getDriver().findElement(By.xpath("//a[contains(@href,'api')]"));
        restApiLink.click();
        WebElement restApiTittle = getDriver().findElement(By.xpath("//h1[1]"));

        Assert.assertEquals(restApiTittle.getText(), apiLink);
    }
}
