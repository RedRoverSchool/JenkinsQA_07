package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodeTest extends BaseTest {

    private static final String NODE_NAME = "nodeName";
    private static final String NEW_NODE_NAME = "newNodeName";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Manage Jenkins')]/..")).click();
        getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'New Node')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class ='jenkins-radio__label']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NODE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + NODE_NAME + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value = '" + NODE_NAME + "']")).sendKeys(NEW_NODE_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Nodes')]")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]"))
                .isDisplayed());
    }

    @Test(dependsOnMethods = "testRename")
    public void testRenameWithIncorrectName() {
        final String incorrectNodeName = "@";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + NEW_NODE_NAME + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value = '" + NEW_NODE_NAME + "']")).sendKeys(incorrectNodeName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("main-panel")).getText(), "Error\n‘" + incorrectNodeName + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testRename")
    public void testAddDescription() {
        final String descriptionText = "description";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//div/button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id= 'description']/div[1]")).getText()
                , descriptionText);
    }

    @Test(dependsOnMethods = "testRename")
    public void testAddLabel() {
        final String labelName = "label";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@name = '_.labelString']")).sendKeys(labelName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[contains(text(), 'Labels')]/..")).getText(),
                "Labels\n" + labelName);
    }

    @Test(dependsOnMethods = "testRename")
    public void testSetIncorrectNumberOfExecutes() {
        final int numberOfExecutes = -1;

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]"))
                .sendKeys(String.valueOf(numberOfExecutes));
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("main-panel")).getText(),
                "Error\nInvalid agent configuration for " + NEW_NODE_NAME + ". Invalid number of executors.");
    }

    @Test(dependsOnMethods = "testRename")
    public void testSetEnormousNumberOfExecutes() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + NEW_NODE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[contains(@name, 'numExecutors')]"))
                .sendKeys(String.valueOf(Integer.MAX_VALUE));
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText().trim(), "Oops!");
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(),
                "A problem occurred while processing the request.");
    }
}
