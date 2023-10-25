package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BuildHistoryTest extends BaseTest {
    @Test
    public void testViewBuildHistory() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Build History')]/parent::a")).click();
        getDriver().findElement(By.id("main-panel")).isDisplayed();
    }
}
