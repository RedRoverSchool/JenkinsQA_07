package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder9Test extends BaseTest {
    @Test
    public void testCreateNewFolder () {
        final String NewFolderName = "NewFolder0090";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NewFolderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//tr[@id='job_" + NewFolderName + "']//td[3]")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@id='job_" + NewFolderName + "']//td[3]")).getText(),
                NewFolderName);
    }
}
