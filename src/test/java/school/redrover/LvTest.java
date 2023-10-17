package school.redrover;

import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.JenkinsUtils;

public class LvTest extends BaseTest {
    @Test
    public void testSearch() throws InterruptedException {

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Добро пожаловать в Jenkins!");
    }
}
