package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupIntroVertsQaTest extends BaseTest {

    /**
     * DmitryS. Тесты
     */
    // region DmitryS. Добавляю в данный блок тесты.

    // endregion

    // region AkiMiraTest

    @Test (description = "Test of Jenkins Search field")
    public void testJenkinsSearchField () {

        WebElement searchField = getDriver().findElement(By.id("search-box"));
        searchField.sendKeys("admin");
        searchField.sendKeys(Keys.ENTER);
        WebElement searchResults = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]"));
        Assert.assertTrue(searchResults.isDisplayed());
        String value = searchResults.getText();
        Assert.assertTrue(value.contains("admin"));
    }
    // endregion

}
