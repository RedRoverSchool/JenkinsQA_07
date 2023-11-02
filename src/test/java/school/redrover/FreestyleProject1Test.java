package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class FreestyleProject1Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final static String NAME_SEARCH = "//span[text()='FreestyleProject']";
    private final static String RENAME_LINE = "//*[@name='newName']";

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getDriver().findElement(By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        if(goToHomePage) {
            getDriver().findElement(By.id(HOME_PAGE)).click();
        }
    }

    @Test
    public void testCreateFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        String actualName = getDriver()
                .findElement(By.xpath(NAME_SEARCH)).getText();
        Assert.assertEquals(actualName, PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        getDriver().findElement(By.xpath(NAME_SEARCH)).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Project']")).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testConfigureBuildEnvironmentSettingsAddTimestamp() throws InterruptedException {
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='Configure']/..")).click();

        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();
        Thread.sleep(600);
        getDriver().findElement(By.xpath("//label[text()='Add timestamps to the Console Output']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//span[text()='Build Now']/..")).click();
        Thread.sleep(5000);
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//span[@class='build-status-icon__outer']")).click();

        List<WebElement> timestamps = getDriver().findElements(
                By.xpath("//pre[@class='console-output']//span[@class='timestamp']"));

        Assert.assertNotEquals(timestamps.size(), 0);
        for (WebElement timestamp : timestamps) {
            Assert.assertTrue(timestamp.getText().trim().matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"));
        }
    }

    @Test
    public void testMoveJobToFolder() {
        final String folderName = "FolderWrapper";
        final String destinationOption = "Jenkins » " + folderName;

        createProject("Freestyle project", PROJECT_NAME, true);
        createProject("Folder", folderName, true);

        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='Move']/..")).click();

        Select destinationDropdown = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        destinationDropdown.selectByVisibleText(destinationOption);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id(HOME_PAGE)).click();

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).isDisplayed());
    }
    @Test
    public void testCreatingFreestyleProject() {
        String projectName = "Artusom";
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='add-item-panel']//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//div[@id='items']//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[@href='/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']/span")).getText(),projectName);
    }
    @Test
    public void FreestyleProjectRenametest() {
        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(By.id(HOME_PAGE)).click();
        WebElement elementToHover = getDriver()
                .findElement(By.xpath("//*[@id='job_FreestyleProject']/td[3]/a/button"));
        actions.moveToElement(elementToHover).perform();
        elementToHover.click();
        WebElement renameClick = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='jenkins-dropdown__item'][4]")));
        renameClick.click();

        getDriver().findElement(By.xpath(RENAME_LINE)).clear();
        getDriver().findElement(By.xpath(RENAME_LINE)).sendKeys("Test name");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        String actualRenameName = getDriver().findElement(By.xpath("//span[text()='Test name']")).getText();
        Assert.assertEquals(actualRenameName,"Test name");
    }
}
