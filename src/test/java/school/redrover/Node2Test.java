package school.redrover;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

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

        Assert.assertTrue(getDriver().findElement(By.xpath("//td/a[text()='"+nodeName+"']"))!=null);
    }
}
