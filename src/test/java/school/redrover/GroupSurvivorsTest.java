package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupSurvivorsTest extends BaseTest {

    @Test
    public void  testEvgenyFindJavaPage() throws InterruptedException {
        getDriver().get("https://ru.wikipedia.org/wiki/");

        getDriver().findElement(By.className("vector-search-box-input")).sendKeys("Java");

        getDriver().findElement(By.xpath("//input[@class='searchButton']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("mw-page-title-main")).getText(), "Java");
    }
}