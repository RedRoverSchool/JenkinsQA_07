package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FolderBayanTest extends BaseTest
{
    private final String MAIN_FOLDER_NAME = "Main_folder_name";
    private final String NESTED_FOLDER_NAME = "Nested_folder_name";
    private final String RENAMED_FOLDER_NAME = "Renamed_folder_name";

    private void create(String nameFolder)
    {
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

    private void goToDashboard() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    //https://trello.com/c/AZlAR8HB/109-tc0400104-us04001-folder-rename-folder
    public void testRenameThroughSidePanel()
    {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        goTheFolderPage(MAIN_FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename(RENAMED_FOLDER_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'" + RENAMED_FOLDER_NAME + "')]")).getText(),
                RENAMED_FOLDER_NAME, "Renamed folder name is not matching to the expected renamed name " + RENAMED_FOLDER_NAME);
    }

    @Test
    //https://trello.com/c/tm5ILS4l/190-tc04002034-folder-move-folder-to-folder-through-context-menu
    public void testRenameThroughContextMenu() {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        getDriver().findElement(By.xpath("//*[@id='job_" + MAIN_FOLDER_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MAIN_FOLDER_NAME + "/confirm-rename']")).click();

        rename(RENAMED_FOLDER_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'" + RENAMED_FOLDER_NAME + "')]")).getText(),
                RENAMED_FOLDER_NAME, "Renamed folder name is not matching to the expected renamed name " + RENAMED_FOLDER_NAME);
    }
    @Test
    //https://trello.com/c/HpLFOFj4/112-tc0400105-folder-rename-folder-check-error-when-no-name-is-specified
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
    //https://trello.com/c/qKuYXV9W/180-tc0400107-folder-rename-folder-check-error-unsafe-character
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
    //https://trello.com/c/2lI25hTY/184-tc0400108-folder-rename-folder-check-error-for-an-ending-period
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
    //https://trello.com/c/Iq7pi6KS/186-tc0400203-folder-move-folder-to-folder-through-the-side-panel
    public void testMoveThroughSidePanel()
    {
        create(MAIN_FOLDER_NAME);
        goToDashboard();

        create(NESTED_FOLDER_NAME);
        goToDashboard();

        goTheFolderPage(NESTED_FOLDER_NAME);
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
}
