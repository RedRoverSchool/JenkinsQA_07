package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class Header5Test extends BaseTest {


    @Test
    public void testRedirectToDashBoardByClickLogoImg()  {

        List<WebElement> menuItemList = getDriver().findElements(By.className("task")) ;

        for (int i = 1; i < menuItemList.size(); i++) {
           getDriver().findElement(By.xpath("//div[contains(@class,'task')]["+i+"]")).click();
           getDriver().findElement(By.id("jenkins-home-link")).click();
           Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).isDisplayed());
        }

    }

}
