package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject17Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {
        final String freestyleProject = "Freestyle Project";

        //"New item" element
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        //Enter project name
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(freestyleProject);

        //Choose "Freestyle project" type
        getDriver().findElement(By.xpath("//span[text() = 'Freestyle project']")).click();

        //CLick on "OK" button
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        ///Click on "Save" button
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        //Check that project was created
        String nameOfProject = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertTrue(nameOfProject.contains(freestyleProject));
    }
}
