package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NodeTest extends BaseTest {

    private static final String NODE_NAME = "nodeName";
    private static final String NEW_NODE_NAME = "newNodeName";

    private void goToNodesPage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
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

        goToNodesPage();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//div/button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id= 'description']/div[1]")).getText()
                , descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testAddLabel() {
        final String labelName = "label";

        goToNodesPage();

        clickConfigureNode(NEW_NODE_NAME);
        getDriver().findElement(By.xpath("//input[@name = '_.labelString']")).sendKeys(labelName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[contains(text(), 'Labels')]/..")).getText(),
                "Labels\n" + labelName);
    }

    @Test(dependsOnMethods = "testAddLabel")
    public void testSetIncorrectNumberOfExecutes() {
        final int numberOfExecutes = -1;

        goToNodesPage();
        clickConfigureNode(NEW_NODE_NAME);
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]"))
                .sendKeys(String.valueOf(numberOfExecutes));
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("main-panel")).getText(),
                "Error\nInvalid agent configuration for " + NEW_NODE_NAME + ". Invalid number of executors.");
    }

    @Test(dependsOnMethods = "testSetIncorrectNumberOfExecutes")
    public void testSetEnormousNumberOfExecutes() {

        goToNodesPage();
        clickConfigureNode(NEW_NODE_NAME);
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]"))
                .sendKeys(String.valueOf(Integer.MAX_VALUE));
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText().trim(), "Oops!");
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(),
                "A problem occurred while processing the request.");
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
        goToNodesPage();
        clickConfigureNode(NEW_NODE_NAME);

        getDriver().findElement(By.xpath("//input[@name = '_.remoteFS']")).sendKeys("@");
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();

        WebElement warningMessage = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class = 'warning']")));
        Assert.assertEquals(warningMessage.getText(),
                "Are you sure you want to use a relative path for the FS root?" +
                        " Note that relative paths require that you can assure that the selected launcher provides" +
                        " a consistent current working directory. Using an absolute path is highly recommended.");

        Assert.assertEquals(Color.fromString(warningMessage.getCssValue("color")).asHex(), "#fe820a");
    }
}
