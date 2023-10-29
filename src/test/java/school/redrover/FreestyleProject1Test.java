package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

import static java.sql.DriverManager.getDriver;

public class FreestyleProject1Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final static String NAME_SEARCH = "//span[text()='FreestyleProject']";

    private void createJobAndGoToHomePage() {
        getDriver().findElement(By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
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
        createJobAndGoToHomePage();

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

        for (WebElement timestamp : timestamps) {
            Assert.assertTrue(timestamp.getText().trim().matches("[0-9]{2}:[0-9]{2}:[0-9]{2}"));
        }
    }
}
