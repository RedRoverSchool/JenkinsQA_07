package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderRenameTest extends BaseTest {
    private void createOrgFolder() {
        String folderName = "Test";
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.cssSelector("li[class='jenkins_branch_OrganizationFolder'] label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test
    public void folderRename() {
        createOrgFolder();
        final String newFolderName =  "New name";

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                newFolderName
        );
    }
}
