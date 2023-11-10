package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesAllTest extends BaseTest {
    public static final String NODE_TEST = "NodeNameTest";

    @Test
    public void testNewNodeCreate() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer'] ")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_TEST);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//a[contains(text(), '" + NODE_TEST + "')]")).isDisplayed());
    }
}
