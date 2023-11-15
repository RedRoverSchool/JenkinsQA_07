package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NewUser1Test extends BaseTest {
    private final String USER_NAME = "FirstUser";

    private void createNewUser(String userName){
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();
        getDriver().findElement(By.linkText("Create User")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys(userName);
        getDriver().findElement(By.name("password1")).clear();
        getDriver().findElement(By.name("password1")).sendKeys("TestPassword");
        getDriver().findElement(By.name("password2")).clear();
        getDriver().findElement(By.name("password2")).sendKeys("TestPassword");
        getDriver().findElement(By.name("fullname")).clear();
        getDriver().findElement(By.name("fullname")).sendKeys("Tester");
        getDriver().findElement(By.name("email")).clear();
        getDriver().findElement(By.name("email")).sendKeys("test@gmail.com");
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToUsersPage() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();
    }

    @Test
    public void testCreateUserWithValidName() {
        createNewUser(USER_NAME);

        assertEquals(getDriver().findElement(
                By.xpath("//a[@href= 'user/firstuser/']")).getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testCreateUserWithValidName")
    public void testCreateUserWithInvalidName() {
        char unsafeCharacter = '$';

        createNewUser(USER_NAME + unsafeCharacter);

        assertTrue(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/div[2]")).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateUserWithInvalidName")
    public void testCreateUserWithAllEmptyFields() {
        goToUsersPage();
        getDriver().findElement(By.linkText("Create User")).click();
        getDriver().findElement(By.name("Submit")).click();

        List<String> listOfExpectedWarnings = Arrays.asList(
                "\"\" is prohibited as a username for security reasons.",
                "Password is required",
                "Password is required",
                "\"\" is prohibited as a full name for security reasons.",
                "Invalid e-mail address");

        List<WebElement> listOfWarnings = getDriver().findElements(
                By.xpath("//div[@class='error jenkins-!-margin-bottom-2' and contains(., '')]"));
        List<String> extractedWarnings = listOfWarnings.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(extractedWarnings, listOfExpectedWarnings);
    }

    @Test(dependsOnMethods = "testCreateUserWithAllEmptyFields")
    public void testCreatedUserCheckFieldName() {
        goToUsersPage();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='people']/tbody/tr[2]/td[3]")).getText(), "Tester");
    }

    @Test(dependsOnMethods = "testCreatedUserCheckFieldName")
    public void testCreatedUserCheckUserIdButton() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//a[@href='user/firstuser/'] ")).click();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[2]")).getText(), "Jenkins User ID: FirstUser");
    }

    @Test(dependsOnMethods = "testCreatedUserCheckUserIdButton")
    public void testCreateUserCheckConfigurationButton() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//a[@href='user/firstuser/configure'] ")).click();

        List<String>  listOfExpectedItems = Arrays.asList("People", "Status", "Builds", "Configure", "My Views", "Delete");

        List<WebElement> listOfDashboardItems = getDriver().findElements(
                By.xpath("//div[@class ='task ' and contains(., '')]"));
        List<String> extractedTexts = listOfDashboardItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(extractedTexts, listOfExpectedItems);
    }

    @Test(dependsOnMethods = "testCreateUserCheckConfigurationButton")
    public void testDeleteUser() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//*[@id='people']/tbody/tr[2]/td[5]/div")).click();
        getDriver().switchTo().alert().accept();

        List<String>  listOfExpectedUsers = List.of("admin");
        List<WebElement> listOfDashboardUsers = getDriver().findElements(
                By.xpath("//a[@href = 'user/admin/' and contains(., '')]"));
        List<String> extractedUsers = listOfDashboardUsers.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(extractedUsers, listOfExpectedUsers);
    }

    @Test(dependsOnMethods = "testDeleteUser")
    public void testCreateNewUserAndLogInAsNewUser() {
        createNewUser(USER_NAME);
        getDriver().findElement(By.linkText("log out")).click();
        getDriver().findElement(By.id("j_username")).clear();
        getDriver().findElement(By.id("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("j_password")).clear();
        getDriver().findElement(By.id("j_password")).sendKeys("TestPassword");
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/firstuser']")).getText(), "Tester");
    }
}
