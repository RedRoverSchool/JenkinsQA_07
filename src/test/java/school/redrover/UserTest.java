package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.UserPage;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.SeleniumUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserTest extends BaseTest {
    private final static String MANAGE_JENKINS_ELEMENT = "//a[@href = '/manage']";
    private final static String SECURITY_ELEMENT = "//a[@href = 'securityRealm/']";
    private final static String ADD_USER_ELEMENT = "//a[@href = 'addUser']";

    private static final String USER_NAME = "Jane";
    private final String USER_NAME_2 = "FirstUser";
    private static final String NAME = "ivan";
    public static final String FULL_NAME = "User Full Name";
    final private static String PASSWORD = "12345";
    final private static String DESCRIPTION = "Test description";
    private static final String EMAIL = "asd@gmail.com";

    private void createUser(String userName, String password, String email) {
        getDriver().findElement(By.xpath("//a[contains(@href,'manage')]")).click();

        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.name("username")).sendKeys(userName);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();
    }

    public void createUserAllFields(String username, String password, String confirmPassword, String fullName, String eMailAddress) {
        getDriver().findElement(By.id("username")).sendKeys(username);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(confirmPassword);
        getDriver().findElement(By.name("fullname")).sendKeys(fullName);
        getDriver().findElement(By.name("email")).sendKeys(eMailAddress);
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createNewUser(String userName) {
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

    @Test
    public void testFullNameAppearsSameAsUserID() {
        final String username = SeleniumUtils.generateRandomName();
        final String password = SeleniumUtils.generateRandomPassword(12);
        final String email = SeleniumUtils.generateRandomName() + "@" + "mail.com";

        getDriver().findElement(By.xpath(MANAGE_JENKINS_ELEMENT)).click();
        getDriver().findElement(By.xpath(SECURITY_ELEMENT)).click();
        getDriver().findElement(By.xpath(ADD_USER_ELEMENT)).click();

        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();

        String name = getDriver().findElement(By.xpath("(//td/a[@href='user/" + username + "/']/following::td[1])"))
                .getText();

        assertEquals(name, username);
    }

    private void goToHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void goToUsersPage() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();
    }

    private void goToUserCreateFormPage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text() = 'Users']")).click();
        getDriver().findElement(By.xpath("//*[@href='addUser']")).click();
    }


    private void goToUsersTab() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
    }

    @Test
    public void testCreateUser() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.cssSelector("#username")).sendKeys("Tetiana");
        getDriver().findElement(By.name("password1")).sendKeys("123456");
        getDriver().findElement(By.name("password2")).sendKeys("123456");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div[class='error jenkins-!-margin-bottom-2']")).getText(),
                "Invalid e-mail address");
    }

    @Test
    public void testCreateUserAndLogIn() {
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);

        getDriver().findElement(By.xpath("//span[contains(text(), 'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String userIconText = getDriver().findElement(By.xpath("//a[contains(@href,'user')]")).getText();
        assertEquals(userIconText, USER_NAME);
    }

    @Test
    public void testDeleteUserAndLogIn() {
        final String password = "te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        getDriver().findElement(By.xpath("//span[contains(text(),'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String errorText = getDriver().findElement(By.className("app-sign-in-register__error")).getText();
        assertEquals(errorText, "Invalid username or password");
    }

    @Test
    public void testCreateUserAndCheckOnUserDatabase() {
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);

        assertTrue(getDriver().findElement(By.xpath(String.format("//a[@href='user/%s/']", USER_NAME.toLowerCase()))).isDisplayed());
    }

    @Test(dependsOnMethods = {"testCreateUserAndCheckOnUserDatabase"})
    public void testSetDefaultUserView() {
        final String viewName = USER_NAME + "view";

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys("projectName");
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/my-views']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.cssSelector("a[href*='configure']")).click();
        getDriver().findElement(By.name("_.primaryViewName")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();

        WebElement myViews = getDriver().findElement(By.cssSelector("a[href*='my-views']"));
        new Actions(getDriver())
                .scrollToElement(myViews)
                .perform();
        myViews.click();

        String activeTabName = getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
        assertEquals(activeTabName, viewName);
    }

    @Test
    public void testCreateUser3() {
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

    @Test(dependsOnMethods = "testCreateUser3")
    public void testConfigureUser() {

        getDriver().findElement(By.xpath("//a[@href = '/asynchPeople/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/user/ivan/']")).click();

        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys("qweqwe");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "qweqwe");

    }

    @Test
    public void testConfigureShowDescriptionPreview() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/asynchPeople/']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/user/admin/']"))).click();

        getDriver().findElement(By.xpath("//a[@href = '/user/admin/configure']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='_.description']"))).clear();
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//a[@class='textarea-show-preview']")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='textarea-preview']"))).getText(), DESCRIPTION);
    }

    @Test
    public void testConfigureAddDescriptionFromPeoplePage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/asynchPeople/']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/user/admin/']"))).click();

        getDriver().findElement(By.xpath("//a[@href = '/user/admin/configure']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='_.description']"))).clear();
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id = 'description']/div[1]"))).getText(), DESCRIPTION);
    }

    @Test
    public void testConfigureAddDescriptionFromManageJenkinsPage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/manage']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = 'securityRealm/']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = 'user/admin/configure']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='_.description']"))).clear();
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id = 'description']/div[1]"))).getText(), DESCRIPTION);
    }

    @Test
    public void testConfigureAddDescriptionUsingDirectLinkInHeader() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = '/user/admin']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = '/user/admin/configure']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='_.description']"))).clear();
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id = 'description']/div[1]"))).getText(), DESCRIPTION);
    }

    @Test(dependsOnMethods = "testConfigureUser")
    public void testDeleteUser() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//dt[contains(text(), 'Users')]/../..")).click();

        getDriver().findElement(By.xpath("//div[@class = 'jenkins-table__cell__button-wrapper']/a[@href = '#']")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> users = getDriver().findElements(By.xpath("//table[@id = 'people']//td[2]/a"));
        List<String> usernames = new ArrayList<>();

        for (WebElement w : users) {
            usernames.add(w.getAttribute("href").substring(48).replace("/", ""));
        }

        Assert.assertFalse(usernames.contains(NAME));
    }

    @Test
    public void testUserCreation() {
        final String username = "testUser";
        final String password = "1";
        final String email = "test@test.com";

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'username']")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name = 'password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'password2']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> listOfUserID = getDriver().findElements(By.xpath(
                "//td/a[@class = 'jenkins-table__link model-link inside']"));

        Assert.assertFalse(listOfUserID.isEmpty());

        boolean isNewUserIDShown = false;
        for (WebElement webElement : listOfUserID) {
            if (webElement.getText().contains(username)) {
                isNewUserIDShown = true;
                break;
            }
        }

        Assert.assertTrue(isNewUserIDShown);
    }

    @Test
    public void testShowingValidationMessages() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> listOfValidationMessages = getDriver().findElements(By.xpath(
                "//div[@class = 'error jenkins-!-margin-bottom-2']"));

        Assert.assertFalse(listOfValidationMessages.isEmpty());
    }

    @Test
    public void testUnableToCreateUserWithExistedUsername() {
        final String existedUsername = "admin";
        final String password = "1";
        final String email = "test@test.com";
        final String validationMessage = "User name is already taken";

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'username']")).sendKeys(existedUsername);
        getDriver().findElement(By.xpath("//input[@name = 'password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'password2']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class = 'error jenkins-!-margin-bottom-2']")).getText(),
                validationMessage);
    }

    @Test
    public void testPasswordAndConfirmPasswordArentTheSame() {
        final String existedUsername = "testUser";
        final String password = "1";
        final String confirmPassword = "2";
        final String email = "test@test.com";
        final String validationMessage = "Password didn't match";

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'username']")).sendKeys(existedUsername);
        getDriver().findElement(By.xpath("//input[@name = 'password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'password2']")).sendKeys(confirmPassword);
        getDriver().findElement(By.xpath("//input[@name = 'email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> listOfValidationMessages = getDriver().findElements(By.xpath(
                "//div[@class = 'error jenkins-!-margin-bottom-2']"));

        Assert.assertFalse(listOfValidationMessages.isEmpty());

        boolean isValidationMessageEqual = false;

        for (WebElement listOfValidationMessage : listOfValidationMessages) {
            if (listOfValidationMessage.getText().equals(validationMessage)) {
                isValidationMessageEqual = true;
            } else {
                isValidationMessageEqual = false;
                break;
            }
        }

        Assert.assertTrue(isValidationMessageEqual);
    }

    @Test(dependsOnMethods = "testUserCreation")
    public void testDeleteLoggedInUser() {
        HomePage homePage = new HomePage(getDriver());

        Assert.assertFalse(homePage.clickManageJenkins().goUserDatabasePage().deleteLoggedUser());
    }

    @Test
    public void testVerifyRequiredFields() {

        List<String> expectedLabelNames = List.of("Username", "Password", "Confirm password", "Full name", "E-mail address");
        List<String> actualLabelNames = new ArrayList<>();

        new HomePage(getDriver())
                .clickManageJenkins()
                .goUserDatabasePage()
                .createUser();

        CreateNewUserPage createNewUserPage = new CreateNewUserPage(getDriver());

        for (String labelName : expectedLabelNames) {
            String labelText = createNewUserPage.getLabelText(labelName);
            actualLabelNames.add(labelText);

            Assert.assertNotNull(createNewUserPage.getInputField(labelName));
        }
        Assert.assertEquals(expectedLabelNames, actualLabelNames);
    }

    @Test(dependsOnMethods = "testUserCreation")
    public void testCreateUserWithInvalidName() {
        char unsafeCharacter = '$';

        createNewUser(USER_NAME_2 + unsafeCharacter);

        assertTrue(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/div[2]")).isDisplayed());
    }

    @Ignore
    @Test(dependsOnMethods = "testShowingValidationMessages")
    public void testCreatedUserCheckFieldName() {
        goToUsersPage();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='people']/tbody/tr[2]/td[3]")).getText(), "Tester");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedUserCheckFieldName")
    public void testCreatedUserCheckUserIdButton() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//a[@href='user/firstuser/'] ")).click();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[2]")).getText(), "Jenkins User ID: FirstUser");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedUserCheckUserIdButton")
    public void testCreateUserCheckConfigurationButton() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//a[@href='user/firstuser/configure'] ")).click();

        List<String> listOfExpectedItems = Arrays.asList("People", "Status", "Builds", "Configure", "My Views", "Delete");

        List<WebElement> listOfDashboardItems = getDriver().findElements(
                By.xpath("//div[@class ='task ' and contains(., '')]"));
        List<String> extractedTexts = listOfDashboardItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(extractedTexts, listOfExpectedItems);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateUserCheckConfigurationButton")
    public void testDeleteUser3() {
        goToUsersPage();
        getDriver().findElement(By.xpath("//*[@id='people']/tbody/tr[2]/td[5]/div")).click();
        getDriver().switchTo().alert().accept();

        List<String> listOfExpectedUsers = List.of("admin");
        List<WebElement> listOfDashboardUsers = getDriver().findElements(
                By.xpath("//a[@href = 'user/admin/' and contains(., '')]"));
        List<String> extractedUsers = listOfDashboardUsers.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(extractedUsers, listOfExpectedUsers);
    }

    @Ignore
    @Test(dependsOnMethods = "testDeleteUser3")
    public void testCreateNewUserAndLogInAsNewUser() {
        createNewUser(USER_NAME_2);
        getDriver().findElement(By.linkText("log out")).click();
        getDriver().findElement(By.id("j_username")).clear();
        getDriver().findElement(By.id("j_username")).sendKeys(USER_NAME_2);
        getDriver().findElement(By.id("j_password")).clear();
        getDriver().findElement(By.id("j_password")).sendKeys("TestPassword");
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/firstuser']")).getText(), "Tester");
    }

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
        List<String> createdUserName = new UserPage(getDriver())
                .createUserSuccess("Test")
                .userNameList();

        Assert.assertTrue(createdUserName.contains("Test"));
    }

    @Test
    public void testUserRecordContainUserIdButton() {
        UserPage createdUserPage = new UserPage(getDriver())
                .createUserSuccess("Test");

        boolean userId = new UserPage(getDriver())
                .userIdIsClickable();
        Assert.assertTrue(userId, "Button should be enabled and displayed");
    }

    @Test
    public void testCreateUserEmptyName() {
        goToUsersTab();

        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("TestName");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='main-panel']/form/div[1]/div[2]")).getText(), "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testVerifyUserCreated() {
        String password = "qwerty";
        String confirmPassword = "qwerty";
        String eMailAddress = "user@mail.com";
        String usersPageTitleActual = "Users";

        goToUserCreateFormPage();
        createUserAllFields(USER_NAME, password, confirmPassword, FULL_NAME, eMailAddress);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), usersPageTitleActual);
        Assert.assertTrue(getDriver().findElement(By.id("people")).getText().contains(USER_NAME) && getDriver().findElement(By.id("people")).getText().contains(FULL_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testVerifyUserCreated")
    public void testVerifyUserIdButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//table[@id='people']//td/a[text()='" + USER_NAME + "']")).click();
        String titleOfUserPageActual = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(titleOfUserPageActual, FULL_NAME);
        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains("Jenkins User ID: " + USER_NAME));
    }

    @Test(dependsOnMethods = "testVerifyUserCreated")
    public void testVerifyUserConfigurationButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, '" + USER_NAME.toLowerCase() + "/configure')]")).click();
        String breadcrumbTrailLastSectionText = getDriver().findElement(By.cssSelector("#breadcrumbs li:last-child")).getText();

        Assert.assertTrue(getDriver().getCurrentUrl().contains(USER_NAME.toLowerCase() + "/configure"));
        Assert.assertEquals(breadcrumbTrailLastSectionText, "Configure");
    }

    @Ignore
    @Test(dependsOnMethods = "testVerifyUserCreated")
    public void testVerifyHelpTooltips() {
        List<String> expectedListOfHelpIconsTooltipsText = List.of(
                "Help for feature: Full Name",
                "Help for feature: Description",
                "Help for feature: Current token(s)",
                "Help for feature: Notification URL",
                "Help",
                "Help for feature: Time Zone");

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/user/" + USER_NAME.toLowerCase() + "/']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/configure')]")).click();
        getWait5();

        List<WebElement> helpIconsTooltips = getDriver().findElements(By.xpath("//a[@class='jenkins-help-button']"));
        List<String> actualListOfHelpIconsTooltipsText = new ArrayList<>();
        for (int i = 0; i < helpIconsTooltips.size(); i++) {
            actualListOfHelpIconsTooltipsText.add(helpIconsTooltips.get(i).getAttribute("tooltip"));
            Assert.assertEquals(actualListOfHelpIconsTooltipsText.get(i), expectedListOfHelpIconsTooltipsText.get(i));
        }
    }

    @Test
    public void testUserCanLogout() {
        getDriver().findElement(By.xpath("//a[@href ='/logout']")).click();

        Assert.assertEquals(getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                        By.xpath("//h1")))).getText(),
                "Sign in to Jenkins");
    }

    @Ignore
    @Test
    public void testVerifyScreenAfterCreateUser() {
        String password = "1234567";
        String email = "test@gmail.com";
        createUser(USER_NAME, password, email);

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/securityRealm/']")).getText(),
                "Jenkins’ own user database");
    }

    @Test
    public void testCreateUserWithValidData() {

        boolean isUserCreated = new HomePage(getDriver())
                .clickManageJenkins()
                .goUserDatabasePage()
                .createUser()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickSubmit()
                .isUserCreated(USER_NAME);

        Assert.assertTrue(isUserCreated);
    }

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testAddUserDescription() {
        goToHomePage();

        getDriver().findElement(By.xpath("//div[@id = 'tasks']//descendant::div[2]")).click();

        getDriver().findElement(
                By.xpath("//tr[@id = 'person-" + USER_NAME + "']/td[2]/a")).click();

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

    @Test
    public void testVerifyDisplayedUserAfterCreateUser() {
        String password = "1234567";
        String email = "test@gmail.com";
        createUser(USER_NAME, password, email);

        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@id='people']/tbody")).
                getText().contains(USER_NAME), true);
    }

    @Test
    public void testDeleteUsingBreadcrumb() {
        goToUserCreateFormPage();
        createUserAllFields(USER_NAME, PASSWORD, PASSWORD, FULL_NAME, EMAIL);

        Actions actions = new Actions(getDriver());

        WebElement breadcrumbName = getDriver().findElement(By.linkText(USER_NAME));
        actions.moveToElement(breadcrumbName);
        actions.moveToElement(breadcrumbName).build().perform();

        WebElement chevron = getDriver().findElement(By.xpath("//a[(@href='user/" + USER_NAME.toLowerCase() + "/')]/button"));

        getDriver().manage().timeouts().setScriptTimeout(Duration.ofSeconds(65));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", chevron);

        WebElement buttonDelete = getDriver().findElement(
                By.xpath("//*[name()='svg' and @class='icon-edit-delete icon-md']"));
        actions.moveToElement(buttonDelete).click().perform();


        getDriver().switchTo().alert().accept();

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//tr/td/a[contains(@class, 'jenkins-table__link')]"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertFalse(resultList.contains(USER_NAME));
    }
}
