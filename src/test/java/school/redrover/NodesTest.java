package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "new node";

    private void createNewNode(String nodeName) {
        getDriver().findElement(By.xpath("//a[@href = 'computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToNodesPage() {
        getDriver().findElement(By.linkText("Build Executor Status")).click();
    }

    @Test
    public void testCreateNewNodeWithValidNameFromMainPanel() {

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
    public void testCreateNewNodeWithInvalidNameFromMainPanel() {
        final String NODE_NAME = "!";

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector(".jenkins-radio__label")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".error")).getText(),
                "‘!’ is an unsafe character");
    }

    @Test
    public void testCreateNewNodeByBuildExecutorInSidePanelMenu() {
        getDriver().findElement(By.linkText("Build Executor Status")).click();
        getDriver().findElement(By.linkText("New Node")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("NewTEST2023");
        getDriver().findElement(By.xpath("//*[text()='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//*[@id='node_NewTEST2023']/td[2]/a")).getText().contains("NewTEST2023"));
    }

    @Test
    public void testCreateNewNodeWithValidNameFromManageJenkinsPage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector(".jenkins-radio__label")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        String actualNodeName = getDriver().findElement(By.xpath("//tr[@id='node_" + NODE_NAME + "']//a")).getText();

        Assert.assertEquals(actualNodeName, NODE_NAME);
    }

    @Test
    public void testCreateNodeByCopyingExistingNode() {
        createNewNode(NODE_NAME);

        getDriver().findElement(By.linkText("Build Executor Status")).click();
        getDriver().findElement(By.linkText("New Node")).click();
        getDriver().findElement(By.id("name")).sendKeys("copy");
        getDriver().findElement(By.xpath("//label[@for='copy']")).click();
        getDriver().findElement(By.name("from")).sendKeys(NODE_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        goToNodesPage();

        String actualNodeName = getDriver().findElement(By.xpath("//tr[@id='node_copy']//a")).getText();

        Assert.assertEquals(actualNodeName, "copy");
    }

    @Test
    public void testCreateNewNodeFromNodesSectionInManageJenkinsPage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Nodes']")).click();
        getDriver().findElement(By.linkText("New Node")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//*[text()='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//*[@id='node_" + NODE_NAME + "']/td[2]/a")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//h1")).getText().contains(NODE_NAME));

    }
}

