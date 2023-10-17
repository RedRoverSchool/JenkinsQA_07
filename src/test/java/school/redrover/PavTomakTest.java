package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class PavTomakTest extends BaseTest {

    @Test
    public void testTypeIn() {

        getDriver().get("https://ymcacapecod.org/");

        WebElement textBox = getDriver().findElement(By.id("s"));
        textBox.sendKeys("Eastham");

        WebElement searchButton = getDriver().findElement(By.id("searchsubmit"));
        searchButton.click();

        WebElement title = getDriver().findElement(By.className("breadcrumb"));
        String value = title.getText();
        Assert.assertEquals(value, "You Are Here: YMCA Cape Cod > Search results for 'Eastham'");

    }

    @Test
    public void testMacsShack() {
        getDriver().get("https://macsseafood.com/restaurant/macs-shack-wellfleet/");
        WebElement orderOnline = getDriver().findElement(By.xpath("//*[text() = 'Order Online']"));
        orderOnline.click();
        WebElement macsFishHouse = getDriver().findElement(By.xpath("//main//a[text() = 'Mac’s Fish House Provincetown']"));
        Assert.assertTrue(macsFishHouse.isDisplayed());
    }

    @Test
    public void testCheckMenu() {
        getDriver().get("https://macsseafood.com/restaurant/macs-shack-wellfleet/");

        WebElement viewMenu = getDriver().findElement(By.id("menu-link"));
        viewMenu.click();

        WebElement aboutThisLocationButton = getDriver().findElement(By.xpath("//span[text() = 'About This Location']"));
        Assert.assertTrue(aboutThisLocationButton.isDisplayed());
    }

    @Test
    public void testJenkinsAdminUserLogin() {
        JenkinsUtils.login(getDriver());

        WebElement adminButton = getDriver().findElement(By.xpath("//*[@id='page-header']//*[@href='/user/admin']"));
        adminButton.click();
        WebElement userText = getDriver().findElement(By.xpath("//div[text() = 'Jenkins User ID: admin']"));
        Assert.assertTrue(userText.isDisplayed());
    }

    @Test
    public void testManageJenkins() {
        JenkinsUtils.login(getDriver());

        WebElement manageJenkinsBtn = getDriver().findElement(By.xpath("//*[@id='tasks']//a[@href='/manage']"));
        manageJenkinsBtn.click();

        WebElement systemConfig = getDriver().findElement(By.xpath("//h2[text()='System Configuration']"));
        WebElement security = getDriver().findElement(By.xpath("//h2[text()='Security']"));
        WebElement statusInfo = getDriver().findElement(By.xpath("//h2[text()='Status Information']"));
        WebElement troubleshooting = getDriver().findElement(By.xpath("//h2[text()='Troubleshooting']"));
        WebElement toolsAndActions = getDriver().findElement(By.xpath("//h2[text()='Tools and Actions']"));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(systemConfig.isDisplayed());
        softAssert.assertTrue(security.isDisplayed());
        softAssert.assertTrue(statusInfo.isDisplayed());
        softAssert.assertTrue(troubleshooting.isDisplayed());
        softAssert.assertTrue(toolsAndActions.isDisplayed());
        softAssert.assertAll();
    }
}
