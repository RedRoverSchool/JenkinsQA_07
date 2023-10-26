package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BuildHistoryTest extends BaseTest {
    @Test
    public void testViewBuildHistory() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Build History')]/parent::a")).click();
        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).isDisplayed());
    }

    @Test
    public void testViewBuildHistoryClickableIconLegend() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Build History')]/parent::a")).click();
        getDriver().findElement(By.id("button-icon-legend")).click();

        getDriver().findElement(By.className("jenkins-modal__contents"));

        int containsTwoElement = getDriver().findElements(By.xpath("//h2[contains(text(),'Status')]/following::dl")).size();
        Assert.assertEquals(containsTwoElement, 2);
    }
}