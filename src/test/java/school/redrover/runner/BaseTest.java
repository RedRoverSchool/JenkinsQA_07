package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import school.redrover.runner.order.OrderForTests;
import school.redrover.runner.order.OrderUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    private void startDriver() {
        ProjectUtils.log("Browser open");

        driver = ProjectUtils.createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void clearData() {
        ProjectUtils.log("Clear data");
        JenkinsUtils.clearData();
    }

    private void loginWeb() {
        ProjectUtils.log("Login");
        JenkinsUtils.login(driver);
    }

    private void getWeb() {
        ProjectUtils.log("Get web page");
        ProjectUtils.get(driver);
    }

    private void stopDriver() {
        try {
            JenkinsUtils.logout(driver);
        } catch (Exception ignore) {}

        closeDriver();
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            ProjectUtils.log("Browser closed");
        }
    }

    @BeforeClass
    protected void beforeClass() {
        methodsOrder = OrderUtils.createMethodsOrder(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method) || methodsOrder.isGroupFinished(method)) {
                clearData();
                startDriver();
                getWeb();
                loginWeb();
            } else {
                getWeb();
            }
        } catch (Exception e) {
            closeDriver();
            throw new RuntimeException(e);
        } finally {
            methodsOrder.markAsInvoked(method);
        }
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (ProjectUtils.isServerRun() && !testResult.isSuccess()) {
            ProjectUtils.takeScreenshot(driver, method.getName(), this.getClass().getName());
        }

        if ((!ProjectUtils.isServerRun() && (testResult.isSuccess() || ProjectUtils.closeBrowserIfError()))
            || (ProjectUtils.isServerRun() && (!testResult.isSuccess() || methodsOrder.isGroupFinished(method))))
        {
            stopDriver();
        }

        ProjectUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
