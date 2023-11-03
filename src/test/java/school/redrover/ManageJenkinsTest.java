package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    final private static String USER_NAME = "New_User";
    final private static String PASSWORD = "12345";
    final private static String EMAIL = "asd@gmail.com";

    private void createUser(String name, String password, String email) {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.name("username")).sendKeys(name);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();
    }

    private boolean isUserDisplayed(List<WebElement> list, String name) {
        for(WebElement element : list) {
            if (element.getText().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testCreateUserWithValidData() {
        createUser(USER_NAME, PASSWORD, EMAIL);

        Assert.assertTrue(getDriver().findElement(By.linkText(USER_NAME)).isDisplayed());
    }

    @Test
    public void testDeleteUser() {
        createUser(USER_NAME, PASSWORD, EMAIL);

        getDriver().findElement(By.xpath("//*[@id = 'people']/tbody//descendant::a[5]")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> userNames = getDriver().findElements(By.xpath("//*[@id = 'people']/tbody/tr"));

        Assert.assertFalse(isUserDisplayed(userNames, USER_NAME));
    }
}
