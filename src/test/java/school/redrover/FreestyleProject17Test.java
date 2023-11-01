package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject17Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {
        final String freestyleProject = "Freestyle Project";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(freestyleProject);
        getDriver().findElement(By.xpath("//span[text() = 'Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String nameOfProject = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertTrue(nameOfProject.contains(freestyleProject));
    }
}
