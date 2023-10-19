package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Ignore
public class BezverkhovaTest extends BaseTest {

    @Test
    public void testJenkinsStatus() {
        getDriver().findElement(By.className("model-link")).click();

        String iconText = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        assertEquals(iconText, "admin");

        String statusIconText = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();
        assertEquals(statusIconText, "Jenkins User ID: admin");
    }
}
