package school.redrover;

import org.openqa.selenium.By;
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
    public void testCreateWithDescription() {
        String projectName = "MyMulticonfiguration project";
        String description = "Description";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();
        String actualProjectName = getDriver().findElement(By.tagName("h1")).getText();
        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualProjectName, String.format("Project %s", projectName));
        Assert.assertEquals(actualDescription, description);


    }
}
