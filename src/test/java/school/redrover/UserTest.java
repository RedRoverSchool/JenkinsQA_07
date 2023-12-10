package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.UserPage;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class UserTest extends BaseTest {

    private static final String USER_NAME = "Username";
    private static final String USER_NAME_2 = "FirstUser";
    private static final String FULL_NAME = "User Full Name";
    private static final String PASSWORD = "12345";
    private static final String DESCRIPTION = "Test description";
    private static final String EMAIL = "asd@gmail.com";

    private void createUserNoFullName(String userName, String password, String email) {
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

    private void goToUsersPage() {
        UserDatabasePage page = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();
    }

    private void goToUserCreateFormPage() {
        CreateNewUserPage page = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton();
    }

    @DataProvider
    public Object[][] provideUnsafeCharacter() {
        return new Object[][]{
                {"#"}, {"&"}, {"?"}, {"!"}, {"@"}, {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"\\"}, {"<"}, {">"},
                {"["}, {"]"}, {":"}, {";"}
        };
    }

    @DataProvider
    public Object[][] provideInvalidCredentials() {
        return new Object[][]{
                {"&", "", "test$test.test"},
                {"@", "", "test.test"},
                {">", "", "тест\"тест.ком"},
                {"[", "", "test2test.test"},
                {":", "", "test-test.test"},
                {";", "", "test_test.test"}
        };
    }

    @Test
    public void testFullNameIsSameAsUserIdWhenCreatingNewUser() {
        String fullName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .getFullNameByName(USER_NAME);

        assertEquals(USER_NAME, fullName);
    }

    @Test
    public void testCreateUserWithWrongEmail() {
        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        Assert.assertEquals(error, "Invalid e-mail address");
    }

    @Test
    public void testCreateUserWithoutPassword() {
        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        Assert.assertEquals(error, "Password is required");
    }

    @Test
    public void testCreateUserWithNotMatchedPassword() {
        String error = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD + 1)
                .inputFullName(FULL_NAME)
                .clickCreateUser()
                .getErrorMessage();

        Assert.assertEquals(error, "Password didn't match");
    }

    @Test(dependsOnMethods = {"testCreateUserWithValidData"})
    public void testCreateUserAndLogIn() {
        String userIconText = new HomePage(getDriver())
                .clickLogOut()
                .logIn(USER_NAME, PASSWORD)
                .getCurrentUserName();

        assertEquals(userIconText, USER_NAME);
    }

    @Test
    public void testDeleteUserAndLogIn() {
        UserDatabasePage pageWithDeletedUser = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .clickDeleteIcon(0);
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        String currentErrorMessage = new HomePage(getDriver())
                .clickLogOut()
                .logInWithError(USER_NAME, PASSWORD)
                .getErrorMessage();
        Assert.assertEquals(currentErrorMessage, "Invalid username or password");
    }

    @Test(dependsOnMethods = {"testCreateUserWithValidData"})
    public void testSetDefaultUserView() {
        final String viewName = USER_NAME + "view";

        String activeUserViewTabName = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject("Test")
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

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testAddUserDescriptionFromPeople() {
        String description = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickAddDescription()
                .addAUserDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }
    @Ignore("expected [Test description] but found []")
    @Test (dependsOnMethods = {"testAddUserDescriptionFromPeople", "testCreateUserWithValidData"})
    public void testConfigureShowDescriptionPreview() {
        String previewDescriptionText = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickConfigure()
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescriptionText, DESCRIPTION);
    }


    @Test
    public void testConfigureAddDescriptionFromPeoplePage() {
        String description = new HomePage(getDriver())
                .clickPeople()
                .clickOnUserId()
                .clickEditDescription()
                .addDescription(DESCRIPTION)
                .getText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test
    public void testConfigureAddDescriptionFromManageJenkinsPage() {
        String actualDescription = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickConfigureIcon(0)
                .typeDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();
        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test
    public void testConfigureAddDescriptionUsingDirectLinkInHeader() {
        String description = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickConfigure()
                .typeDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Ignore
    @Test(dependsOnMethods = "testAddUserDescriptionFromPeople")
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

        assertFalse(usernames.contains(USER_NAME));
    }

    @Test
    public void testShowingValidationMessages() {
        List<WebElement> listOfValidationMessages = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .clickCreateUser()
                .getErrorList();

        assertFalse(listOfValidationMessages.isEmpty());
    }

    @Test
    public void testCreateUserWithExistedUsername() {
        String existedName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .getUserID(0);

        String warningMessage = new UserDatabasePage(getDriver())
                .clickAddUserButton()
                .inputUserName(existedName)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickCreateUser()
                .getErrorMessage();

        Assert.assertEquals(warningMessage, "User name is already taken");
    }

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testDeleteLoggedInUser() {
        UserDatabasePage userDatabasePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton();
        assertFalse(userDatabasePage.deleteLoggedUser());
    }

    @Test
    public void testVerifyRequiredFields() {

        List<String> expectedLabelNames = List.of("Username", "Password", "Confirm password", "Full name", "E-mail address");
        List<String> actualLabelNames = new ArrayList<>();

        CreateNewUserPage createNewUserPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton();

        for (String labelName : expectedLabelNames) {
            String labelText = createNewUserPage.getLabelText(labelName);
            actualLabelNames.add(labelText);

            Assert.assertNotNull(createNewUserPage.getInputField(labelName));
        }
        Assert.assertEquals(expectedLabelNames, actualLabelNames);
    }

    @Test(dataProvider = "provideUnsafeCharacter")
    public void testCreateUserWithInvalidName(String unsafeCharacter) {

        String errorMessage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME_2 + unsafeCharacter)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(errorMessage, "User name must only contain alphanumeric characters, underscore and dash");
    }

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testCreatedUserIdDisplayedOnUserPage() {
        boolean isCreatedUserIdDisplayed = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .isCreatedUserIdDisplayedCorrectly(USER_NAME);

        assertTrue(isCreatedUserIdDisplayed);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedUserNameDispalyedOnUserPage")
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


    @Test
    public void testUserIsDisplayedInUsersTable() {
        List<String> createdUserName = new UserPage(getDriver())
                .createUserSuccess("Test")
                .userNameList();

        Assert.assertTrue(createdUserName.contains("Test"));
    }

    @Test
    public void testUserRecordContainUserIdButton() {
        boolean userId = new UserPage(getDriver())
                .createUserSuccess("Test")
                .userIdIsClickable();
        Assert.assertTrue(userId, "Button should be enabled and displayed");
    }

    @Test
    public void testVerifyUserCreated() {
        String usersPageTitleActual = "Users";

        goToUserCreateFormPage();
        createUserAllFields(USER_NAME, PASSWORD, PASSWORD, FULL_NAME, EMAIL);

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
        createUserNoFullName(USER_NAME, password, email);

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/securityRealm/']")).getText(),
                "Jenkins’ own user database");
    }

    @Test
    public void testCreateUserWithValidData() {

        boolean isUserCreated = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickSubmit()
                .isUserCreated(USER_NAME);

        Assert.assertTrue(isUserCreated);
    }

    @Ignore
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

        Boolean userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .isUserCreated(USER_NAME);

        Assert.assertTrue(userName);
    }

    @Test
    public void testUserChangFullName() {
        String userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .clickUserByName(USER_NAME)
                .clickConfigure()
                .sendKeysFullNameUser(FULL_NAME)
                .clickSaveButton()
                .getHeadLineText();

        Assert.assertEquals(userName, FULL_NAME);
    }

    @Test
    public void testCreateUserWithoutEmail() {
        CreateNewUserPage userNotCreated = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .clickCreateUser();
        Assert.assertEquals(userNotCreated.getErrorMessage(), "Invalid e-mail address");
    }

    @Test
    public void testNewUserDisplayedOnPeopleScreen() {
        String userId = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputEmail(EMAIL)
                .clickSubmit()
                .getUserID(1);
        Assert.assertEquals(userId, USER_NAME);
    }

    @Test
    public void testUsernameDisplayedOnUserStatusPage() {

        String userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
                .clickUserByName(USER_NAME)
                .getHeadLineText();
        Assert.assertEquals(userName, USER_NAME);
    }

    @Test(dataProvider = "provideInvalidCredentials")
    public void testCreateUserWithInvalidCredentials(String name, String password, String mail) {
        List<WebElement> errorList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(name)
                .inputPassword(password)
                .inputPasswordConfirm(password)
                .inputEmail(mail)
                .clickCreateUser()
                .getErrorList();

        Assert.assertTrue(errorList.size() == 4);
    }
}
