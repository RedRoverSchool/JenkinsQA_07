package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder15Test extends BaseTest {
    private void createNewFolder(String folderName) {
        getDriver().findElement(By.xpath("//a [@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//img[@class = 'icon-folder icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }
    @Test
    public void testAddDescriptionFolder() {
        createNewFolder("OfficeFolder");
        getDriver().findElement(By.xpath("//span[contains(text(), 'OfficeFolder')]")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys("123456789!!!!");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).getText(), "123456789!!!!");
    }
}
