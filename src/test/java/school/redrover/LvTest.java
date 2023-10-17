package school.redrover;
//import jdk.internal.access.JavaIOFileDescriptorAccess;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LvTest extends BaseTest {
    @Test
    public void titleTest(){

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Web form");

    }
}
