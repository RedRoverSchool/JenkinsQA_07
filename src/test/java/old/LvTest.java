package old;
//import jdk.internal.access.JavaIOFileDescriptorAccess;
import org.testng.annotations.Ignore;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;


@Ignore
public class LvTest extends BaseTest {
    @Test
    public void titleTest(){

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Web form");

    }
}
