package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class User3Test extends BaseTest {

    private static final String NAME = "ivan";
    @Test
    public void testCreateUser() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();

        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();

        getDriver().findElement(By.id("username")).sendKeys(NAME);
        getDriver().findElement(By.name("password1")).sendKeys("qweqwe12");
        getDriver().findElement(By.name("password2")).sendKeys("qweqwe12");
        getDriver().findElement(By.name("email")).sendKeys("hotmail@hotmail.ru");
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button")).click();


        Assert.assertEquals(
                getDriver().findElement(By.xpath("//td[contains(text(),'" + NAME + "')]")).getText(), NAME
        );
    }

}
