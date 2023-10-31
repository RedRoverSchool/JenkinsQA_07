package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderNewTest extends BaseTest {

    private static final String FOLDER_NAME = "NewFolder";

    private void createNewFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']"))
                .sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[1]/a")).click();

    }

    @Test
    public void testCreateNewFolder() {
        createNewFolder();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//tr[@class=' job-status-']/td[3]/a/span"))
                .getText(), FOLDER_NAME);
    }
}
