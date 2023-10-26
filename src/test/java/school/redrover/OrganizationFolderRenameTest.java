package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class OrganizationFolderRenameTest extends BaseTest {

    private void createOrgFolder() {
        String folderName = "Test";
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.cssSelector("li[class='jenkins_branch_OrganizationFolder'] label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins-name-icon\"]")).click();
        getDriver().findElement(By.cssSelector("td a[href='job/" + folderName + "/'] span")).click();
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//tr[@id='job_" + folderName + "']")).isDisplayed());

    }
    @Test
    public void folderRename() {
        final String folderName = "Test";
        createOrgFolder();
        final String newFolderName =  "New name";
        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + folderName + "/']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[8]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                newFolderName
        );



    }

}
