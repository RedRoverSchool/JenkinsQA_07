package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewUser1Test extends BaseTest {
    private final String USER_NAME = "FirstUser";
    private void createNewUser(String testName){
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();
        getDriver().findElement(By.linkText("Create User")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys(testName);
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

    @Test
    public void TestCreateUserWithValidName() {
        createNewUser(USER_NAME);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href= 'user/firstuser/']")).getText(), USER_NAME);
    }

    @Test
    public void TestCreateUserWithInvalidName() {
        char unsafeCharacter = '$';

        createNewUser(USER_NAME + unsafeCharacter);

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[2]")).isDisplayed());
    }
    @Test
    public void TestCreateUserWithAllEmptyFields() {

        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();
        getDriver().findElement(By.linkText("Create User")).click();
        getDriver().findElement(By.name("Submit")).click();

        List<String> listOfExpectedWarnings = Arrays.asList(
                "\"\" is prohibited as a username for security reasons.",
                "Password is required",
                "Password is required",
                "\"\" is prohibited as a full name for security reasons.",
                "Invalid e-mail address");

        List<WebElement> listOfWarnings = getDriver().findElements(By.xpath("//div[@class='error jenkins-!-margin-bottom-2' and contains(., '')]"));
        List<String> extractedWarnings = listOfWarnings.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(extractedWarnings, listOfExpectedWarnings);
    }

    @Test
    public void TestCreatedUserCheckFieldName() {
        createNewUser(USER_NAME);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id=\"people\"]/tbody/tr[2]/td[3]")).getText(), "Tester");
    }

    @Test
    public void TestCreatedUserCheckUserIdButton() {
        createNewUser(USER_NAME);

        getDriver().findElement(By.xpath("//a[@href='user/firstuser/'] ")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[2]")).getText(), "Jenkins User ID: FirstUser");
    }
    @Test
    public void TestCreatedUserCheckConfigurationButton() {
        createNewUser(USER_NAME);

        getDriver().findElement(By.xpath("//a[@href='user/firstuser/configure'] ")).click();

        List<String>  listOfExpectedItems = Arrays.asList("People", "Status", "Builds", "Configure", "My Views", "Delete");

        List<WebElement> listOfDashboardItems = getDriver().findElements(By.xpath("//div[@class ='task ' and contains(., '')]"));
        List<String> extractedTexts = listOfDashboardItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(extractedTexts, listOfExpectedItems);
    }
}
