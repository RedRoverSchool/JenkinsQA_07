package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewUser2Test extends BaseTest {

    private static final String TEST_INPUT = "Test";

    @Test
    public void testEmptyFields() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dd[contains(text(),'Create')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Create')]")).click();
        getDriver().findElement((By.name("Submit"))).click();

        List<WebElement> error = getDriver().findElements(By.cssSelector(".error"));

        Assert.assertEquals(error.size(), 5);
    }

    @Test
    public void testUserIsDisplayedInUsersTable() {
        createUserSuccess();
        WebElement createdUser = getDriver().findElement(By.xpath("//tbody/tr[2]/td[2]/a[1]"));

        Assert.assertTrue(createdUser.isDisplayed());
        Assert.assertEquals(createdUser.getText(), TEST_INPUT);
    }

    private void goToUserCreateFormPage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text() = 'Users']")).click();
        getDriver().findElement(By.xpath("//*[@href='addUser']")).click();
    }

    private void createUserSuccess() {
        goToUserCreateFormPage();
        List<WebElement> valueInputs = getDriver().findElements(
                By.xpath("//*[@class = 'jenkins-input']"));
        for (int i = 0; i < valueInputs.size(); i++) {
            if (i == 0) {
                valueInputs.get(i).sendKeys(TEST_INPUT);
            } else {
                valueInputs.get(i).sendKeys(TEST_INPUT + "@" + TEST_INPUT + ".com");
            }
        }
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test
    public void testUserRecordContainUserIdButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dd[contains(text(),'Create')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Create')]")).click();

        WebElement username = getDriver().findElement(By.name("username"));
        username.sendKeys("userTest");
        WebElement password = getDriver().findElement(By.name("password1"));
        password.sendKeys("@userTest");
        WebElement confirm_password = getDriver().findElement(By.name("password2"));
        confirm_password.sendKeys("@userTest");
        WebElement full_name = getDriver().findElement(By.name("fullname"));
        full_name.sendKeys("fullUserTest");
        WebElement email_address = getDriver().findElement(By.name("email"));
        email_address.sendKeys("userTest@gmail.com");
        getDriver().findElement((By.name("Submit"))).click();

        WebElement UserIdButton = getDriver().findElement(By.xpath("//tbody/tr[2]/td[2]/a[1]"));

        Assert.assertEquals(UserIdButton.getText(), "userTest");

    }
}

