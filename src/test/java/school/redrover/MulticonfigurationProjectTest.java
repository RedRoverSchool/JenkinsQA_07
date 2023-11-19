package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final  String DESCRIPTION = "This is a description!!!";
    private static final  String PROJECTNAME = "MulticonfigurationProject";
    private final static By LINK_ON_A_CREATED_FREESTYLE_PROJECT = By.xpath("//tr[@id='job_" + PROJECTNAME + "']/td[3]/a");

    private WebElement textDescription() {
        return getDriver().findElement(By.xpath("//*[@class='jenkins-!-margin-bottom-0']//div"));
    }

    private void createProject() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECTNAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }

    private void addDescriptionIntoProject() {
        getDriver().findElement(By.xpath("//span[normalize-space()='" + PROJECTNAME + "']")).click();
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
        createProject();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + PROJECTNAME + "/']")).click();

        Assert.assertEquals(getDriver().getTitle(),PROJECTNAME + " [Jenkins]");
    }

    @Test
    public void testCreateWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"itemname-required\"]")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertTrue(
                getDriver().findElement(By.cssSelector(".disabled")).isDisplayed());
    }

    @Test
    public void testCreateWithDublicateName() {
        createProject();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECTNAME);

        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"itemname-invalid\"]")).getText(),
                "» A job already exists with the name ‘" + PROJECTNAME + "’");


        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#main-panel")).getText().contains("A job already exists with the name ‘" + PROJECTNAME + "’"));
    }

    @Test
    public void testCreateProjectWithDescription() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECTNAME);
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

        Assert.assertEquals(actualProjectName, String.format("Project %s",PROJECTNAME));
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
