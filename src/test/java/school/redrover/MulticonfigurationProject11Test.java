package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.interactions.Actions;

public class MulticonfigurationProject11Test extends BaseTest {

    @Test
    public void testCreateMCProjectClickingNewItemDashboard(){

        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("Test");
        getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        WebElement el = getDriver().findElement(By.xpath("//td/a[@href = 'job/Test/']"));

        new Actions(getDriver()).click(el).perform();


        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project Test");
    }

    @Test
    public void testCreateMCProjectClickingNewItemLeftPanel() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("Test");
        getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        WebElement el = getDriver().findElement(By.xpath("//td/a[@href = 'job/Test/']"));

        new Actions(getDriver()).click(el).perform();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project Test");


    }

}
