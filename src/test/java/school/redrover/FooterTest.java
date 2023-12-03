package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.AboutJenkinsPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FooterTest extends BaseTest {
    private static final String JENKINS_VERSION = "Jenkins 2.414.2";
    private static final String ABOUT_JENKINS_VERSION = "Version 2.414.2";

    @Test
    public void testVersionButtonHomePage() {
        String jenkinsVersionActual = new HomePage(getDriver())
                .getVersion();

        Assert.assertEquals(jenkinsVersionActual, JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionButtonHomePage")
    public void testVersionAboutJenkinsPage() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickJenkinsVersion()
                .clickAboutJenkins()
                .getJenkinsVersion();

        Assert.assertEquals(jenkinsVersion, ABOUT_JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionAboutJenkinsPage")
    public void testVersionStatusUserPage() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .getJenkinsVersion();

        Assert.assertEquals(jenkinsVersion, JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionStatusUserPage")
    public void testJenkinsVersionStatusUserPageClick() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickVersionJenkins()
                .clickAboutJenkins()
                .getJenkinsVersion();

        Assert.assertEquals(jenkinsVersion, ABOUT_JENKINS_VERSION);
    }


    @Test
    public void testCheckTippyBox() {
        final List<String> expectedMenu = List.of(
                "About Jenkins",
                "Get involved",
                "Website");

        List<String> actualMenu = new HomePage(getDriver())
                .clickJenkinsVersion()
                .getVersionJenkinsTippyBoxText();
        Assert.assertEquals(actualMenu, expectedMenu);
    }

    @Test(dependsOnMethods = "testCheckTippyBox")
    public void testClickAboutJenkins() {

        String actualPageName = new HomePage(getDriver())
                .moveAboutJenkinsPage()
                .getHeadLineText();

        Assert.assertEquals(actualPageName, "Jenkins");
    }

    @Ignore
    @Test
    public void testClickGetInvolved() {
        String actualPageName = new HomePage(getDriver())
                .clickJenkinsVersion()
                .clickGetInvolved()
                .getHeadLineText();

        Assert.assertEquals(actualPageName, "Participate and Contribute");
    }

    @Test
    public void testClickWebsite() {
        String actualPageName = new HomePage(getDriver())
                .clickJenkinsVersion()
                .clickWebsite()
                .getHeadLineText();

        Assert.assertEquals(actualPageName, "Jenkins");
    }

    @Test(dependsOnMethods = "testClickAboutJenkins")
    public void testVerifyClickabilityOfRestAPILink() {
        String restApi = new HomePage(getDriver())
                .clickRestApiButton()
                .getHeadLineText();

        Assert.assertEquals(restApi, "REST API");
    }

    @Test(dependsOnMethods = "testVerifyClickabilityOfRestAPILink")
    public void testJenkinsVersionListTabBar() {
        final List<String> expectedListTabBar = List.of(
                "Mavenized dependencies",
                "Static resources",
                "License and dependency information for plugins");

        List<String> tabBarList = new HomePage(getDriver())
                .moveAboutJenkinsPage()
                .getTabBarText();

        Assert.assertEquals(tabBarList, expectedListTabBar);
    }

    @Test(dependsOnMethods = "testJenkinsVersionListTabBar")
    public void testVerifyAboutJenkinsTabNamesAndActiveStates() {
        AboutJenkinsPage active = new HomePage(getDriver())
                .moveAboutJenkinsPage();

        for (int i = 0; i < active.getTabBarElements().size() || i < active.getTabPaneElements().size(); i++) {
            active.getTabBarElements().get(i).click();

            Assert.assertTrue(active.getTabPaneElements().get(i).isDisplayed());
        }
    }

    @Ignore
    @Test
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
