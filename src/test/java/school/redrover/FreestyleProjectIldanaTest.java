package school.redrover;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProjectIldanaTest extends BaseTest {
    private final static String PROJECT_NAME = "Ildana Frolova";
    private final static String DESCRIPTION_TEXT = "Test add description to project";

    private void goHomePage() {
        getDriver().findElement(By.xpath("//ol[@id=\"breadcrumbs\"]/li[1]"));
    }

    @Test
    public void testCreateFreeStyleProject2() {

        getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//tr[@id= 'job_" + PROJECT_NAME + "' ] //td[3]/a")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreeStyleProject2")
    public void testAddDescription2FreestyleProject() {

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), DESCRIPTION_TEXT
        );

    }

    @Test(dependsOnMethods = "testAddDescription2FreestyleProject")
    public void testEditDescription2() {

        final String descriptionChangeText = "Test change description to project";

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        WebElement textArea = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        textArea.clear();
        textArea.sendKeys(descriptionChangeText);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), descriptionChangeText
        );

    }

    @Test(dependsOnMethods = "testAddDescription2FreestyleProject")
    public void testDeleteDescription2() {

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        WebElement afterClear = getDriver().findElement(By.xpath("//div[@id='description']/div[1]"));
        String textAfterClear = afterClear.getText();

        Assert.assertTrue(textAfterClear.isEmpty(), " ");

    }

    }

