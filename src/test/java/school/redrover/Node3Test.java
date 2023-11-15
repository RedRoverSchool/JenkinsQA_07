package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Node3Test extends BaseTest {

    @Test
    public void testCreateNode(){
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Nodes']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Node name");
        getDriver().findElement(By.xpath("//label[@for='hudson.slaves.DumbSlave']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        final String nodeName = getDriver().findElement(By.xpath("//a[@href='../computer/Node%20name/']")).getText();

        Assert.assertEquals(nodeName,"Node name");
    }

}
