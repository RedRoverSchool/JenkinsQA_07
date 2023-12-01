package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "NewNode";
    private static final String NEW_NODE_NAME = "newNodeName";

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

    @Test(dependsOnMethods = "testNodeStatusUpdateOfflineReason")
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

    @Test(dependsOnMethods = "testNodeStatusUpdateOfflineReason")
    public void testRenameNodeWithValidName() {

        String actualName = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NODE_NAME)
                .clickConfigure(new NodeCofigurationPage(getDriver()))
                .clearAndInputNewName(NEW_NODE_NAME)
                .saveButtonClick(new NodeDetailsPage(getDriver()))
                .getNodeName();

        Assert.assertTrue(actualName.contains(NEW_NODE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewNodeWithValidNameFromManageJenkinsPage")
    public void testCreateNewNodeCopyingExistingWithNotExistingName() {

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

    @Test(dependsOnMethods = "testCreateNewNodeWithValidNameFromMainPanel")
    public void testNodeStatusUpdateOfflineReason() {
        final String reasonMessage = "Original Offline Reason Message";
        final String updatedReasonMessage = "Updated Offline Reason Message";

        String offlineReasonMessage = new HomePage(getDriver())
                .clickOnNodeName(NODE_NAME)
                .clickMarkOffline()
                .takingNewNodeOffline(reasonMessage)
                .clickUpdateOfflineReason()
                .setNewNodeOfflineReason(updatedReasonMessage)
                .offlineReasonMessage();

        Assert.assertEquals(offlineReasonMessage,"Updated Offline Reason Message");
    }

    @Test(dependsOnMethods = "testRenameNodeWithValidName")
    public void testRenameWithIncorrectName() {
        final String incorrectNodeName = "@";

        String errorText = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure(new NodeCofigurationPage(getDriver()))
                .clearAndInputNewName(incorrectNodeName)
                .saveButtonClick(new ErrorPage(getDriver()))
                .getErrorFromMainPanel();

        Assert.assertEquals(errorText, "Error\n‘" + incorrectNodeName + "’ is an unsafe character");
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
                .clickConfigure(new NodeCofigurationPage(getDriver()))
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
                .clickConfigure(new NodeCofigurationPage(getDriver()))
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
                .clickConfigure(new NodeCofigurationPage(getDriver()))
                .inputEnormousNumberOfExecutors(Integer.MAX_VALUE);

        Assert.assertEquals(angryErrorPage.getErrorNotification(), "Oops!");
        Assert.assertEquals(angryErrorPage.getErrorMessage(), "A problem occurred while processing the request.");
    }

    @Test(dependsOnMethods = "testCheckWarningMessage")
    public void testSetCorrectNumberOfExecutorsForBuiltInNode() {
        final int numberOfExecutors = 3;

        int number = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName("")
                .clickConfigure(new BuiltInNodeConfigurationPage(getDriver()))
                .inputNumbersOfExecutors(numberOfExecutors)
                .getSizeListBuildExecutors();

        Assert.assertEquals(number, numberOfExecutors);
    }

    @Test(dependsOnMethods = "testSetEnormousNumberOfExecutes")
    public void testCheckWarningMessage() {

        String warning = new HomePage(getDriver())
                .goNodesListPage()
                .clickNodeByName(NEW_NODE_NAME)
                .clickConfigure(new NodeCofigurationPage(getDriver()))
                .inputRemoteRootDirectory("@")
                .clickConfigure(new NodeCofigurationPage(getDriver()))
                .getWarningText();

        Assert.assertEquals(warning,
                "Are you sure you want to use a relative path for the FS root?" +
                        " Note that relative paths require that you can assure that the selected launcher provides" +
                        " a consistent current working directory. Using an absolute path is highly recommended.");
    }

    @Test
    public void testSortNodesInReverseOrder() {
        final List<String> nodes = List.of("Agent1", "Agent2", "Agent3", "Built-In Node");

        List<String> actualNodes = new HomePage(getDriver())
                .goNodesListPage()
                .clickNewNodeButton()
                .sendNodeName(nodes.get(0))
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .clickNewNodeButton()
                .sendNodeName(nodes.get(1))
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .clickNewNodeButton()
                .sendNodeName(nodes.get(2))
                .SelectPermanentAgentRadioButton()
                .clickCreateButton()
                .saveButtonClick(new NodesListPage(getDriver()))
                .clickSortByNameButton()
                .clickSortByNameButton()
                .getNodeList();

        Assert.assertEquals(nodes, actualNodes);
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
