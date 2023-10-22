package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupSurvivorsTest extends BaseTest {
    @Test
    public void testOlgaTransitionAnalysis() {

        getDriver().findElement(
                By.xpath(("//*[@id='jenkins']/footer/div/div[2]/button"))).click();
        getDriver().findElement(
                By.className(("jenkins-dropdown__item"))).click();
        getDriver().findElement(
                By.xpath(("//*[@id='jenkins']/footer/div/div[2]/button"))).click();

        Assert.assertEquals(getDriver().getTitle(), "О Jenkins 2.414.3 [Jenkins]");
    }

}


