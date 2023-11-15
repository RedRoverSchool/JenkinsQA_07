package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NodesDeleteTest extends BaseTest {
    private static final String NODE_NAME = "NodeNew";

    private void createNode() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector(".jenkins-radio__label")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToNodesPage() {
        getDriver().findElement(By.linkText("Build Executor Status")).click();
    }

    private boolean elementIsNotPresent(String xpath){
        return getDriver().findElements(By.xpath(xpath)).isEmpty();
    }

    private void clickOnDeleteButton(){
        getDriver().findElement(By.xpath("//a[@href='../computer/"+ NODE_NAME +"/']")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[2]/span/a")).click();
    }

    @Test
    public void testCheckAlertMessageInDeleteNewNode() {
        createNode();

        clickOnDeleteButton();

        Assert.assertEquals(getDriver().switchTo().alert().getText(), "Delete the agent ‘"+ NODE_NAME + "’?");
    }

    @Test
    public void testCancelToDeleteNewNodeFromAgentPage() {
        createNode();

        clickOnDeleteButton();

        getDriver().switchTo().alert().dismiss();

        goToNodesPage();

        Assert.assertFalse(elementIsNotPresent("//tr[@id='node_"+ NODE_NAME +"']//a//button"));
    }

    @Test(dependsOnMethods = "testCancelToDeleteNewNodeFromAgentPage")
    public void testDeleteNewNodeFromAgentPage() {
        goToNodesPage();

        clickOnDeleteButton();

        getDriver().switchTo().alert().accept();

        Assert.assertTrue(elementIsNotPresent("//tr[@id='node_"+ NODE_NAME +"']//a"));
    }
}
