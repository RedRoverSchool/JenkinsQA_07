package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Footer3Test extends BaseTest {
    private final String jenkinsExpectedVersion = "Jenkins 2.414.2";

    public WebElement jenkinsVersionButton() {
        return getDriver().findElement(By.xpath("//button[@type = 'button']"));
    }

    public void clickDropdownItemJenkinsVersion(String itemText) {
        jenkinsVersionButton().click();
        WebElement dropdownItem = getDriver().findElement(By.xpath("//a[contains(text(),'" + itemText + "')]"));
        dropdownItem.click();
    }

    public void clickDropdownItemJenkinsVersionButton(String dropdownItem) {
        String startWindow = getDriver().getWindowHandle();

        clickDropdownItemJenkinsVersion(dropdownItem);

        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!startWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }
    }

    @Test
    public void testJenkinsVersion() {

        Assert.assertEquals(jenkinsVersionButton().getText(), jenkinsExpectedVersion);
    }

    @Test
    public void testClickAboutJenkins() {
        String expectedPageName = "Jenkins";
        String expectedPageTitle = "About Jenkins 2.414.2 [Jenkins]";
        String expectedItem = "About Jenkins";

        clickDropdownItemJenkinsVersionButton(expectedItem);

        String actualPageName = getDriver().findElement(By.tagName("h1")).getText();
        String actualPageTitle = getDriver().getTitle();

        Assert.assertEquals(actualPageName, expectedPageName, "The page name is not Jenkins");
        Assert.assertEquals(actualPageTitle, expectedPageTitle, "The title is not About Jenkins 2.414.2 [Jenkins]");
    }

    @Test
    public void testClickGetInvolved() {
        String expectedPageName = "Participate and Contribute";
        String expectedPageTitle = "Participate and Contribute";
        String expectedItem = "Get involved";

        clickDropdownItemJenkinsVersionButton(expectedItem);

        String actualPageName = getDriver().findElement(By.tagName("h1")).getText();
        String actualPageTitle = getDriver().getTitle();

        Assert.assertEquals(actualPageName, expectedPageName, "The page name is not Participate and Contribute");
        Assert.assertEquals(actualPageTitle, expectedPageTitle, "The title is not Participate and Contribute");
    }

    @Test
    public void testClickWebsite() {
        String expectedPageName = "Jenkins";
        String expectedPageTitle = "Jenkins";
        String expectedItem = "Website";

        clickDropdownItemJenkinsVersionButton(expectedItem);

        String actualPageName = getDriver().findElement(By.tagName("h1")).getText();
        String actualPageTitle = getDriver().getTitle();

        Assert.assertEquals(actualPageName, expectedPageName, "The page name is not Jenkins");
        Assert.assertEquals(actualPageTitle, expectedPageTitle, "The title is not Jenkins");
    }

    @Test
    public void testVerifyAboutJenkinsTabNamesAndActiveStates() {
        String aboutJenkins = "About Jenkins";

        clickDropdownItemJenkinsVersionButton(aboutJenkins);

        List<WebElement> tabs = getDriver().findElements(By.cssSelector(".tabBar .tab"));

        String[] expectedTabNames = {"Mavenized dependencies", "Static resources", "License and dependency information for plugins"};
        for (int i = 0; i < tabs.size(); i++) {

            Assert.assertEquals(tabs.get(i).getText(), expectedTabNames[i]);

            tabs.get(i).click();
            WebElement activeTab = getDriver().findElement(By.cssSelector(".tabBar .tab.active"));
            Assert.assertTrue(activeTab.getText().equals(expectedTabNames[i]));
        }
    }
}
