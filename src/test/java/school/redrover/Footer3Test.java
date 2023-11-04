package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Footer3Test extends BaseTest {
    private final String jenkinsExpectedVersion = "Jenkins 2.414.2";

    public WebElement jenkinsVersionButton() {
        return getDriver().findElement(By.xpath("//button[@type = 'button']"));
    }

    @Test
    public void testJenkinsVersion() {

        Assert.assertEquals(jenkinsVersionButton().getText(), jenkinsExpectedVersion);
    }
}
