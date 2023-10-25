package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesTest extends BaseTest {

    private void createNewNode(String nodeName) {
        getDriver().findElement(By.xpath("//a[@href = 'computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();

    }

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

    @Test
    public void testRenameNodeValidName() {
        final String nodeName = "TestNode";
        final String newNodeName = "TestNodeRename";

        createNewNode(nodeName);

        getDriver().findElement(By.xpath("//tr[@id = 'node_" + nodeName + "']//a")).click();
        getDriver().findElement(By.xpath("//a[@href= '/computer/" + nodeName + "/configure']")).click();

        getDriver().findElement(By.name("_.name")).sendKeys(newNodeName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement
                (By.tagName("h1")).getText().contains(newNodeName));

    }

    @Test
    public void createNewNodeWithInvalidNameFromMainPanel() {
        final String NODE_NAME = "!";

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector(".jenkins-radio__label")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".error")).getText(),
                "‘!’ is an unsafe character");
    }
}
