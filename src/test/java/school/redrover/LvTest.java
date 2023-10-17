package school.redrover;
//import jdk.internal.access.JavaIOFileDescriptorAccess;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.JenkinsUtils;

public class LvTest extends BaseTest {
    @Test
    public void MainTitle(){

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Web form");

    }
}
