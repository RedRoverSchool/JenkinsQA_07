package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewUser2Test extends BaseTest {
    @Test
    public void testEmptyFields() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dd[contains(text(),'Create')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Create')]")).click();
        getDriver().findElement((By.name("Submit"))).click();

        List<WebElement> error = getDriver().findElements(By.cssSelector(".error"));

        Assert.assertEquals(error.size(), 5);
    }
}

