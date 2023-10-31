package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder7Test extends BaseTest {

    @Test
    public void testCreateOrganizationFolder() {

        final String folderNameToFind = "OrganizationFolder";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("OrganizationFolder");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@href='/'][@class='model-link']")).click();

        getDriver().
                findElement(By.xpath("//tr[@id='job_OrganizationFolder']//a[@href='job/OrganizationFolder/']"))
                .click();
        String createdFolderName = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(createdFolderName,folderNameToFind);

    }

}
