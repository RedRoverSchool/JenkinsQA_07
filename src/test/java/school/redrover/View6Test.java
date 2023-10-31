package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class View6Test extends BaseTest {
    final String LIST_VIEW_NAME = "List View Sv";

    private void createFreestyleProject() {

        getDriver().findElement(By.xpath("//*[@href=\"/view/all/newJob\"]")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("One more Freestyle project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

    }

    @Test
    public void testCreateView() {

        createFreestyleProject();

        getDriver().findElement(By.xpath("//a[@href=\"/newView\"]")).click();
        getDriver().findElement(By.id("name")).sendKeys(LIST_VIEW_NAME);
        getDriver().findElement(By.xpath("//*[@class='jenkins-radio']//*[text()='List View']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit'][@id='ok']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit' and contains(text(), 'OK')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(),
                LIST_VIEW_NAME
        );

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='tabBar']/div[2]/a")).getText(),
                LIST_VIEW_NAME
        );

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='tabBar']/div[2]/a")).getText(),
                LIST_VIEW_NAME
        );
    }
}
