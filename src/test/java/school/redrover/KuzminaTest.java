package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class KuzminaTest extends BaseTest {

    @Test
    public void testParksOpening() {
        getDriver().get("https://parks.canada.ca/pn-np");

        String firstTitle = getDriver().getTitle();
        Assert.assertEquals(firstTitle, "National parks");

        WebElement findANationalParkButton = getDriver().findElement(By.xpath("//a[normalize-space()='Find a national park']"));
        findANationalParkButton.click();

        String secondTitle = getDriver().getTitle();
        Assert.assertEquals(secondTitle, "Find a national park");

    }
}
