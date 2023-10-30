package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class KuzminaTest extends BaseTest {

    @Test
    public void testAddedDescription() {

        String myInputText = "My description\nSecond Line";

        getDriver().findElement(By.id("description-link")).click();

        WebElement textAreaDescription = getDriver().findElement(By.className("jenkins-input"));
        textAreaDescription.sendKeys(myInputText);
        getDriver().findElement(By.name("Submit")).click();

        String descriptionText = getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).getText();

        Assert.assertEquals(descriptionText, myInputText);

    }
}
