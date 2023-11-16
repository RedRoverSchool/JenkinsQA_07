package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        if (dateNow.length() == 5) {
            Assert.assertEquals(getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText().substring(0, 5), dateNow);
        } else {
            Assert.assertEquals(getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText().substring(0, 6), dateNow);
        }
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