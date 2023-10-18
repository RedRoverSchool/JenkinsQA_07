package school.redrover;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class GroupQaClimbersTest extends BaseTest {

    @Ignore
    @Test
    public void testClickOnCreateAJob() {

        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();

        String actualResult = getDriver().findElement(By.xpath("//label[@for='name']"))
                .getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Test
    public void testSearchSettingsField() throws InterruptedException {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(
                By.xpath("//div[@id='tasks']/div[4]/span/a")).click();
        WebElement searchSettingsField = getDriver().findElement(
                By.xpath("//div[@class='jenkins-search-container']/div/input[@id='settings-search-bar']"));
        searchSettingsField.click();
        searchSettingsField.sendKeys("script");
        Thread.sleep(500);
        searchSettingsField.sendKeys(Keys.ENTER);
        String actualTitle = getDriver().findElement(
                By.xpath("//div[@id='main-panel']/h1[text()='Script Console']"))
                .getText();

        Assert.assertEquals(actualTitle, "Script Console");
    }

    @Test
    public void testManageJenkinsOption() throws InterruptedException {
        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        Thread.sleep(1000);

        String actualMessage=getDriver().findElement(By.xpath("//div/h1[text()='Manage Jenkins']")).getText();
        String expectedMessage="Manage Jenkins";
        Assert.assertEquals(actualMessage,expectedMessage);
    }
}