package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Footer2Test extends BaseTest {

    @Test
    public void testRestApiClickability(){
        getDriver().findElement(By.linkText("REST API")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='REST API']")).isDisplayed());
    }
}
