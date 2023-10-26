package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderBayanTest extends BaseTest
{
    private final String FOLDER_NAME = "FOLDER_NAME";
    private final String RENAMED_FOLDER_NAME = "RENAMED_FOLDER_NAME";

    private void create()
    {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void rename(String newFolderName) {
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void goToRenamePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[contains(.,'" + FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();
    }

    @Test
    //https://trello.com/c/AZlAR8HB/109-tc0400104-us04001-folder-rename-folder
    public void testRenameThroughSidePanel()
    {
        create();
        goToRenamePage();
        rename(RENAMED_FOLDER_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'RENAMED_FOLDER')]")).getText(),
                RENAMED_FOLDER_NAME, "Renamed folder name is not matching to the expected renamed name " + RENAMED_FOLDER_NAME);
    }

    @Test
    //https://trello.com/c/HpLFOFj4/112-tc0400105-folder-rename-folder-check-error-when-no-name-is-specified
    public void testRenameErrorNoNameSpecified() {
        create();
        goToRenamePage();
        rename("");

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "No name is specified");

    }

    @Test
    //https://trello.com/c/qKuYXV9W/180-tc0400107-folder-rename-folder-check-error-unsafe-character
    public void testRenameWithUnsafeCharacter() {
        char unsafeCharacter = '*'; // $ # / : ? [ \

        create();
        goToRenamePage();
        rename("FOLDER_WITH_UNSAFE_CHARACTER_"+unsafeCharacter);

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "‘" + unsafeCharacter +"’ is an unsafe character");
    }

    @Test
    //https://trello.com/c/2lI25hTY/184-tc0400108-folder-rename-folder-check-error-for-an-ending-period
    public void testRenameWithEndingPeriod() {
        char period = '.';

        create();
        goToRenamePage();
        rename("FOLDER_WITH_UNSAFE_CHARACTER"+period);

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "A name cannot end with ‘" + period + "’");
    }
}
