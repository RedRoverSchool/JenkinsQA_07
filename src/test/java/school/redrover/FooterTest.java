package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FooterTest extends BaseTest {

    public void clickDropdownItemJenkinsVersion(String itemText) {
        HomePage dropDown = new HomePage(getDriver())
                .clickJenkinsVersion()
                .clickDropDownItem(itemText);
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
    public void testCheckTheVersion() {
        String verJenkins = new HomePage(getDriver())
                .getVersion();

        Assert.assertEquals(verJenkins, "Jenkins 2.414.2");
    }

    @Test
    public void checkTippyBox() throws InterruptedException {
        List<String> expectedMenu = List.of("About Jenkins", "Get involved", "Website");
        List<String> actualMenu = new ArrayList<>();

        List<WebElement> tippyBoxElements = new HomePage(getDriver())
                .clickJenkinsVersion()
                .getListJenkins();

        for (WebElement tippyContectMenu : tippyBoxElements) {
            actualMenu.add(tippyContectMenu.getText());
        }

        Assert.assertEquals(actualMenu, expectedMenu, "Tippy box context menu doesn't macth");
    }

    @Test
    public void testClickGetInvolved() {
        String expectedPageName = "Participate and Contribute";
        String expectedPageTitle = "Participate and Contribute";
        String expectedItem = "Get involved";

        clickDropdownItemJenkinsVersionButton(expectedItem);

        String actualPageName = new HomePage(getDriver())
                .getHeaderText();
        String actualPageTitle = new HomePage(getDriver())
                .getTitle();

        Assert.assertEquals(actualPageName, expectedPageName, "The page name is not Participate and Contribute");
        Assert.assertEquals(actualPageTitle, expectedPageTitle, "The title is not Participate and Contribute");
    }

    @Test
    public void testVerifyClickabilityOfRestAPILink() {
        String getTitleRestApt = new HomePage(getDriver())
                .clickRestApi()
                .getTitlePage();

        Assert.assertEquals(getTitleRestApt, "Remote API [Jenkins]");
    }

    @Test
    public void testJenkinsVersionButtonVisibilityCLikabilityFunctionality() {

        List<WebElement> actualListsTabBar = new HomePage(getDriver())
                .clickJenkinsVersion()
                .clickAboutJenkins()
                .actualListsTabBar();

        List<String> expectedListTabBar = List.of("Mavenized dependencies",
                "Static resources", "License and dependency information for plugins");
        List<String> actualListTabBarGetText = new ArrayList<>();

        for (WebElement e : actualListsTabBar) {
            actualListTabBarGetText.add(e.getText());
        }
        Assert.assertTrue(actualListTabBarGetText.containsAll(expectedListTabBar));
    }

    @Test
    public void testVerifyRedirectedRestApi() {
        boolean urlApi = new HomePage(getDriver())
                .clickRestApi()
                .getApiUrl();

        Assert.assertTrue(urlApi);
    }

    @Test
    public void testClickRestApi() {
        Boolean expectNameApi = new HomePage(getDriver())
                .clickRestApi()
                .getRestApi();

        Assert.assertTrue(expectNameApi);
    }
}

