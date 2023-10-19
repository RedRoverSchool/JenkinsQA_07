package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupCoffeeCoders extends BaseTest {

    @Test
    public void testProject1() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Project 1");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();

        WebElement description = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea"));
        description.sendKeys("This is my project, which was created for testing Jenkins");
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        WebElement projectName = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/h1"));
        String result = projectName.getText();

        Assert.assertEquals(result, "Project Project 1");
    }

    @Test
    public void testFolder (){
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("New Folder 1");
        getDriver().findElement((By.xpath("//*[@id=\"j-add-item-type-nested-projects\"]/ul/li[1]"))).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/input")).sendKeys("My new folder");
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        getDriver().findElement(By.xpath("//*[@id=\"jenkins-name-icon\"]")).click();
        WebElement folderName = getDriver().findElement(By.xpath("//*[@id=\"job_New Folder 1\"]/td[3]/a/span"));
        String value = folderName.getText();
        Assert.assertEquals(value, "My new folder");

    }
}



