package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Node2Test extends BaseTest {

    private void createNewNode(String nodeName){
        getDriver().findElement(By.xpath("//a[@href='new']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label[text()='Permanent Agent']")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }
    @Test
    public void testCreateNodeFromMainPanel() {
        final String nodeName = "Test node from main panel";
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        createNewNode(nodeName);
        Assert.assertNotNull(getDriver().findElement(By.xpath("//td/a[text()='"+nodeName+"']")));
    }

    @Test
    public void testCreateNodeFromBuildExecutorStatus(){
        final String nodeName = "Test node from build executor status";
        getDriver().findElement(By.xpath("//a[text()='Build Executor Status']")).click();
        createNewNode(nodeName);
        Assert.assertNotNull(getDriver().findElement(By.xpath("//td/a[text()='"+nodeName+"']")));
    }
}
