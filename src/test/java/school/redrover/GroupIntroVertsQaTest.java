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
    @Test(description = "check Jenkins's version")
    public void testJenkinsVersion() {
        String jenkinsVersion = getDriver().findElement((By.xpath("//*[@type='button']"))).getText();
        Assert.assertEquals(jenkinsVersion, "Jenkins 2.414.2");
    }

    @Test(description = "check welcome header")
    public void testWelcomeHeader() {
        String welcomeHeader = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/h1")).getText();
        Assert.assertEquals(welcomeHeader, "Welcome to Jenkins!");
    }

    @Test(description = "check logout and start page")
    public void testLogoutButton() {
        getDriver().findElement(By.xpath("//*[@href = '/logout']")).click();
        String logoutButton = getDriver().findElement(By.xpath("//*/main/div/h1")).getText();
        Assert.assertEquals(logoutButton, "Sign in to Jenkins");
    }
    // endregion

    // region AkiMiraTest

    @Test(description = "Test of Jenkins Search field")
    public void testJenkinsSearchFieldPositiveAkiMira() {

        WebElement searchField = getDriver().findElement(By.id("search-box"));
        searchField.sendKeys("admin");
        searchField.sendKeys(Keys.ENTER);

        WebElement searchResults = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]"));
        Assert.assertTrue(searchResults.isDisplayed());
        String value = searchResults.getText();
        Assert.assertTrue(value.contains("admin"));
    }

    @Test(description = "New Freestyle project is created using valid name")
    public void testNewFreestyleProjectPositiveAkiMira() {

        WebElement newItem = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItem.click();

        WebElement itemNameField = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        itemNameField.sendKeys("Test");

        WebElement freestyleProjectField = getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]"));
        freestyleProjectField.click();

        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]"));
        submitButton.click();

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Test Config [Jenkins]");

    }
    // endregion

}
