package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

public class GroupPlusThreeTest extends BaseTest {
    
    @Test (description = "Test creating new item")
    public void testNewItem () {
        
    final String itemName = "Akiko";
    getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
    getDriver().findElement(By.id("name")).sendKeys(itemName);
    getDriver().findElement(By.xpath("//li[@class = 'hudson_model_FreeStyleProject']")).click();
    getDriver().findElement(By.id("ok-button")).click();
    getDriver().findElement(By.name("Submit")).click();
    

    Assert.assertNotNull(getDriver().findElement(By.xpath("//a[@href = '/job/" + itemName + "/']")), "Ссылка на страницу созданного Item не была найдена");
    Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + itemName);

    }
}