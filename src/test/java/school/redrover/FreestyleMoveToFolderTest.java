package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleMoveToFolderTest extends BaseTest {

    private void createNewItemAndGoToDashboard(String itemType, String itemName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//li[contains(@class, '" + itemType + "')][1]")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }
    @Test
    public void testMoveFreestyleToFolder() {
        final String freestyleName = "freestyleName";
        final String folderName = "folderName";

        createNewItemAndGoToDashboard("FreeStyleProject", freestyleName);
        createNewItemAndGoToDashboard("Folder", folderName);

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + freestyleName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + freestyleName + "/move']")).click();

        Select select = new Select(getDriver().findElement(By.xpath("//select[@name = 'destination']")));
        select.selectByValue("/" + folderName);

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + folderName + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText().trim(), folderName);
        Assert.assertTrue(
                getDriver().findElement(By.xpath("//tbody/tr[@id = 'job_" + freestyleName + "']")).isDisplayed());
    }
}
