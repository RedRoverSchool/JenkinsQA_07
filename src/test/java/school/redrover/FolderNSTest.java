package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderNSTest extends BaseTest {
    @Test
    public void createNewFolder() {

        final String folderName = "New Folder";
        getDriver().findElement(By.cssSelector(".task-icon-link")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.cssSelector(".label")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs__list-item")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(),
                folderName);
    }
}

