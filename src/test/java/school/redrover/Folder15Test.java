package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder15Test extends BaseTest {
    private static final String NEW_FOLDER_NAME = "WorkFolder";
    private void createNewFolder() {
        getDriver().findElement(By.xpath("//a [@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.xpath("//img[@class = 'icon-folder icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }
    @Test
    public void testCreateView() {
        createNewFolder();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(), 'WorkFolder')]")).getText(), "WorkFolder");
    }
}
