package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder3Test extends BaseTest {

    private static final String FOLDER_NAME = "Folder1";
    private static final String RENAMED_FOLDER = "RenamedFolder";
    private static final String NESTED_FOLDER = "Nested";
    private static final String JOB_NAME = "New Job";

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        returnToJenkinsDashboard();
    }

    private void returnToJenkinsDashboard() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
    }

    @Test
    public void testCreate() {
        createFolder(FOLDER_NAME);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@href='job/" + FOLDER_NAME + "/']")).getText(), FOLDER_NAME);
    }

    @Ignore
    @Test
    public void testRename() {
        createFolder(FOLDER_NAME);

        getDriver().findElement(By.xpath("//*[@id='job_" + FOLDER_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(RENAMED_FOLDER);
        getDriver().findElement(By.name("Submit")).click();
        returnToJenkinsDashboard();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@href='job/" + RENAMED_FOLDER + "/']")).getText(), RENAMED_FOLDER);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreate")
    public void testMoveFolderToFolder() {
        createFolder(NESTED_FOLDER);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/" + NESTED_FOLDER + "/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + NESTED_FOLDER + "/move']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("destination"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//option[@value='/" + FOLDER_NAME + "']"))).click();
        getDriver().findElement(By.name("Submit")).click();
        returnToJenkinsDashboard();

        getDriver().findElement(By.xpath("//li[@class='children'][1]")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/']")).click();
        getDriver().findElement(By.xpath("//li[@class='children'][2]")).click();
        getDriver().findElement(By.xpath("//a[@class='jenkins-dropdown__item']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@class='jenkins-table__link model-link inside']")).getText(), NESTED_FOLDER);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateNewJob() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/a[@href='job/" + FOLDER_NAME + "/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(JOB_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText(),
                "Project " + JOB_NAME);
    }
}
