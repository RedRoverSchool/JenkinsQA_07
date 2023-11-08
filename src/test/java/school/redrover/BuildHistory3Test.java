package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BuildHistory3Test extends BaseTest {

    @Test
    public void testViewBuildHistory() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Build History')]/parent::a"));

        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).isDisplayed());
    }

    @Test
    public void testClickableIconLegend() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Build History')]/parent::a")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary']")).click();

        getDriver().findElement(By.className("jenkins-modal__contents"));

        Assert.assertEquals(getDriver().findElements(By.xpath("//h2[contains(text(),'Status')]/following::dl")).
                size(), 2);
    }
}