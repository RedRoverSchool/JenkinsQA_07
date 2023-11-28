package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "NewNode";
    private static final String NEW_NODE_NAME = "newNodeName";

    private void createNewNode(String nodeName) {
        getDriver().findElement(By.xpath("//a[@href = 'computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    private void goToNodesPage() {
        getDriver().findElement(By.linkText("Build Executor Status")).click();
    }

    private void clickConfigureNode(String nodeName) {
        getDriver().findElement(By.xpath("//a[contains(text(), '" + nodeName + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
    }

    private void renameNode(String oldName, String newName) {
        getDriver().findElement(By.xpath("//input[@value = '" + oldName + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value = '" + oldName + "']")).sendKeys(newName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test
    public void testCreateNewNodeWithValidNameFromMainPanel() {
        List<String> nodeList = new HomePage(getDriver())
                .clickSetUpAnAgent()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .getNodeList();

        Assert.assertTrue(nodeList.contains(NODE_NAME));
    }

    @Test
    public void testCreateNewNodeWithInvalidNameFromMainPanel() {
        final String NODE_NAME = "!";

        String errorMessage = new HomePage(getDriver())
                .clickSetUpAnAgent()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "‘!’ is an unsafe character");
    }

    @Test
    public void testCreateNewNodeWithValidNameFromManageJenkinsPage() {

        List<String> nodeList = new HomePage(getDriver())
                .clickManageJenkins()
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .getNodeList();

        Assert.assertTrue(nodeList.contains(NODE_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateNewNodeWithValidNameFromMainPanel")
    public void testCreateNodeByCopyingExistingNode() {
        final String newNode = "Copy node";

        String nodeName = new HomePage(getDriver())
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(newNode)
                .SelectPermanentAgentRadioButton()
                .SelectCopyExistingNodeRadioButton()
                .sendExistingNodeName(NODE_NAME)
                .clickCreateButton()
                .saveButtonClick(new NodeDetailsPage(getDriver()))
                .getNodeName();

        Assert.assertTrue(nodeName.contains(newNode));
    }

    @Test(dependsOnMethods = "testCreateNewNodeWithValidNameFromMainPanel")
    public void testMarkNodeTemporarilyOffline() {

        String message = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NODE_NAME)
                .clickMarkOffline()
                .saveChanges()
                .getMessage();

        Assert.assertEquals(message, "Disconnected by admin");
    }

    @Test(dependsOnMethods = "testMarkNodeTemporarilyOffline")
    public void testRenameNodeWithValidName() {
        final String newName = "Renamed node";

        String actualName = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NODE_NAME)
                .clickConfigure()
                .clearAndInputNewName(newName)
                .saveButtonClick(new NodeDetailsPage(getDriver()))
                .getNodeName();

        Assert.assertTrue(actualName.contains(newName));
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

    @Ignore
    @Test
    public void testUpdateOfflineReason() {
        final String newReason = "Updated reason";

        createNewNode(NODE_NAME);
        goToMainPage();

        getDriver().findElement(By.xpath("//span[text()='" + NODE_NAME + "']")).click();
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

    @Test
    public void testCreateNewNodeCopyingExistingWithNotExistingName() {
        HomePage newNode = new HomePage(getDriver())
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new HomePage(getDriver()))
                .goHomePage();

        String errorMassage = new HomePage(getDriver())
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NEW_NODE_NAME)
                .SelectCopyExistingNodeRadioButton()
                .sendExistingNodeName(NODE_NAME + "xxx")
                .clickCreateButton(new ErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorMassage.contains("No such agent"));
    }

    @Test
    public void testNodeStatusUpdateOfflineReason() {
        createNewNode(NODE_NAME);
        goToMainPage();
        final String reasonMessage = "New No Reason";

        getDriver().findElement(By.xpath("//span[text()='" + NODE_NAME + "']")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.name("offlineMessage")).sendKeys("No Reason");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//form[@action = 'setOfflineCause']/button")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'offlineMessage']")).clear();

        getDriver().findElement(By.xpath("//textarea[@name = 'offlineMessage']")).sendKeys(reasonMessage);
        getDriver().findElement(By.name("Submit")).click();

        String message = getDriver().findElement(By.xpath("//div[@class='message']")).getText();

        Assert.assertEquals(message.substring(message.indexOf(':') + 1).trim(), reasonMessage);
    }

    @Test
    public void testCreate() {
        goToNodesPage();

        getDriver().findElement(By.xpath("//a[contains(text(), 'New Node')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class ='jenkins-radio__label']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        goToNodesPage();
        clickConfigureNode(NODE_NAME);
        renameNode(NODE_NAME, NEW_NODE_NAME);

        getDriver().findElement(By.xpath("//a[contains(text(), 'Nodes')]")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]"))
                .isDisplayed());
    }

    @Test(dependsOnMethods = "testRename")
    public void testRenameWithIncorrectName() {
        final String incorrectNodeName = "@";

        goToNodesPage();
        clickConfigureNode(NEW_NODE_NAME);
        renameNode(NEW_NODE_NAME, incorrectNodeName);

        Assert.assertEquals(getDriver().findElement(By.id("main-panel")).getText(), "Error\n‘" + incorrectNodeName + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testRenameWithIncorrectName")
    public void testAddDescription() {
        final String descriptionText = "description";

        String actualDescription = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .inputDescription(descriptionText)
                .getDescriptionText();

        Assert.assertEquals(actualDescription, descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testAddLabel() {
        final String labelName = "label";

        String labelText = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure()
                .inputLabelName(labelName)
                .getLabelText();

        Assert.assertEquals(labelText, labelName);
    }

    @Test(dependsOnMethods = "testAddLabel")
    public void testSetIncorrectNumberOfExecutes() {
        final int numberOfExecutes = -1;

        String errorMessage = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure()
                .inputInvalidNumberOfExecutors(numberOfExecutes)
                .getErrorMessage();

        Assert.assertEquals(errorMessage,
                "Invalid agent configuration for " + NEW_NODE_NAME + ". Invalid number of executors.");
    }

    @Test(dependsOnMethods = "testSetIncorrectNumberOfExecutes")
    public void testSetEnormousNumberOfExecutes() {

        AngryErrorPage angryErrorPage = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure()
                .inputEnormousNumberOfExecutors(Integer.MAX_VALUE);

        Assert.assertEquals(angryErrorPage.getErrorNotification(), "Oops!");
        Assert.assertEquals(angryErrorPage.getErrorMessage(), "A problem occurred while processing the request.");
    }

    @Test(dependsOnMethods = "testCheckWarningMessage")
    public void testSetCorrectNumberOfExecutorsForBuiltInNode() {
        final int numberOfExecutors = 5;

        goToNodesPage();
        clickConfigureNode("Built-In Node");
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]")).clear();
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]"))
                .sendKeys(String.valueOf(numberOfExecutors));
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        List<WebElement> listExecutors = getDriver().findElements(By.xpath("//div[@id = 'executors']//table//tr/td[1]"));
        Assert.assertEquals(listExecutors.size(), numberOfExecutors);
    }

    @Test(dependsOnMethods = "testSetEnormousNumberOfExecutes")
    public void testCheckWarningMessage() {

        String warning = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure()
                .inputRemoteRootDirectory("@")
                .clickConfigure()
                .getWarningText();

        Assert.assertEquals(warning,
                "Are you sure you want to use a relative path for the FS root?" +
                        " Note that relative paths require that you can assure that the selected launcher provides" +
                        " a consistent current working directory. Using an absolute path is highly recommended.");
    }

    @Test
    public void testSortNodesInReverseOrder() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        Actions actions = new Actions(getDriver());
        actions.scrollToElement(getDriver().findElement(By.xpath("//a[@href='computer']")))
                .perform();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        for (int i = 1; i <= 3; i++) {
            String NODE_NAME = "Agent" + i;
            getDriver().findElement(By.xpath("//a[@href='new']")).click();
            getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NODE_NAME);
            getDriver().findElement(By.className("jenkins-radio__label")).click();
            getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
            getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        }

        List<String> originalTextList = new ArrayList<>();
        originalTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent1/']"))
                .getText());
        originalTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent2/']"))
                .getText());
        originalTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent3/']"))
                .getText());
        originalTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/(built-in)/']"))
                .getText());

        List<String> expectedSortedTextList = new ArrayList<>(originalTextList);
        Collections.reverse(expectedSortedTextList);

        getDriver().findElement(By.xpath("//th[@initialsortdir='down']")).click();

        List<String> actualSortedTextList = new ArrayList<>();
        actualSortedTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/(built-in)/']")).getText());
        actualSortedTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent3/']")).getText());
        actualSortedTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent2/']")).getText());
        actualSortedTextList.add(getDriver().findElement(By.xpath("//a[@href = '../computer/Agent1/']")).getText());

        Assert.assertEquals(actualSortedTextList, expectedSortedTextList);

    }

    @Test
    public void testCheckAlertMessageInDeleteNewNode() {
        final String expectedAlertText = "Delete the agent ‘" + NODE_NAME + "’?";

        String actualAlertText = new HomePage(getDriver())
                .clickManageJenkins()
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .clickNodeByName(NODE_NAME)
                .clickDeleteAgentButton()
                .switchToAlertAndGetText();

        Assert.assertEquals(actualAlertText, expectedAlertText);
    }

    @Test
    public void testCancelToDeleteNewNodeFromAgentPage() {
        String nodeName = "//tr[@id='node_" + NODE_NAME + "']//a//button";

        boolean newNode = new HomePage(getDriver())
                .clickManageJenkins()
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NODE_NAME)
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .clickNodeByName(NODE_NAME)
                .clickDeleteAgentButton()
                .dismissAlert()
                .goNodesListPage()
                .elementIsNotPresent(nodeName);

        Assert.assertFalse(newNode);
    }

    @Test(dependsOnMethods = "testCancelToDeleteNewNodeFromAgentPage")
    public void testDeleteNewNodeFromAgentPage() {
        String nodeName = "//tr[@id='node_" + NODE_NAME + "']//a";

        boolean deletedNode = new HomePage(getDriver())
                .clickManageJenkins()
                .goNodesListPage()
                .clickNodeByName(NODE_NAME)
                .clickDeleteAgentButton()
                .acceptAlert()
                .elementIsNotPresent(nodeName);

        Assert.assertTrue(deletedNode);
    }

    @Test
    public void testCreatingNodeWithoutPermanentAgent() {
        boolean visibleButton = new HomePage(getDriver())
                .clickManageJenkins()
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(NODE_NAME)
                .enabledCreateButton();

        Assert.assertFalse(visibleButton);
    }
}
