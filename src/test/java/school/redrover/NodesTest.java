package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesTest extends BaseTest {

    @Test
    public void createNewNodeWithValidNameFromMainPanel() {
        final String NODE_NAME = "new node";

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector(".jenkins-radio__label")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        String actualNodeName = getDriver().findElement(By.xpath("//tr[@id='node_" + NODE_NAME + "']//a")).getText();

        Assert.assertEquals(actualNodeName, NODE_NAME);

    }


}
