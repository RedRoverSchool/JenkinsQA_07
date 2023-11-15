package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View2Test extends BaseTest {

    private static final String MY_VIEW_NAME = "testView";
    private static final String FREESTYLE_PROJECT_NAME = "testProject";
    private static final String NEW_VIEW_NAME = "renamedView";

    private void createFreestyleProject () {
        getDriver().findElement(By.className("task")).click();
        getDriver().findElement(By.id("name")).sendKeys(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateNewView () {
        createFreestyleProject();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.id("name")).sendKeys(MY_VIEW_NAME);
        getDriver().findElement(By.xpath("//*[@id='createItemForm']/div[1]/div[2]/fieldset/div[2]/label")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement getViewName = getDriver().findElement(By.xpath("//a[@href='/view/"+ MY_VIEW_NAME +"/']"));

        Assert.assertEquals(getViewName.getText(), MY_VIEW_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewView")
    public void testRenameView () {
        getDriver().findElement(By.xpath("//a[normalize-space()='testView']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + MY_VIEW_NAME + "/configure']")).click();
        getDriver().findElement(By.name("name")).clear();
        getDriver().findElement(By.name("name")).sendKeys(NEW_VIEW_NAME);
        getDriver().findElement(By.name("Submit")).click();

        WebElement getNewViewName = getDriver().findElement(By.xpath("//a[@href='/view/"+ NEW_VIEW_NAME +"/']"));

        Assert.assertEquals(getNewViewName.getText(), NEW_VIEW_NAME);
    }

    @Test(dependsOnMethods = {"testCreateNewView", "testRenameView"})
    public void testDeleteView () {
        getDriver().findElement(By.xpath("//a[@href='/view/"+ NEW_VIEW_NAME +"/']")).click();
        getDriver().findElement(By.xpath("//span[text()='Delete View']")).click();
        getDriver().switchTo().alert().accept();

        String getTabBarText = getDriver().findElement(By.xpath("//div[@class='tab']")).getText();

        Assert.assertFalse(getTabBarText.contains(NEW_VIEW_NAME));
    }

}
