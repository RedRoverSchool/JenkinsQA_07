package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.MultiConfigurationConfigurePage;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final  String DESCRIPTION = "This is a description!!!";
    private static final  String PROJECT_NAME = "MultiConfigurationProject123";
    private static final  By LINK_ON_A_CREATED_FREESTYLE_PROJECT = By.xpath("//tr[@id='job_" + PROJECT_NAME + "']/td[3]/a");

    private WebElement textDescription() {
        return getDriver().findElement(By.xpath("//*[@class='jenkins-!-margin-bottom-0']//div"));
    }

    private void createProject() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }

    private void addDescriptionIntoProject() {
        getDriver().findElement(By.xpath("//span[normalize-space()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test
    public void testCreate() {
        final String projectName = "MyMulticonfigurationProject";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + projectName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + projectName);
    }

    @Test
    public void testCreateWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectMultiConfigurationProject()
                .clickOk(new MultiConfigurationConfigurePage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(PROJECT_NAME));
    }

    @Test
    public void testCreateWithEmptyName() {
        String validationMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("")
                .selectMultiConfigurationProject()
                .inputValidationMessage();

        Assert.assertTrue(validationMessage.contains("» This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(getDriver().findElement(By.cssSelector(".disabled")).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateWithValidName")
    public void testCreateWithDuplicateName() {
        String duplicateName = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .inputValidationMessage();

        Assert.assertTrue(duplicateName.contains("» A job already exists with the name ‘" + PROJECT_NAME +"’"));
    }

    @Test
    public void testCreateProjectWithDescription() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@class='jenkins-!-margin-bottom-0']//div")).isDisplayed());
        Assert.assertEquals(DESCRIPTION, textDescription().getText());
    }

    @Test
    public void testAddDescription() {
        createProject();
        addDescriptionIntoProject();
        Assert.assertEquals(DESCRIPTION, textDescription().getText());
    }

    @Test
    public void testEditDescription() {
        createProject();
        addDescriptionIntoProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        Assert.assertEquals(DESCRIPTION + DESCRIPTION, textDescription().getText());
    }

    @Test
    public void testDeleteDescription() {
        createProject();
        addDescriptionIntoProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        Assert.assertEquals("", textDescription().getText());
    }


    @Test
    public void testCancelDelete() {
        createProject();
        addDescriptionIntoProject();

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[6]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.dismiss();

        String actualProjectName = getDriver().findElement(By.tagName("h1")).getText();
        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", PROJECT_NAME));
        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test
    public void testDelete() {
        String greetingJenkins = "Welcome to Jenkins!";

        createProject();
        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[6]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        String actualGreetingJenkins = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualGreetingJenkins, greetingJenkins);
    }
}
