package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes3Test extends BaseTest {

    @Test
    public void testCreateNode() {
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span > a > span.task-icon-link")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/section[2]/div/div[4]/a")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/div/div[2]/a")).click();

        getDriver().findElement(By.id("name")).sendKeys("Test");
        getDriver().findElement(By.xpath("//*[@id='createItemForm']/div[1]/div[2]/fieldset/div[1]/label")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        Assert.assertTrue(
                getDriver().findElement(By.id("main-panel")).getText().contains("Test"));
    }

}
