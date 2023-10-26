package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderBayanTest extends BaseTest
{

    private void create(String folderName)
    {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void rename(String newFolderName) {
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void goToRenamePage(String folder) {
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[contains(.,'" + folder + "')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folder + "/confirm-rename']")).click();
    }

    @Test
    //https://trello.com/c/AZlAR8HB/109-tc0400104-us04001-folder-rename-folder
    public void testRenameThroughSidePanel()
    {
        final String folderName = "FOLDER";
        final String renamedFolderName = "RENAMED_FOLDER";

        create(folderName);

        goToRenamePage(folderName);

        rename(renamedFolderName);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'RENAMED_FOLDER')]")).getText(),
                renamedFolderName, "Renamed folder name is not matching to the expected renamed name " + renamedFolderName);
    }

    @Test
    //https://trello.com/c/HpLFOFj4/112-tc0400105-folder-rename-folder-check-error-when-no-name-is-specified
    public void testRenameErrorNoNameSpecified() {
        final String folderName = "FOLDER";

        create(folderName);

        goToRenamePage(folderName);

        rename("");

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "No name is specified");

    }
}
