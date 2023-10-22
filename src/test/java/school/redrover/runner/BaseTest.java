package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;


@Listeners({FilterForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

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

    @BeforeMethod
    protected void beforeMethod(Method method) {
        startDriver();

        clearData();
        getWeb();
        loginWeb();
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectUtils.isServerRun()) {
            ProjectUtils.takeScreenshot(driver, method.getName(), this.getClass().getName());
        }

        if (!ProjectUtils.isServerRun() && (testResult.isSuccess() || ProjectUtils.closeBrowserIfError())) {
            stopDriver();
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
