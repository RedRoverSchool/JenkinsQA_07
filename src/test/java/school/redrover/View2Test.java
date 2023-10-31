package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View2Test extends BaseTest {

    private void createFreestyleProject (String freestyleProjectName) {
        getDriver().findElement(By.className("task")).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }
    @Test
    public void testCreateNewView () {
        final String myViewName = "testView";

        createFreestyleProject("testProject");

        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.id("name")).sendKeys(myViewName);
        getDriver().findElement(By.xpath("//*[@id=\"createItemForm\"]/div[1]/div[2]/fieldset/div[2]/label")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement getViewName = getDriver().findElement(By.xpath("//a[@href='/view/testView/']"));

        Assert.assertEquals(getViewName.getText(), myViewName);

    }

}
