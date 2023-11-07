package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes2Test extends BaseTest {
    final String NODE_NAME = "Node Name";

    private void createNode() {

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.className("jenkins-radio__label")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateNodeFromMainPage() {

        final String TEST_NODE_NAME = "Test Nodes name";

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(TEST_NODE_NAME);
        getDriver().findElement(By.className("jenkins-radio__label")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@id='node_" + TEST_NODE_NAME + "']//a")).getText(),
                TEST_NODE_NAME
        );
    }

    @Test
    public void testRenameExistingNode() {

        final String RENAMED_NODE_NAME = "Renamed Node";

        createNode();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a"));

        getDriver().findElement(By.xpath("//*[@id='executors']/div[1]")).click();

        getDriver().findElement(By.xpath("//*[@id='node_" + NODE_NAME + "']/td[9]/div/a")).click();

        getDriver().findElement(By.name("_.name")).clear();
        getDriver().findElement(By.name("_.name")).sendKeys(RENAMED_NODE_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1")).getText().contains(RENAMED_NODE_NAME));

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).getText(),
                RENAMED_NODE_NAME
        );

    }

}