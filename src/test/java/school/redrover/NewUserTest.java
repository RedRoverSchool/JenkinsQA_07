package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewUserTest extends BaseTest {
    @Test
    public void testCreateWithValidName() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dd[contains(text(),'Create')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("username"));
        getDriver().findElement(By.id("username")).sendKeys("test_user");
        getDriver().findElement((By.name("password1"))).sendKeys("test_password");
        getDriver().findElement(By.name("password2")).sendKeys("test_password");
        getDriver().findElement(By.name("fullname")).sendKeys("test_user");
        getDriver().findElement(By.name("email")).sendKeys("test_user@gmail.com");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tbody/tr[2]/td[2]/a[1]")).getText(), "test_user");
    }
}
