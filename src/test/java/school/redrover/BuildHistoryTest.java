package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.BuildHistoryPage;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.FreestyleProjectDetailsPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BuildHistoryTest extends BaseTest {

    @Test
    public void testViewBuildHistory() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickBuildHistoryButton();

        Assert.assertTrue(buildHistoryPage.getMainPanel().isDisplayed());
    }

    @Test
    public void testViewBuildHistoryClickableIconLegend() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickBuildHistoryButton()
                .clickIconLegendButton();

        Assert.assertEquals(buildHistoryPage.getIconLegendHeaders().size(), 2);
    }

    @Test
    public void testCheckDateAndMonthBuildHistory() {
        Date systemDate = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        String dateNow = formatForDateNow.format(systemDate);

        FreestyleProjectDetailsPage buildHistoryPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("Test")
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .clickBuildNowButton();

        if (dateNow.length() == 5) {
            Assert.assertEquals(getWait10().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText()
                    .substring(0, 5), dateNow);
        } else {
            Assert.assertEquals(getWait10().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText()
                    .substring(0, 6), dateNow);
        }
    }
}