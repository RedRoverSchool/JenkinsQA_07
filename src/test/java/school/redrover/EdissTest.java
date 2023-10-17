package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class EdissTest extends BaseTest {

    @Test
    public void SearchBoxVerification() throws InterruptedException {
        getDriver().get("https://www.google.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(9000));
        WebElement SearchBox = getDriver().findElement(By.name("q"));
        SearchBox.sendKeys("Duck");

        Thread.sleep(1000);
        SearchBox.submit();

        WebElement title = getDriver().findElement(By.id("result-stats"));
        String value = title.getText();
        System.out.println(value);
        WebElement dictionary = getDriver().findElement(By.xpath("//div[@class='VpH2eb vmod']//span[@data-dobid='hdw'][normalize-space()='duck']"));
        String assertduck = dictionary.getText();
        Assert.assertEquals(assertduck,"duck");
    }

}
