package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.UserPage;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.SeleniumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class UserTest extends BaseTest {

    private static final String USER_NAME = "Jane";
    private final String USER_NAME_2 = "FirstUser";
    private static final String NAME = "ivan";
    public static final String FULL_NAME = "User Full Name";
    final private static String PASSWORD = "12345";
    final private static String WRONG_CONFIRM_PASSWORD = "123";
    private static final String DESCRIPTION = "Test description";
    private static final String EMAIL = "asd@gmail.com";


    @Test
    public void testCreateUserWithValidData() {

        boolean isUserCreated = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickSubmit()
                .isUserCreated(USER_NAME);

        assertTrue(isUserCreated);
    }
    @Test
    public void testFullNameAppearsSameAsUserIdWhenCreatingNewUser() {
        String username = SeleniumUtils.generateRandomName();
        String password = SeleniumUtils.generateRandomPassword(12);
        String email = SeleniumUtils.generateRandomName() + "@" + "mail.com";

        String name = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(username, password, email)
                .getUserId(username);

        assertEquals(name, username);
    }


    @Test
    public void testCreateUserWithExistedUsername() {

        String existedName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .getUserID(0);

        String warningMessage = new UserDatabasePage(getDriver())
                .clickCreateUserButton()
                .inputUserName(existedName)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(warningMessage, "User name is already taken");
    }


    @Test
    public void testCreateUserWithEmptyFullName() {

        String fullName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .getFullNameByName(USER_NAME);

        assertEquals(USER_NAME, fullName);
    }

    @Test
    public void testCreateUserWithWrongEmail() {

        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(error, "Invalid e-mail address");
    }

    @Test
    public void testCreateUserWithoutPassword() {

        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .inputUserName(USER_NAME)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(error, "Password is required");
    }

    @Test
    public void testCreateUserWithNotMatchedPassword() {

        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(WRONG_CONFIRM_PASSWORD)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(error, "Password didn't match");
    }


    @Test(dependsOnMethods = {"testCreateUserWithValidData"})
    public void testSetDefaultUserView() {
        final String viewName = USER_NAME + "view";

        String activeUserViewTabName = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("Test")
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .goHomePage()
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickUserMyViews()
                .clickAddMyViews()
                .createUserViewAndSave(viewName)
                .goHomePage()
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickConfigure()
                .setDefaultUserViewAndSave(viewName)
                .clickUserMyViews()
                .getUserViewActiveTabName();

        assertEquals(activeUserViewTabName, viewName);
    }

    @Test
    public void testShowingValidationMessages() {

        List<WebElement> listOfValidationMessages = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .clickCreateUser()
                .getErrorList();

        assertFalse(listOfValidationMessages.isEmpty());
    }


    @Test(dependsOnMethods = "testUserCreation")
    public void testDeleteLoggedInUser() {

        UserDatabasePage userDatabasePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();

        assertFalse(userDatabasePage.deleteLoggedUser());
    }

    @Test
    public void testUserIsDisplayedInUsersTable() {

        List<String> createdUserName = new UserPage(getDriver())
                .createUserSuccess("Test")
                .userNameList();

        assertTrue(createdUserName.contains("Test"));
    }


    @Test
    public void testUserRecordContainUserIdButton() {

        UserPage createdUserPage = new UserPage(getDriver())
                .createUserSuccess("Test");

        boolean userId = new UserPage(getDriver())
                .userIdIsClickable();

        assertTrue(userId, "Button should be enabled and displayed");
    }



    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testAddUserDescription() {

        String actualDescription = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickAddDescription()
                .addAUserDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        assertEquals(actualDescription, DESCRIPTION);
    }


    @Test
    public void testCreateUserWithoutEmail() {

        String userNotCreatedMessage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(userNotCreatedMessage, "Invalid e-mail address");
    }

    @Test
    public void testUserChangeFullName() {

        final String existedUsername = "Usertest2";
        final String fullName = "User User";

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(existedUsername, PASSWORD, EMAIL);

        new UserConfigurationPage(getDriver())
                .clickUsername()
                .clickConfigurationPage()
                .clearUserFull()
                .sendKeysFullNameUser()
                .clickSaveButton();

        assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                fullName);
    }

    @Test
    public void testVerifyUserCreated() {
        String usersPageTitleActual = "Users";

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, FULL_NAME, EMAIL);

        assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), usersPageTitleActual);
        assertTrue(getDriver().findElement(By.id("people")).getText().contains(USER_NAME) && getDriver().findElement(By.id("people")).getText().contains(FULL_NAME));
    }

    @Test
    public void testVerifyDisplayedUserAfterCreateUser() {

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL);

        assertTrue(getDriver().findElement(By.xpath("//table[@id='people']/tbody")).
                getText().contains(USER_NAME));
    }

    @Ignore
    @Test
    public void testVerifyScreenAfterCreateUser() {

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL);

        assertEquals(getDriver().findElement(By.xpath("//a[@href='/securityRealm/']")).getText(),
                "Jenkins’ own user database");
    }

    @Test(dependsOnMethods = "testUserCreation")
    public void testCreateUserWithInvalidName() {
        char unsafeCharacter = '$';

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME_2 + unsafeCharacter, PASSWORD, EMAIL);

        assertTrue(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/div[2]")).isDisplayed());
    }


    @Test
    public void testCreateUserAndLogIn() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL);


        getDriver().findElement(By.xpath("//span[contains(text(), 'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("Submit")).click();

        String userIconText = getDriver().findElement(By.xpath("//a[contains(@href,'user')]")).getText();
        assertEquals(userIconText, USER_NAME);
    }

    @Test
    public void testDeleteUserAndLogIn() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        getDriver().findElement(By.xpath("//span[contains(text(),'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("Submit")).click();

        String errorText = getDriver().findElement(By.className("app-sign-in-register__error")).getText();
        assertEquals(errorText, "Invalid username or password");
    }


    @Test(dependsOnMethods = "testDeleteUser")
    public void testLoginAsARemoteUser() {
        getDriver().findElement(By.xpath("//span[text() = 'log out']")).click();

        getDriver().findElement(By.id("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("j_password")).sendKeys(PASSWORD);

        getDriver().findElement(By.name("Submit")).click();

        assertTrue(getDriver().findElement(
                By.xpath("//div[contains(text(), 'Invalid')]")).isDisplayed(), "Invalid username or password");
    }


    @Ignore
    @Test(dependsOnMethods = "testVerifyUserCreated")
    public void testVerifyUserIdButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//table[@id='people']//td/a[text()='" + USER_NAME + "']")).click();
        String titleOfUserPageActual = getDriver().findElement(By.tagName("h1")).getText();

        assertEquals(titleOfUserPageActual, FULL_NAME);
        assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains("Jenkins User ID: " + USER_NAME));
    }


    @Test(dependsOnMethods = "testVerifyUserCreated")
    public void testVerifyUserConfigurationButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, '" + USER_NAME.toLowerCase() + "/configure')]")).click();
        String breadcrumbTrailLastSectionText = getDriver().findElement(By.cssSelector("#breadcrumbs li:last-child")).getText();

        assertTrue(getDriver().getCurrentUrl().contains(USER_NAME.toLowerCase() + "/configure"));
        assertEquals(breadcrumbTrailLastSectionText, "Configure");
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
            assertEquals(actualListOfHelpIconsTooltipsText.get(i), expectedListOfHelpIconsTooltipsText.get(i));
        }
    }


    @Test
    public void testUserCanLogout() {
        getDriver().findElement(By.xpath("//a[@href ='/logout']")).click();

        assertEquals(getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                        By.xpath("//h1")))).getText(),
                "Sign in to Jenkins");
    }


    @Test
    public void testVerifyRequiredFields() {

        List<String> expectedLabelNames = List.of("Username", "Password", "Confirm password", "Full name", "E-mail address");
        List<String> actualLabelNames = new ArrayList<>();

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton();

        CreateNewUserPage createNewUserPage = new CreateNewUserPage(getDriver());

        for (String labelName : expectedLabelNames) {
            String labelText = createNewUserPage.getLabelText(labelName);
            actualLabelNames.add(labelText);

            Assert.assertNotNull(createNewUserPage.getInputField(labelName));
        }
        assertEquals(expectedLabelNames, actualLabelNames);
    }



    @Test
    public void testEmptyFields() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dd[contains(text(),'Create')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Create')]")).click();
        getDriver().findElement((By.name("Submit"))).click();

        List<WebElement> error = getDriver().findElements(By.cssSelector(".error"));

        assertEquals(error.size(), 5);
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

        assertFalse(listOfValidationMessages.isEmpty());

        boolean isValidationMessageEqual = false;

        for (WebElement listOfValidationMessage : listOfValidationMessages) {
            if (listOfValidationMessage.getText().equals(validationMessage)) {
                isValidationMessageEqual = true;
            } else {
                isValidationMessageEqual = false;
                break;
            }
        }
        assertTrue(isValidationMessageEqual);
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


        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertFalse(usernames.contains(NAME));
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

        assertFalse(listOfUserID.isEmpty());

        boolean isNewUserIDShown = false;
        for (WebElement webElement : listOfUserID) {
            if (webElement.getText().contains(username)) {
                isNewUserIDShown = true;
                break;
            }
        }
        assertTrue(isNewUserIDShown);
    }

    @Ignore
    @Test(dependsOnMethods = "testShowingValidationMessages")
    public void testCreatedUserCheckFieldName() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='people']/tbody/tr[2]/td[3]")).getText(), "Tester");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedUserCheckFieldName")
    public void testCreatedUserCheckUserIdButton() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();
        getDriver().findElement(By.xpath("//a[@href='user/firstuser/'] ")).click();

        assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[2]")).getText(), "Jenkins User ID: FirstUser");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedUserCheckUserIdButton")
    public void testCreateUserCheckConfigurationButton() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();
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
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();
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
        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickCreateUserButton()
                .fillUserInformationField(USER_NAME_2, PASSWORD, EMAIL);

        getDriver().findElement(By.linkText("log out")).click();
        getDriver().findElement(By.id("j_username")).clear();
        getDriver().findElement(By.id("j_username")).sendKeys(USER_NAME_2);
        getDriver().findElement(By.id("j_password")).clear();
        getDriver().findElement(By.id("j_password")).sendKeys("TestPassword");
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/firstuser']")).getText(), "Tester");
    }
}
