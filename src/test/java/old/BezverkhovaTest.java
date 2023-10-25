package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@Ignore
public class BezverkhovaTest extends BaseTest {
    private static final String EMAIL = "test_redrov@yahoo.com";
    private static final String PASSWORD = "te5t_redr0v";
    private static final String USER_NAME = "Test";
    private static final String PROJECT_NAME = "TestProject";
    private static final By DASHBOARD_LOCATOR = By.xpath("//*[@id='breadcrumbs']//a[@href='/']");
    private static final By PROJECT_ON_DASHBOARD = By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]");
    private static final By USER_ICON_LOCATOR = By.xpath("//*[@id='page-header']//a[contains(@href,'user')]");
    private static final By USER_STATUS_LOCATOR = By.xpath("//div[contains(text(), 'User ID')]");
    private static final By PROJECT_NAME_LOCATOR = By.xpath("//h1");
    private static final By BUILD_STATUS_LOCATOR = By.xpath("//div[@class='jenkins-mobile-hide']//span[@class='build-status-icon__outer']//*[name()='svg' and @title='Success']");
    private static final String SECOND_JOB = "Second job";
    private static final String FIRST_JOB = "First job";
    private static final By BUILD_NOW_LOCATOR = By.xpath("//a[contains(@href, 'build')]");


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
        createProject(PROJECT_NAME);

        String actualNameText = getDriver().findElement(PROJECT_NAME_LOCATOR).getText();
        assertEquals(actualNameText, "Project " + PROJECT_NAME);
    }

    @Test
    public void testRenameProject() {
        createProject(PROJECT_NAME);
        getDriver().findElement(DASHBOARD_LOCATOR).click();

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
        createProject(PROJECT_NAME);
        getDriver().findElement(BUILD_NOW_LOCATOR).click();
        getDriver().findElement(DASHBOARD_LOCATOR).click();

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

    @Test
    public void testCreateSeveralJobs() {
        List<String> jobNames = List.of(FIRST_JOB, SECOND_JOB, "Third job", "Forth job", "Fifth job");
        for (String name : jobNames) {
            createProject(name);
            getDriver().findElement(DASHBOARD_LOCATOR).click();
        }
        List<WebElement> jobElements = getDriver().findElements(By.xpath("//*[@id='projectstatus']//a[@class='jenkins-table__link model-link inside']/span"));
        List<String> factJobsNames = new ArrayList<>();
        for (WebElement job : jobElements) {
            factJobsNames.add(job.getText());
        }
        assertTrue(factJobsNames.containsAll(jobNames) && jobNames.containsAll(factJobsNames));
    }

    @Test
    public void testPostBuildCheckUpstream() {
        createProject(FIRST_JOB);

        configBuildOtherProjectAfterBuild(SECOND_JOB);

        createProject(SECOND_JOB);

        String nameOfUpstreamProject = getDriver().findElement(By.xpath("//a[@class='model-link jenkins-icon-adjacent']")).getText();
        assertEquals(nameOfUpstreamProject, FIRST_JOB);
    }

    @Test
    public void testBuildJobInQueue() {
        createProject(FIRST_JOB);
        configBuildOtherProjectAfterBuild(SECOND_JOB);
        createProject(SECOND_JOB);

        getDriver().findElement(DASHBOARD_LOCATOR).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + FIRST_JOB + "')]")).click();
        getDriver().findElement(BUILD_NOW_LOCATOR).click();

        getDriver().findElement(DASHBOARD_LOCATOR).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'builds')]")).click();

        boolean found = false;
        while (!found) {
            try {
                WebElement buildOfSecondJob = getDriver().findElement(By.xpath("//span[contains(text(),'" + SECOND_JOB + "')]"));
                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOf(buildOfSecondJob));
                found = true;
            } catch (Exception ex) {
                getDriver().navigate().refresh();
            }
        }
        getDriver().findElement(By.xpath("//span[contains(text(),'" + SECOND_JOB + "')]/parent::a/parent::td/following-sibling::td[@class='jenkins-table__cell--tight']//a")).click();
        String consoleOutput = getDriver().findElement(By.className("console-output")).getText().replace("\n", "");
        assertTrue(consoleOutput.contains(String.format("Started by upstream project \"%s\"", FIRST_JOB)));
    }


    private void createProject(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(jobName + "_New Test project");
        getDriver().findElement(By.className("jenkins-button--primary")).click();
    }

    private void configBuildOtherProjectAfterBuild(String jobName) {
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        WebElement actionButton = getDriver().findElement(By.xpath("//button [@type='button' and @suffix= 'publisher']"));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(actionButton);
        new Actions(getDriver())
                .scrollFromOrigin(scrollOrigin, 0, 200)
                .perform();
        actionButton.click();

        getDriver().findElement(By.xpath("//a[@class='yuimenuitemlabel' and text()='Build other projects']")).click();

        getDriver().findElement(By.name("buildTrigger.childProjects")).sendKeys(jobName);

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']//button[1]")).click();
        getDriver().findElement(DASHBOARD_LOCATOR).click();
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
