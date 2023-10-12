package school.redrover.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;


@Listeners({FilterForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        driver = new ChromeDriver(ProjectUtils.chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        driver.quit();
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
