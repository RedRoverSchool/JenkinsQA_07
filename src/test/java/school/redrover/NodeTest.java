package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodeTest extends BaseTest {

    private static final String INITIAL_NAME = "nodeName";

    private void createNode(String nodeName) {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Manage Jenkins')]/..")).click();
        getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'New Node')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label[@class ='jenkins-radio__label']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    private void renameNode(String initialNodeName, String newName) {
        getDriver().findElement(By.xpath("//a[contains(text(), '" + initialNodeName + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + initialNodeName + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value = '" + initialNodeName + "']")).sendKeys(newName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test
    public void testRename() {
        final String resultNodeName = "newNodeName";

        createNode(INITIAL_NAME);
        renameNode(INITIAL_NAME, resultNodeName);

        getDriver().findElement(By.xpath("//a[contains(text(), 'Nodes')]")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[contains(text(), '" + resultNodeName + "')]"))
                .isDisplayed());
    }

    @Test
    public void testRenameWithIncorrectName() {
        final String incorrectNodeName = "@";

        createNode(INITIAL_NAME);
        renameNode(INITIAL_NAME, incorrectNodeName);

        Assert.assertEquals(getDriver().findElement(By.id("main-panel")).getText(), "Error\n‘" + incorrectNodeName + "’ is an unsafe character");
    }

    @Test
    public void testAddDescription() {
        final String descriptionText = "description";

        createNode(INITIAL_NAME);

        getDriver().findElement(By.xpath("//a[contains(text(), '" + INITIAL_NAME + "')]")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//div/button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id= 'description']/div[1]")).getText()
                , descriptionText);
    }

    @Test
    public void testAddLabel() {
        final String labelName = "label";

        createNode(INITIAL_NAME);

        getDriver().findElement(By.xpath("//a[contains(text(), '" + INITIAL_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@name = '_.labelString']")).sendKeys(labelName);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[contains(text(), 'Labels')]/..")).getText(),
                "Labels\n" + labelName);
    }
}
