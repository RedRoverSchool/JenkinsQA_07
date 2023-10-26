package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder2Test extends BaseTest {

    private void createOrganizationFolder() {

        final String folderName = "OrganizationFolder";

        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.xpath("//span[text() = 'Organization Folder']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name ='Submit']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        getDriver().findElement(By.cssSelector("td a[href='job/" + folderName + "/'] span")).click();
    }

    @Test
    public void renameOrganizationFolder() {

        createOrganizationFolder();

        final String newFolderName = "OrganizationFolderRenamed";

        getDriver().findElement(By.xpath("//a[@href='/job/OrganizationFolder/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//h1[contains(text(), 'OrganizationFolderRenamed')]")).getText(), newFolderName);
    }
}