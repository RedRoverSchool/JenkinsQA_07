package school.redrover;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProjectIldanaTest extends BaseTest {
    private void createFreeStyleProject2(String projectName){
        getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']")).sendKeys("Ildana Frolova");
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

    }

    private void addDescription(String textDescription){
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(textDescription);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();
    }




    @Test
    public void testAddDescription2FreestyleProject() {
        final String projectName = "FreestyleProjectIldana";
        final String descriptionText = "Test add description to project";

        createFreeStyleProject2(projectName);

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),descriptionText
        );

    }

    @Test
    public void testEditDescription2() {
        final String projectName = "FreestyleProjectIldana";
        final String descriptionInitialText = "Test add description to project";
        final String descriptionChangeText = "Test change description to project";

        createFreeStyleProject2(projectName);
        addDescription(descriptionInitialText);

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        WebElement textArea = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        textArea.clear();
        textArea.sendKeys(descriptionChangeText);
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),descriptionChangeText
        );

    }

    @Test
    public void testDeleteDescription2() {
        final String projectName = "FreestyleProjectIldana";
        final String descriptionText = "Test add description to project";

        createFreeStyleProject2(projectName);
        addDescription(descriptionText);

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//form[@action='submitDescription']//button[@name='Submit']")).click();

        WebElement afterClear =  getDriver().findElement(By.xpath("//div[@id='description']/div[1]"));

        String textAfterClear = afterClear.getText();

        Assert.assertTrue(textAfterClear.isEmpty(), " ");

    }

}

