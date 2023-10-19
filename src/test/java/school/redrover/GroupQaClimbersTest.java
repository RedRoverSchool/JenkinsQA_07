package school.redrover;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupQaClimbersTest extends BaseTest {

    @Ignore
    @Test
    public void testClickOnCreateAJob() {


        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();

        String actualResult = getDriver().findElement(By.xpath("//label[@for='name']"))
                .getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Test
    public void testSearchSettingsField() throws InterruptedException {

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

        getDriver().findElement(By.xpath("(//a[@class='task-link '])[4]")).click();
        Thread.sleep(1000);

        String actualMessage=getDriver().findElement(By.xpath("//div/h1[text()='Manage Jenkins']")).getText();
        String expectedMessage="Manage Jenkins";
        Assert.assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void testLoginJenkins() {
        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();
        WebElement checkJenkinsVersion = getDriver().findElement(By.xpath("//button[@type='button']"));
        checkJenkinsVersion.click();

        WebElement openJenkinsWebSite = getDriver().findElement(By.xpath("//a[@rel='noopener noreferrer']"));
        openJenkinsWebSite.click();

        WebElement getTitle = getDriver().findElement(By.xpath("//a[@href='/']"));
        String getTitleText = getTitle.getText();
        Assert.assertEquals(getTitleText, "");

    }

    @Test
    public void testCreateFolder() {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        String folderName = "Progect_1";
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div[2]/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        String finalFolderName = getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[3]/a/span")).getText();
        Assert.assertEquals(finalFolderName, folderName);

    }
}