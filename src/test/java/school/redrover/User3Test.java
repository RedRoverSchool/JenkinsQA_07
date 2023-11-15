package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

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

    @Test(dependsOnMethods = "testCreateUser")
    public void testConfigureUser() {

        getDriver().findElement(By.xpath("//a[@href = '/asynchPeople/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/user/ivan/']")).click();

        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys("qweqwe");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "qweqwe");

    }

    @Test(dependsOnMethods = "testConfigureUser")
    public void testDeleteUser() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(), 'Users')]/../..")).click();

        getDriver().findElement(By.xpath("//div[@class = 'jenkins-table__cell__button-wrapper']/a[@href = '#']")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> users = getDriver().findElements(By.xpath("//table[@id = 'people']//td[2]/a"));
        List<String> usernames = new ArrayList<>();

        for(WebElement w: users){
            usernames.add(w.getAttribute("href").substring(48).replace("/", ""));
        }

        Assert.assertFalse(usernames.contains(NAME));
    }
}
