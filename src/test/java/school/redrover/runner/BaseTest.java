package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;


@Listeners({FilterForTests.class})
public abstract class BaseTest {

    private WebDriver webDriver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        webDriver = new ChromeDriver(ProjectUtils.chromeOptions);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        webDriver.quit();
    }

    protected WebDriver getDriver() {
        return webDriver;
    }
}
