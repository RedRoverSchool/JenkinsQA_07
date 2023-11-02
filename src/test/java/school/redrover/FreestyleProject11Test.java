package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject11Test extends BaseTest {
    final String name = "Test";
    final String newName = "newTest";

    private void createNewProject(String name){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRenameProject() throws InterruptedException {
        createNewProject("Test");

        getDriver().findElement(By.xpath("//td/a[@href='job/" + name + "/']/span")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + name + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//h1")).getText().equals("Project " + newName)
        );
    }
}
