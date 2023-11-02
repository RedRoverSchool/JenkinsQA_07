package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ManageJenkinsTest extends BaseTest {

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

    @Test
    public void testCreateUserWithValidData() {
        final String userName = "New_User";
        final String password = "12345";
        final String email = "asd@gmail.com";

        createUser(userName, password, email);

        Assert.assertTrue(getDriver().findElement(By.linkText(userName)).isDisplayed());
    }
}
