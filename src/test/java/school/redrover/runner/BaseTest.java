package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

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
