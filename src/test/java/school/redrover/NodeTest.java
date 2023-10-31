package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodeTest extends BaseTest {

    private void clickSubmitButton() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }
    @Test
    public void testRenameNode() {
        final String initialNodeName = "nodeName";
        final String resultNodeName = "newNodeName";

        getDriver().findElement(By.xpath("//span[contains(text(), 'Manage Jenkins')]/..")).click();
        getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'New Node')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(initialNodeName);
        getDriver().findElement(By.xpath("//label[@class ='jenkins-radio__label']")).click();
        clickSubmitButton();
        clickSubmitButton();

        getDriver().findElement(By.xpath("//a[contains(text(), '" + initialNodeName + "')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + initialNodeName + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value = '" + initialNodeName + "']")).sendKeys(resultNodeName);
        clickSubmitButton();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Nodes')]")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[contains(text(), '" + resultNodeName + "')]")).isDisplayed());
    }
}
