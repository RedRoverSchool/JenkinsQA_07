package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


@Ignore
public class SevchenkoTest extends BaseTest {
    //Search elements //*[@id='mainbar']//a[@href='/questions/tagged/slf4j']
    //Show elements//div[@class='s-pagination site1 themed page-sizer float-right']//a[.='15']

    @Test
    public void testSearch()  {
        getDriver().get("https://stackoverflow.com/");
        String title = getDriver().getTitle();
        Assert.assertEquals(title.substring(0, 14), "Stack Overflow");
    }
}


