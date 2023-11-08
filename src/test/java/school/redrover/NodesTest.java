package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
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

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    private void goToConfigureNodePage(String nodeName) {
        getDriver().findElement(By.xpath("//span[text()='" + nodeName +"']")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[3]/span/a")).click();
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

    @Ignore
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
        goToNodesPage();
        getDriver().findElement(By.linkText("New Node")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//*[text()='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//*[@id='node_" + NODE_NAME + "']/td[2]/a")).getText().contains(NODE_NAME));
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

    @Test
    public void testMarkNodeTemporarilyOffline() {
        createNewNode(NODE_NAME);
        goToMainPage();

        getDriver().findElement(By.xpath("//span[text()='" + NODE_NAME +"']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("message")).getText(),
                "Disconnected by admin"
        );
    }

    @Test
    public void testRenameNodeWithValidName() {
        final String NEW_NAME = "renamed node";

        createNewNode(NODE_NAME);
        goToMainPage();
        goToConfigureNodePage(NODE_NAME);

        getDriver().findElement(By.name("_.name")).clear();
        getDriver().findElement(By.name("_.name")).sendKeys(NEW_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']/h1")).getText(),
                String.format("Agent %s", NEW_NAME)
        );
    }

    @Test
    public void testUpdateOfflineReason() {
        final String newReason = "Updated reason";

        createNewNode(NODE_NAME);
        goToMainPage();

        getDriver().findElement(By.xpath("//span[text()='" + NODE_NAME +"']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'offlineMessage']")).sendKeys("111");
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.xpath("//form[@action = 'setOfflineCause']/button")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'offlineMessage']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name = 'offlineMessage']")).sendKeys(newReason);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class = 'message']")).getText(),
                "Disconnected by admin : " + newReason
        );
    }
}

