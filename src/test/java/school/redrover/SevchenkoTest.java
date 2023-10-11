package school.redrover;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@Ignore
public class SevchenkoTest {
    // TODO: 05.10.2023 coorected class name. 
    //Search elements //*[@id='mainbar']//a[@href='/questions/tagged/slf4j']
    //Show elements//div[@class='s-pagination site1 themed page-sizer float-right']//a[.='15']

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://stackoverflow.com/");
            String title = driver.getTitle();
            Assert.assertEquals(title.substring(0, 14), "Stack Overflow");
        } finally {
            driver.quit();
        }
    }
}


