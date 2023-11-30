package school.redrover;

import org.bouncycastle.oer.its.etsi102941.SharedAtRequest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class ApiTest extends BaseTest {
    @Test
    public void testVerifyClickabilityOfRestAPILink() {
        String titlePage = new HomePage(getDriver())
                .clickRestApi()
                .getTitlePage();

        Assert.assertEquals(titlePage, "Remote API [Jenkins]");
    }
}
