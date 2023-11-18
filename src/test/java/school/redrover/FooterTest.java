package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FooterTest extends BaseTest {

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

    private void clickRestApi() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();
    }

    @Ignore
    @Test
    public void testVersionCheck() {
        getDriver().findElement(By.xpath("//div/button")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/button")).getText(),
                "Jenkins 2.414.2");

        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("page-footer__links")).getText(),
                "Jenkins 2.414.2");
    }

    @Test
    public void testVersionJenkins() {
        Assert.assertEquals(
                getDriver()
                        .findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"))
                        .getText(),
                "Jenkins 2.414.2");
    }

    @Ignore
    @Test
    public void testJenkinsVersionCheck() {
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//button[contains(text(),'Jenkins 2.414.2')]")).
                        getAttribute("innerText").trim(),
                "Jenkins 2.414.2");

        getDriver().findElement((By.className("page-footer__links"))).click();

        getDriver().findElement((By.className("jenkins-dropdown__item"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("app-about-version")).getText(),
                "Version 2.414.2");

        Assert.assertEquals(
                getDriver().findElement(By.className("page-footer__links")).getText(),
                "Jenkins 2.414.2");
    }

    @Ignore
    @Test
    public void testVersion() {
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".app-about-version")).getText(), "Version 2.414.2");
    }

    @Test
    public void testCheckTheVersion() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"))
                .getText(), "Jenkins 2.414.2");
    }

    @Test
    public void testJenkinsVersionCheck1() {
        getDriver().findElement(By.xpath("//a[@class]//span[@class='hidden-xs hidden-sm']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/manage/about/");
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//p[@class='app-about-version']"))
                .getText().contains("Version 2.414.2"));
    }

    @Ignore
    @Test
    public void checkTippyBox() throws InterruptedException {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")));

        List<String> expectedMenu = List.of("About Jenkins", "Get involved", "Website");
        List<String> actualMenu = new ArrayList<>();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        Thread.sleep(1000);
        List<WebElement> tippyBoxElements = getDriver().findElements(By.xpath("//div[@class = 'tippy-content']/a"));

        for (WebElement tippyContectMenu : tippyBoxElements) {
            actualMenu.add(tippyContectMenu.getText());
        }

        Assert.assertEquals(actualMenu, expectedMenu, "Tippy box context menu doesn't macth");
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

    @Ignore
    @Test
    public void testGetInvolved() throws InterruptedException {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")));

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();

        getDriver().findElement(By.xpath("//a[@href='https://www.jenkins.io/participate/']")).click();

        ArrayList<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.jenkins.io/participate/");
    }

    @Ignore
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

    @Ignore
    @Test
    public void testWebsite() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")));

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();

        getDriver().findElement(By.xpath("//a[@href='https://www.jenkins.io/']")).click();

        ArrayList<String> tab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tab.get(1));

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.jenkins.io/");
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

    @Ignore
    @Test
    public void testVerifyClickabilityOfRestAPILink() {
        clickRestApi();

        Assert.assertEquals(getDriver().getTitle(), "Remote API [Jenkins]");
    }

    @Ignore
    @Test(description = "Кликабельность ссылки и отображение страницы REST API")
    public void testvisabilityAndClickabilityRestApiLink() {
        String link = getDriver().findElement(By.xpath("//a[@href='api/']")).getText();
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String getTitle = getDriver().findElement(By.xpath("//h1[contains(text(),'REST API')]")).getText();

        Assert.assertEquals(link, "REST API", "заголовок не совпадает");
        Assert.assertEquals(getTitle, "REST API", "заголовок страницы не совпадает");
    }

    @Test
    public void testRestApiLinkClickable() {
        getDriver().findElement(By.id("executors")).click();
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), "REST API");
    }

    @Ignore
    @Test
    public void testJenkinsVersionButtonVisibilityCLikabilityFunctionality() {
        getDriver().findElement(By.xpath("//a[@class]//span[@class='hidden-xs hidden-sm']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();

        List<WebElement> listJenkinsDropdownItem = getDriver().findElements(By.xpath("//a[@class='jenkins-dropdown__item']"));

        Assert.assertEquals(listJenkinsDropdownItem.size(), 3);

        for (WebElement e : listJenkinsDropdownItem) {
            Assert.assertTrue(e.isDisplayed());
        }
        listJenkinsDropdownItem.get(0).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/manage/about/");

        List<WebElement> actualListTabBar = getDriver()
                .findElements(By.xpath("//div[@class='tabBar']//div[contains(@class,'tab')]"));
        List<String> expectedListTabBar = List.of("Mavenized dependencies",
                "Static resources", "License and dependency information for plugins");
        List<String> actualListTabBarGetText = new ArrayList<>();

        for (WebElement e : actualListTabBar) {
            actualListTabBarGetText.add(e.getText());
        }
        Assert.assertTrue(actualListTabBarGetText.containsAll(expectedListTabBar));
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

    @Test
    public void testVerifyRedirectedRestApi() {
        clickRestApi();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
    }

    @Test
    public void testClickRestApi() {
        getDriver().findElement(By.xpath("//a[@class='jenkins-button jenkins-button--tertiary rest-api']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testClickRestApi")
    public void testRestApiLinkRedirectionMainMenu() {
        List<By> pages = List.of(
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[5]/span/a"));

        for (By locator : pages) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.linkText("REST API")).click();

            Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed());
            Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
            getDriver().navigate().back();
        }
    }

    @Ignore
    @Test
    public void testRestApiLinkRedirectionUserArea() {
        List<By> userPages = List.of(
                By.xpath("//*[@id='page-header']/div[3]/a[1]/span"),
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[4]/span/a"),
                By.xpath("//*[@id='tasks']/div[5]/span/a"),
                By.xpath("//*[@id='tasks']/div[6]/span/a")
        );
        for (By locator : userPages) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.linkText("REST API")).click();
            Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='REST API']")).isDisplayed());
            String currentURL = getDriver().getCurrentUrl();
            Assert.assertTrue(currentURL.contains("api"));
            getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[1]/span")).click();
        }
    }
}
