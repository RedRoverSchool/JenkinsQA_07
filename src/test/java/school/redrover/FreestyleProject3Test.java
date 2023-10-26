package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject3Test extends BaseTest {
    @Test
    public void testNewItemFreestyleProject() {
        final String pipelineName = "New Item Freestyle";

        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.cssSelector("input#name.jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();


        getDriver().findElement(By.name("description")).sendKeys(pipelineName + "description");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(),'"+ pipelineName + "')]")).isDisplayed(), "Everyrhing is working,dont forget to improve yout test");

    }
}
