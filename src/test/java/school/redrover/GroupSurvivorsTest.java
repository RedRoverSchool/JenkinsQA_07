package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupSurvivorsTest extends BaseTest {
  @Test
    public void testOlgaEditDescription() {

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(
                By.xpath("//*[@name = 'description']")).sendKeys("Test description");
        getDriver().findElement(
                By.xpath("//*[@id = 'description']/form/div[2]/button")).click();
        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//*[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//*[@name = 'description']")).sendKeys("Test new");
        getDriver().findElement(By.xpath("//*[@id = 'description']/form/div[2]/button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id = 'description']/div")).getText(),"Test new");

    }
}


