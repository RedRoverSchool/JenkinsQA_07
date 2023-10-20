package school.redrover;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class GroupQaClimbersTest extends BaseTest {

    @Test
    public void testAllItemsToCreateJobsArePresented() {

        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();

        List<WebElement> webElementsItemsOnFirstPage = getDriver()
                .findElements(By.xpath("//span[@class='label']"));
        List<String> itemsOnFirstPageGetText = new ArrayList<>();

        for (WebElement el: webElementsItemsOnFirstPage) {
            itemsOnFirstPageGetText.add(el.getText());
        }

        List<String> expectedItemsGetText = List.of(
                "Freestyle project", "Pipeline",
                "Multi-configuration project", "Folder",
                "Multibranch Pipeline", "Organization Folder");

        Assert.assertEquals(itemsOnFirstPageGetText, expectedItemsGetText);
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

    @Test
    public void testAddProfileDescription() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("Test");

        getDriver().findElement(By.className("jenkins-button--primary")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), "Test");

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.className("jenkins-input")).clear();

        getDriver().findElement(By.className("jenkins-button--primary")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), "");
    }
}