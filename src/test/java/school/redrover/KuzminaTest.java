package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class KuzminaTest extends BaseTest {

    @Test
    public void testJenkins() {

        getDriver().findElement(By.id("description-link")).click();

        WebElement textAreaDescription = getDriver().findElement(By.className("jenkins-input"));

        textAreaDescription.sendKeys("My description\nSecond Line");

        getDriver().findElement(By.name("Submit")).click();

        String descriptionText = getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).getText();

        Assert.assertEquals(descriptionText, "My description\nSecond Line");


    }
}
