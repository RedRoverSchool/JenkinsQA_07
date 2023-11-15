package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nodes4Test extends BaseTest {

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
}
