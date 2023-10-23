package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class GroupJavaExplorersTest extends BaseTest {

    private static final String FOLDER_NAME = "Folder1";
    private static final String USER_NAME = "New_User";
    private static final String PASSWORD = "12345";
    private static final String EMAIL = "asd@gmail.com";

    private void createNewFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void addNewUser() {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.name("username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("password1")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("password2")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("email")).sendKeys(EMAIL);
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateNewFolder() {
        createNewFolder();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_Folder1']/td[3]/a/span"))
                .getText(), "Folder1");
    }

    @Ignore
    @Test
    public void testCreateNewJob() {

        String expectedText = "This view has no jobs associated with it. You can either add " +
                "some existing jobs to this view or create a new job in this view.";

        WebElement newView = getDriver().findElement(By.xpath("//div//a[@title='New View']"));
        newView.click();
        WebElement viewName = getDriver().findElement(By.xpath("//div//input[@id='name']"));
        viewName.sendKeys("MyView2");
        WebElement viewTypeChckbx = getDriver().findElement(By.xpath("//div//label[@for='hudson.model.ListView']"));
        viewTypeChckbx.click();
        WebElement buttonSubmit = getDriver().findElement(By.xpath("//div//button[@name='Submit']"));
        buttonSubmit.click();
        WebElement buttonSubmitView = getDriver().findElement(By.xpath("//div//button[@name='Submit']"));
        buttonSubmitView.click();
        String actualText = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText();

        Assert.assertTrue(actualText.contains(expectedText));
    }

    @Test
    public void testWelcomeToJenkins() {

        String header = getDriver().findElement(By.xpath("//h1")).getText();
        assertEquals(header, "Welcome to Jenkins!");
    }

    @Test
    public void testAddNewUser() {
        addNewUser();

        Assert.assertTrue(getDriver().findElement(By.linkText("New_User")).isDisplayed());
    }

    @Ignore
    @Test()
    public void testCreateFreeStyleProject() {
        int desiredLength = 5;
        String testFreeStyleProjectName = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);

//        JenkinsUtils.login(getDriver());

        WebElement newViewButton = getDriver().findElement(By.xpath("//span[@class='task-icon-link']"));
        newViewButton.click();

        WebElement jenkinsJobNameField = getDriver().findElement(By.xpath("//*[@class='jenkins-input']"));
        jenkinsJobNameField.sendKeys(testFreeStyleProjectName);

        WebElement freeStyleProject = getDriver().findElement(By.xpath("//*[text()='Freestyle project']"));
        freeStyleProject.click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));

        saveButton.click();
        String jenkinsJobName = getDriver().findElement(By.xpath("//*[@class='job-index-headline page-headline']")).getText();

        Assert.assertTrue(jenkinsJobName.contains(testFreeStyleProjectName));
    }

    @Test
    public void testVersion() {

        WebElement version = getDriver().findElement(By.xpath("//button[contains(text(), 'Jenkins 2.414.2')]"));
        String versionName = version.getText();
        Assert.assertEquals(versionName, "Jenkins 2.414.2");
    }

    @Test
    public void testJobEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]")).click();
        final String errorText = getDriver().findElement(By.xpath("//div[@id='itemname-required']")).getText();
        Assert.assertEquals(errorText, "» This field cannot be empty, please enter a valid name");

    }

    @Test
    public void testCreateItemWithInvalidName() {
        List<String> listOfSpecialCharacters =
                List.of("!", "#", "$", "%", "&", "*", "/", ":", ";", "<", ">", "?", "@", "[", "]", "|", "\\", "^");

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        for (String listOfSpecialCharacter : listOfSpecialCharacters) {
            String errorMessage = "» ‘" + listOfSpecialCharacter + "’ is an unsafe character";

            WebElement fieldInputName = getDriver().findElement(By.id("name"));
            fieldInputName.clear();
            fieldInputName.sendKeys(listOfSpecialCharacter);

            String resultMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

            Assert.assertEquals(resultMessage, errorMessage);
        }
    }

    @Test
    public void testAddDescription() {

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("some text");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        final String description = getDriver().findElement(By.xpath("//div[@id='description']")).getText();
        Assert.assertEquals(description, "some text\nEdit description");
    }

    @Test
    public void testDescriptionPreview() {

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("some text");
        getDriver().findElement(By.xpath("//a[contains(text(),'Preview')]")).click();
        final String previewText = getDriver().findElement(By.xpath("//div[@class='textarea-preview']")).getText();
        Assert.assertEquals(previewText, "some text");
        getDriver().findElement(By.xpath("//a[@class='textarea-hide-preview']")).click();
    }
}

