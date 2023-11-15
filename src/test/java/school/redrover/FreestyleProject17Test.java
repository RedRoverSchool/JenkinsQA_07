package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject17Test extends BaseTest {
    final String freestyleProject = "Freestyle Project";

    public void createFreestyleProject(){

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(freestyleProject);
        getDriver().findElement(By.xpath("//span[text() = 'Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test
    public void testCreateFreestyleProject() {

        createFreestyleProject();

        String nameOfProject = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertTrue(nameOfProject.contains(freestyleProject));
    }

    @Test
    public void testAddDescription() {
        final String emptyDescription = "";

        createFreestyleProject();

        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();

        getDriver().findElement(By.xpath("//span[text() = 'Freestyle Project']")).click();

        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys("Description for freestyle project");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys("Edited description");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]"))
                .getText(), emptyDescription);
    }
}