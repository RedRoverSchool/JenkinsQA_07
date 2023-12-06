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
    private static final String USER_NAME_2 = "FirstUser";
    private static final String NAME = "ivan";
    private static final String FULL_NAME = "User Full Name";
    private static final String PASSWORD = "12345";
    private static final String DESCRIPTION = "Test description";
    private static final String EMAIL = "asd@gmail.com";

    @Test
    public void testFullNameAppearsSameAsUserIdWhenCreatingNewUser() {
        String username = SeleniumUtils.generateRandomName();
        String password = SeleniumUtils.generateRandomPassword(12);
        String email = SeleniumUtils.generateRandomName() + "@" + "mail.com";

        String name = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(username, password, email)
                .getUserFullName(username);

        assertEquals(name, username);
    }

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


    @Test
    public void testCreateUserWithEmptyFullName() {

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
                .clickDeleteIcon(1);

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
    public void testCreateUser() {
        String createdUserFullName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(NAME)
                .inputPassword(PASSWORD)
                .inputPasswordConfirm(PASSWORD)
                .inputFullName(FULL_NAME)
                .inputEmail(EMAIL)
                .clickSubmit()
                .getUserFullName(NAME);

        Assert.assertEquals(createdUserFullName, FULL_NAME);
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testAddUserDescriptionFromPeople() {
        String description = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(NAME)
                .clickAddDescription()
                .addAUserDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test
    public void testConfigureShowDescriptionPreview() {
        String previewDescriptionText = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser("admin")
                .clickConfigure()
                .typeDescription(DESCRIPTION)
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

        assertFalse(usernames.contains(NAME));
    }

    @Test
    public void testUserCreation() {

       boolean isNewUserIDPresent = new HomePage(getDriver())
               .clickManageJenkins()
               .clickUsersButton()
               .clickAddUserButton()
               .fillUserInformationField(USER_NAME, PASSWORD, EMAIL)
               .listOfUserIDsContainsName(USER_NAME);


        assertTrue(isNewUserIDPresent);
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

    @Test(dependsOnMethods = "testUserCreation")
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

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton();

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

        String errorMessage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .inputUserName(USER_NAME_2 + unsafeCharacter)
                .clickCreateUser()
                .getErrorMessage();

        assertEquals(errorMessage, "User name must only contain alphanumeric characters, underscore and dash");
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

    @Test(dependsOnMethods = "testCreateUserWithValidData")
    public void testAddUserDescription() {
        String actualDescription = new HomePage(getDriver())
                .clickPeople()
                .clickOnTheCreatedUser(USER_NAME)
                .clickAddDescription()
                .addAUserDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, DESCRIPTION);
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
        String password = "1234567";
        String email = "test@gmail.com";
        createUserNoFullName(USER_NAME, password, email);

        Assert.assertEquals(getDriver().findElement(By.xpath("//table[@id='people']/tbody")).
                getText().contains(USER_NAME), true);
    }

    @Test
    public void testUserChangFullName() {
        final String existedUsername = "Usertest2";
        final String password = "5679";
        final String email = "rv@gmail.com";
        final String fullName = "User User";

        String userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersButton()
                .clickAddUserButton()
                .fillUserInformationField(existedUsername, password, email)
                .clickUserByName(existedUsername)
                .goConfigurePage()
                .sendKeysFullNameUser(fullName)
                .clickSaveButton()
                .getHeadLineText();

        Assert.assertEquals(userName, fullName);
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

}
