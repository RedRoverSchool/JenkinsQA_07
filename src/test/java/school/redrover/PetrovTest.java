package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class PetrovTest extends BaseTest {

    @Test
    public void testTextBoxForm() {

        getDriver().get("https://demoqa.com/text-box");

        WebElement fullName = getDriver().findElement(By.xpath("//*[@id=\"userName\"]"));
        fullName.sendKeys("testName");

        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.sendKeys("test@gmail.com");

        WebElement submitButton = getDriver().findElement(By.id("submit"));
        submitButton.click();

        WebElement nameS = getDriver().findElement(By.id("name"));
        WebElement emailS = getDriver().findElement(By.id("email"));

        Assert.assertEquals(nameS.getText(), "Name:testName");
        Assert.assertEquals(emailS.getText(), "Email:test@gmail.com");
        Assert.assertNotEquals(emailS.getText(), "");
    }

}
