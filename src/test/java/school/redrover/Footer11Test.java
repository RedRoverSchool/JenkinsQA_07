package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Footer11Test extends BaseTest {
    @Test
    public void testCheckTheVersion() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"))
                .getText(),"Jenkins 2.414.2");
    }
}
