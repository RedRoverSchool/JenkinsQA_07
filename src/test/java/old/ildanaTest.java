package old;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class ildanaTest extends BaseTest {
    @Test
    public void testNewItemCreate() {

        getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']")).sendKeys("Ildana Frolova");
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1"))
                        .getText(),"Project Ildana Frolova"
        );

    }

    @Test
    public void testSameItemCreate(){

        getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@class='add-item-name']//input[@name='name']")).sendKeys("Ildana Frolova");
        getDriver().findElement(By.xpath("//ul[@class='j-item-options']/li[@class='hudson_model_FreeStyleProject']")).click();

        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Ildana Frolova");

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='add-item-name']//div[@id='itemname-invalid']"))
                        .getText(),"» A job already exists with the name ‘Ildana Frolova’"
        );

    }

}








