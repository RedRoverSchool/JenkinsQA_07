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
    final private static String DESCRIPTION = "Student";

    private void goToHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void goToUsersPage() {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
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
        goToUsersPage();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.name("username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("password1")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("password2")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("email")).sendKeys("asd@gmail.com");

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(USER_NAME)).isDisplayed());
    }

    @Test(dependsOnMethods = "testAddUserDescription")
    public void testDeleteUser() {
        goToUsersPage();

        getDriver().findElement(By.xpath("//*[@id = 'people']/tbody//descendant::a[5]")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> userNames = getDriver().findElements(By.xpath("//*[@id = 'people']/tbody/tr"));

        Assert.assertFalse(isUserDisplayed(userNames, USER_NAME));
    }

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testAddUserDescription() {
        goToHomePage();

        getDriver().findElement(By.xpath("//div[@id = 'tasks']//descendant::div[2]")).click();

        getDriver().findElement(
                By.xpath("//tr[@id = 'person-" + USER_NAME +"']/td[2]/a")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id = 'description']/div[1]")).getText(), DESCRIPTION);
    }

    @Test(dependsOnMethods = "testDeleteUser")
    public void testLoginAsARemoteUser() {
        getDriver().findElement(By.xpath("//span[text() = 'log out']")).click();

        getDriver().findElement(By.id("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("j_password")).sendKeys(PASSWORD);

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[contains(text(), 'Invalid')]")).isDisplayed(), "Invalid username or password");
    }
}

