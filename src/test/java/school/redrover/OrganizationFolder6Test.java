package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder6Test extends BaseTest {
    @Test
    public void testCreate() {
        final String folderName = "OrganizationFolderName";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();

        getDriver().findElement(By.xpath("//span[normalize-space()='" + folderName + "']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[normalize-space()='" + folderName + "']")).getText(),
                folderName);
    }
}

