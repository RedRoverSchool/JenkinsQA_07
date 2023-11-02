package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import school.redrover.runner.BaseTest;

public class BuildHistory2Test extends BaseTest {

    @Test
    public void testCheckDateAndMonthBuildHistory() {
        Date systemDate = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        String dateNow = formatForDateNow.format(systemDate);

        getDriver().findElement(By.className("content-block__link")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("Test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.cssSelector("a[href='/job/Test/build?delay=0sec']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='model-link inside build-link']")).getText().substring(0, 5), dateNow);
    }
}