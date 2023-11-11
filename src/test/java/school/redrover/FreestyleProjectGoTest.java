package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectGoTest extends BaseTest {
    @Test
    public void testCreateNewItem() {
        final String itemName = "newJob";
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(
                By.xpath("//div/input[@class='jenkins-input']")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']/label[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        Assert.assertEquals(getDriver().findElements(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a")).size(), 0);
    }
    @Test(dependsOnMethods = "testCreateNewItem")
    public void testConfigureAdventSettingS() {
        final String itemName = "newJob";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + itemName + "')]")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(6)")).click();
        getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[9]/div[1]/div/span/label"))
                .click();
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys("3");


        JavascriptExecutor is = (JavascriptExecutor) getDriver();
        is.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(
                By.cssSelector("#yui-gen13")));

        getDriver().findElement(By.cssSelector("#yui-gen13")).click();
        getDriver().findElement(By.id("yui-gen22")).click();
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']"))
                .sendKeys("Hello Jenkins!");
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span ")).click();

        Assert.assertNotEquals(getDriver().findElement(By.id("no-builds")).getText(), "No Builds");
    }
}



