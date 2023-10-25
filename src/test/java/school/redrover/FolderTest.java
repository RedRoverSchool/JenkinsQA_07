package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FolderTest extends BaseTest {

    private void creationNewFolder(String folderName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
    }

    private WebElement findJobByName(String name) {
        return getDriver().findElement(By.xpath("//td/a[@href='job/" + name + "/']"));
    }

    @Test
    public void testRenameWithValidNameFromDropDownMenu() {

        final String folderName = "Folder1";
        final String renamedFolder = "Folder111";
        creationNewFolder(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + renamedFolder + "']")).isDisplayed());
    }

    @Test
    public void testRenameFolder() {

        final String oldFolderName = "FolderToRename";
        final String newFolderName = "RenamedFolder";

        creationNewFolder(oldFolderName);

        getDashboardLink();

        findJobByName(oldFolderName).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + oldFolderName + "/confirm-rename']")).click();
        WebElement inputName = getDriver().findElement(By.name("newName"));
        inputName.clear();
        inputName.sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();
        getDashboardLink();

        Assert.assertEquals(findJobByName(newFolderName).getText(),
                newFolderName);


    }

}

