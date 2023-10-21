package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BezverkhovaTest extends BaseTest {
    private static final String EMAIL = "test_redrov@yahoo.com";
    private static final String PASSWORD = "te5t_redr0v";
    private static final String USER_NAME = "Test";
    private static final String PROJECT_NAME = "TestProject";
    private static final By PROJECT_ON_DASHBOARD = By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]");
    private static final By USER_ICON_LOCATOR = By.xpath("//*[@id='page-header']//a[contains(@href,'user')]");
    private static final By USER_STATUS_LOCATOR = By.xpath("//div[contains(text(), 'User ID')]");
    private static final By PROJECT_NAME_LOCATOR = By.xpath("//h1");
    private static final By BUILD_STATUS_LOCATOR = By.xpath("//div[@class='jenkins-mobile-hide']//span[@class='build-status-icon__outer']//*[name()='svg' and @title='Success']");

    @Test
    public void testJenkinsStatus() {
        getDriver().findElement(USER_ICON_LOCATOR).click();

        String iconText = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        assertEquals(iconText, "admin");

        String statusIconText = getDriver().findElement(USER_STATUS_LOCATOR).getText();
        assertEquals(statusIconText, "Jenkins User ID: admin");
    }

    @Test
    public void testCreateNewProject() {
        createProject();

        String actualNameText = getDriver().findElement(PROJECT_NAME_LOCATOR).getText();
        assertEquals(actualNameText, "Project " + PROJECT_NAME);
    }

    @Test
    public void testRenameProject() {
        createProject();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']//a[@href='/']")).click();

        getDriver().findElement(PROJECT_ON_DASHBOARD).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'rename')]")).click();

        WebElement newNameBox = getDriver().findElement(By.name("newName"));
        newNameBox.clear();
        newNameBox.sendKeys(PROJECT_NAME + "_rename");
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        String actualNameText = getDriver().findElement(PROJECT_NAME_LOCATOR).getText();
        assertEquals(actualNameText, "Project " + PROJECT_NAME + "_rename");
    }

    @Test
    public void testBuildNow() {
        createProject();
        getDriver().findElement(By.xpath("//a[contains(@href, 'build')]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']//a[@href='/']")).click();
        getDriver().findElement(By.xpath(" //*[@id='projectstatus-tabBar']//a[(@href = '/')]")).click();

        String actualBuildStatus = getDriver().findElement(BUILD_STATUS_LOCATOR).getAttribute("title");
        assertTrue(actualBuildStatus.contains("Success"));
    }

    @Test
    public void testCreateUser() {
        createUser();

        String actualUsers = getDriver().findElement(By.xpath("//*[@id='people']")).getText();
        assertTrue(actualUsers.contains(USER_NAME));
    }

    @Test
    public void testLoginNewUSer() {
        createUser();

        getDriver().findElement(By.xpath("//span[contains(text(), 'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(USER_ICON_LOCATOR).click();

        String statusIconText = getDriver().findElement(USER_STATUS_LOCATOR).getText();
        assertEquals(statusIconText, "Jenkins User ID: " + USER_NAME);
    }

    private void createProject() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create a job')]")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("New Test project");
        getDriver().findElement(By.className("jenkins-button--primary")).click();
    }

    private void createUser() {
        getDriver().findElement(By.xpath("//*[@id='tasks']//a[contains(@href,'manage')]")).click();

        getDriver().findElement(By.xpath("//*[@id='main-panel']//dt[contains(text(),'Users')]")).click();

        getDriver().findElement(By.className("jenkins-button--primary")).click();

        getDriver().findElement(By.name("username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("password1")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("password2")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("email")).sendKeys(EMAIL);
        getDriver().findElement(By.name("Submit")).click();
    }

}
