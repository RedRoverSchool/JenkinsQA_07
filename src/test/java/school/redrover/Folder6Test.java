package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder6Test extends BaseTest {

    @Test
    public void testCreate() {

        getDriver().findElement(By.xpath("//a[@href = \"/view/all/newJob\"]")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("TestFolder");
        getDriver().findElement(By.xpath("//span[text() = \"Folder\"]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = \"Submit\"]")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//td//a[@href = 'job/TestFolder/']")).getText(),
                "TestFolder");
    }
}
