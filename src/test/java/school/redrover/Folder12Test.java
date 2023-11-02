package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.lang.Thread.*;
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class Folder12Test extends BaseTest
{
    private final String MAIN_FOLDER_NAME = "Main_folder_name";
    private final String NESTED_FOLDER_NAME = "Inserted_Folder";
    private final String RENAMED_FOLDER_NAME = "Renamed_folder_name";
    private final String FOLDER_NAME = "Original Folder";
    private final String FREESTYLE_PROJECT = "First Project";

    private void create(String nameFolder) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameFolder);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void rename(String newFolderName) {
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void goTheFolderPage(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).click();
    }

    @Test
    public void testRenameThroughSidePanel() {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        goTheFolderPage(MAIN_FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename(RENAMED_FOLDER_NAME);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[contains(.,'" + RENAMED_FOLDER_NAME + "')]")).getText(),
                RENAMED_FOLDER_NAME,
                "Renamed folder name is not matching to the expected renamed name " + RENAMED_FOLDER_NAME);
    }

    @Test
    public void testRenameThroughContextMenu() {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        getDriver().findElement(By.xpath("//*[@id='job_" + MAIN_FOLDER_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename(RENAMED_FOLDER_NAME);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[contains(.,'" + RENAMED_FOLDER_NAME + "')]")).getText(),
                RENAMED_FOLDER_NAME,
                "Renamed folder name is not matching to the expected renamed name " + RENAMED_FOLDER_NAME);
    }

    @Test
    public void testRenameErrorNoNameSpecified() {
        create(MAIN_FOLDER_NAME);
        goToDashboard();
        goTheFolderPage(MAIN_FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename("");

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "No name is specified");
    }

    @Test
    public void testRenameWithUnsafeCharacter() {
        char unsafeCharacter = '*'; // $ # / : ? [ \

        create(MAIN_FOLDER_NAME);
        goToDashboard();
        goTheFolderPage(MAIN_FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename("FOLDER_WITH_UNSAFE_CHARACTER_"+unsafeCharacter);

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "‘" + unsafeCharacter +"’ is an unsafe character");
    }

    @Test
    public void testRenameWithEndingPeriod() {
        char period = '.';

        create(MAIN_FOLDER_NAME);
        goToDashboard();
        goTheFolderPage(MAIN_FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename("FOLDER_WITH_UNSAFE_CHARACTER" + period);

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "A name cannot end with ‘" + period + "’");
    }

    @Test
    public void testMoveThroughSidePanel() throws InterruptedException
    {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        create(NESTED_FOLDER_NAME);
        goToDashboard();

        goTheFolderPage(NESTED_FOLDER_NAME);
        Thread.sleep(1000);
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By .xpath("//option[contains(text(),'Jenkins » " + MAIN_FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.name("Submit")).click();

        goToDashboard();
        goTheFolderPage(MAIN_FOLDER_NAME);
        goTheFolderPage(NESTED_FOLDER_NAME);

        ArrayList<String> expectedBreadcrumbs = new ArrayList<>();
        expectedBreadcrumbs.add("Dashboard");
        expectedBreadcrumbs.add(MAIN_FOLDER_NAME);
        expectedBreadcrumbs.add(NESTED_FOLDER_NAME);

        ArrayList<String> actualBreadcrumbs = new ArrayList<>();
        List<WebElement> bredcrumbs = getDriver().findElements(By.xpath("//li/a[@class='model-link']"));
        for (WebElement eachBreadcrumb: bredcrumbs)
        {
            actualBreadcrumbs.add(eachBreadcrumb.getText());
        }

        Assert.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs don't match");
    }

    private void createNewFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createNewFreestyleProject(String projectName) {
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToDashboard() {
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @Test
    public void TestCreateJobInFolder() {
        createNewFolder(FOLDER_NAME);
        getDriver().findElement(By.linkText("Create a job")).click();
        createNewFreestyleProject(FREESTYLE_PROJECT);
        goToDashboard();
        getDriver().findElement(By.xpath("//*[@id= 'job_" + FOLDER_NAME + "']/td[3]/a")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='job_" + FREESTYLE_PROJECT + "']/td[3]/a/span")).isDisplayed());
    }

    @Test
    public void TestMoveFolder() {
        createNewFolder(FOLDER_NAME);
        goToDashboard();

        createNewFolder(NESTED_FOLDER_NAME);
        goToDashboard();

        getDriver().findElement(By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//*[@href='/job/" + NESTED_FOLDER_NAME+ "/move']")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select/option[2]")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/button")).click();

        goToDashboard();
        getDriver().findElement(By.xpath("//*[@id= 'job_" + FOLDER_NAME + "']/td[3]/a")).click();

        assertEquals(getDriver().findElement(By.xpath("//*[@id='job_" + NESTED_FOLDER_NAME + "']/td[3]/a/span")).getText(), NESTED_FOLDER_NAME);
    }
}



