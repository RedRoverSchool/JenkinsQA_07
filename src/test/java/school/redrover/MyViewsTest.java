package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.UUID;

public class MyViewsTest extends BaseTest {

    private void createItemForTest(String itemName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testCreateViewWithOptionGlobalView() {

        final String VIEW_NAME = UUID.randomUUID().toString();

        createItemForTest(UUID.randomUUID().toString());

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.name("name")).sendKeys(VIEW_NAME);

        getDriver().findElement(By.xpath("//div/label[@for='hudson.model.ProxyView']")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/ol/li/a[@href='/user/admin/my-views/view/"+VIEW_NAME+"/']")).getText(), VIEW_NAME);

    }
}
