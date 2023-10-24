package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;


public class ildanaTest extends BaseTest {

    @Test
    public void testNewItemCreate() {

        WebElement newItem = getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']"));
        newItem.click();

        WebElement itemName = getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']"));
        itemName.sendKeys("Ildana Frolova");

        WebElement projectType = getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']"));
        projectType.click();

        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']"));
        saveButton.click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1"))
                        .getText(),"Project Ildana Frolova"
        );

    }

    @Test
    public void testSameItemCreate(){

        WebElement newSameItem = getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']"));
        newSameItem.click();

        WebElement itemSameName = getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']"));
        itemSameName.sendKeys("Ildana Frolova");

        Assert.assertEquals(
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//div[@id='itemname-invalid']"))
                .getText(),"» A job already exists with the name ‘Ildana Frolova’"
        );

    }

}






