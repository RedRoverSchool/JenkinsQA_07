package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserTest extends BaseTest {

    @Test
    public void testDeleteUser() {
        final String user = "New_User";

        getDriver().findElement(By.xpath("//a[contains(@href, 'manage')]")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(), 'Users')]/../..")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Create User')]")).click();
        getDriver().findElement(By.xpath("//input[@name = 'username']")).sendKeys(user);
        getDriver().findElement(By.xpath("//input[@name = 'password1']")).sendKeys("password");
        getDriver().findElement(By.xpath("//input[@name = 'password2']")).sendKeys("password");
        getDriver().findElement(By.xpath("//input[@name = 'email']")).sendKeys("email@email.com");
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'manage')]")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(), 'Users')]/../..")).click();
        getDriver().findElement(By.xpath("//a[contains(@data-message, 'Delete Jenkins user ‘" + user + "’?')]")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> peoples = getDriver().findElements(By.xpath("//table[@id = 'people']//td[2]/a"));
        List<String> peopleList = new ArrayList<>();

        for(WebElement w: peoples){
            peopleList.add(w.getAttribute("href").substring(48).replace("/", ""));
        }

        Assert.assertFalse(peopleList.contains(user));
    }
}
