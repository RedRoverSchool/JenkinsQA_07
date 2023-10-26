package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Breadcrumb2Test extends BaseTest {

    private void clickOnBuildHistoryBtn() {

        String builtHistoryBtn = "Built History";
        List<WebElement> options = getDriver().findElements(By.xpath("//div[@class = 'task ']"));
        for (WebElement item : options) {
            if (item.getText().equals(builtHistoryBtn)) {
                item.click();
            }
        }
    }

    @Test
    public void testReturnOnMainPageByClickDashboardBtn() {

        clickOnBuildHistoryBtn();

        getDriver().findElement(By.xpath("//li/a[@class = 'model-link']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//h1"))
                        .getText(),
                "Welcome to Jenkins!");
    }
}

