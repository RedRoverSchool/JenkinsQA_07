package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesAllTest extends BaseTest {
    public static final String NODE_TEST = "NodeNameTest";

    private void goToNodePage() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer'] ")).click();

    }

    @Test
    public void testNewNodeCreate() {

        goToNodePage();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_TEST);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//a[contains(text(), '" + NODE_TEST + "')]")).isDisplayed());
    }

    @Test(dependsOnMethods = "testNewNodeCreate")
    public void testNodeAddDescription() {
        final String descriptionTxt = "node description text";

        goToNodePage();

        getDriver().findElement(By.xpath("//a[@href='../computer/NodeNameTest/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(descriptionTxt);
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id= 'description']/div[1]")).getText(), descriptionTxt);
    }
}
