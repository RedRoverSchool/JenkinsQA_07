package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {

    private void createMulticonfigurationProject (String MulticonfigurationProjectName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(MulticonfigurationProjectName);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createMulticonfigurationProjectWithDescription(String projectName, String description) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateWithValidName() {
        final String MulticonfigurationProjectName = "MCProjectName";
        createMulticonfigurationProject (MulticonfigurationProjectName);

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + MulticonfigurationProjectName + "/']")).click();

        Assert.assertEquals(getDriver().getTitle(),MulticonfigurationProjectName + " [Jenkins]");
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
        final String multiconfigurationProjectName = "MCProjectName";
        createMulticonfigurationProject (multiconfigurationProjectName);

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(multiconfigurationProjectName);

        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"itemname-invalid\"]")).getText(),
                "» A job already exists with the name ‘" + multiconfigurationProjectName + "’");


        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#main-panel")).getText().contains("A job already exists with the name ‘" + multiconfigurationProjectName + "’"));

    }

    @Test
    public void testCreateWithDescription() {
        String projectName = "MyMulticonfiguration project";
        String description = "Description";

        createMulticonfigurationProjectWithDescription(projectName, description);

          String actualProjectName = getDriver().findElement(By.tagName("h1")).getText();
          String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", projectName));
        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testEditDescription() {
        String projectName = "MyMulticonfiguration project";
        String description = "Description";
        String newDescription = "New Description";

        createMulticonfigurationProjectWithDescription(projectName, description);

        getDriver().findElement(By.id("description-link")).click();
        WebElement descriptionield = getDriver().findElement(By.tagName("textarea"));
        descriptionield.clear();
        descriptionield.sendKeys(newDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        String actualProjectName = getDriver().findElement(By.tagName("h1")).getText();
        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", projectName));
        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test
    public void testCancelDelete() {
        String projectName = "MyMulticonfiguration project";
        String description = "Description";

        createMulticonfigurationProjectWithDescription(projectName, description);
        getDriver().findElement(By.xpath("//a[@data-url='/job/MyMulticonfiguration%20project/doDelete']")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.dismiss();

        String actualProjectName = getDriver().findElement(By.tagName("h1")).getText();
        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", projectName));
        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testDelete() {
        String projectName = "MyMulticonfiguration project";
        String description = "Description";
        String greetingJenkins = "Welcome to Jenkins!";

        createMulticonfigurationProjectWithDescription(projectName, description);
        getDriver().findElement(By.xpath("//a[@data-url='/job/MyMulticonfiguration%20project/doDelete']")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        String actualGreetingJenkins = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualGreetingJenkins, greetingJenkins);
    }
}
