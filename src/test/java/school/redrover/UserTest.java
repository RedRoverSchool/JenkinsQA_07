package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class UserTest extends BaseTest {

    @Test
    public void testCreateUser () {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.cssSelector("#username")).sendKeys("Tetiana");
        getDriver().findElement(By.name("password1")).sendKeys("123456");
        getDriver().findElement(By.name("password2")).sendKeys("123456");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div[class='error jenkins-!-margin-bottom-2']")).getText(),
                "Invalid e-mail address");

    }
}
