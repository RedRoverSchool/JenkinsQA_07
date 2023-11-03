package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes2Test extends BaseTest {

    @Test
    public void testCreateNodeFromMainPage() {

        final String NODE_NAME = "Test Nodes name";

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.className("jenkins-radio__label")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@id='node_" + NODE_NAME + "']//a")).getText(),
                NODE_NAME
        );
    }
}