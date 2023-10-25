package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    @Test
    public void testVersionJenkins() {
        Assert.assertEquals(
                getDriver()
                .findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"))
                .getText(),
        "Jenkins 2.414.2");
    }

    @Test
    public void testVerifyClickabilityOfRestAPILink() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        Assert.assertEquals(getDriver().getTitle(), "Remote API [Jenkins]");
    }
}
