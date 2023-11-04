package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameNode3Test extends BaseTest {
    private static final String USER_NAME = "FirstUser";
    @Test
    public void testRenameNode() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("//a[@href='/user/admin/configure']")).click();
        getDriver().findElement(By.name("_.fullName")).clear();

        getDriver().findElement(By.xpath("//input[@name='_.fullName']")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),USER_NAME);
    }
}
