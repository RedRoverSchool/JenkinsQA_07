package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Node2Test extends BaseTest {
    @Test
    public void testCreateNodeFromMainPanel() {
        final String nodeName = "Test node";
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label[text()='Permanent Agent']")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertNotNull(getDriver().findElement(By.xpath("//td/a[text()='"+nodeName+"']")));
    }
}
