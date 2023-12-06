package school.redrover;

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
    private static final String REST_API = "REST API";

    @Test
    public void testVersionButtonHomePage() {
        String jenkinsVersionActual = new HomePage(getDriver())
                .getVersionJenkinsButton();

        Assert.assertEquals(jenkinsVersionActual, JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionButtonHomePage")
    public void testVersionAboutJenkinsPage() {
        String jenkinsVersion = new HomePage(getDriver())
                .goAboutJenkinsPage()
                .getJenkinsVersion();

        Assert.assertEquals(jenkinsVersion, ABOUT_JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionAboutJenkinsPage")
    public void testVersionStatusUserPage() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .getVersionJenkinsButton();

        Assert.assertEquals(jenkinsVersion, JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testVersionStatusUserPage")
    public void testJenkinsVersionStatusUserPageClick() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .goAboutJenkinsPage()
                .getJenkinsVersion();

        Assert.assertEquals(jenkinsVersion, ABOUT_JENKINS_VERSION);
    }

    @Test(dependsOnMethods = "testJenkinsVersionStatusUserPageClick")
    public void testCheckTippyBox() {
        final List<String> expectedMenu = List.of(
                "About Jenkins",
                "Get involved",
                "Website");

        List<String> actualMenu = new HomePage(getDriver())
                .clickJenkinsVersionButton(new HomePage(getDriver()))
                .getVersionJenkinsTippyBoxText();

        Assert.assertEquals(actualMenu, expectedMenu);
    }

    @Test
    public void testClickGetInvolved() {
        String actualPageName = new HomePage(getDriver())
                .goGetInvolvedWebsite()
                .getHeadLineText();

        Assert.assertEquals(actualPageName, "Participate and Contribute");
    }
    @Ignore("Unable to locate element: {\"method\":\"css selector\",\"selector\":\"a[href='https://www.jenkins.io/']\"}(..)")
    @Test
    public void testClickWebsite() {
        String actualPageName = new HomePage(getDriver())
                .goWebsiteJenkins()
                .getHeadLineText();

        Assert.assertEquals(actualPageName, "Jenkins");
    }

    @Test(dependsOnMethods = "testCheckTippyBox")
    public void testVerifyClickabilityOfRestAPILink() {
        String restApi = new HomePage(getDriver())
                .goRestApi()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testVerifyClickabilityOfRestAPILink")
    public void testJenkinsVersionListTabBar() {
        final List<String> expectedListTabBar = List.of(
                "Mavenized dependencies",
                "Static resources",
                "License and dependency information for plugins");

        List<String> tabBarList = new HomePage(getDriver())
                .goAboutJenkinsPage()
                .getTabBarText();

        Assert.assertEquals(tabBarList, expectedListTabBar);
    }

    @Test(dependsOnMethods = "testJenkinsVersionListTabBar")
    public void testVerifyAboutJenkinsTabNamesAndActiveStates() {
        AboutJenkinsPage about = new HomePage(getDriver())
                .goAboutJenkinsPage();

        for (int i = 0; i < about.getTabBarElements().size(); i++) {
            about.getTabBarElements().get(i).click();

            Assert.assertTrue(about.getTabPaneElements().get(i).isDisplayed());
        }
    }

    @Test(dependsOnMethods = "testVerifyAboutJenkinsTabNamesAndActiveStates")
    public void testRestApiLinkRedirectionPeople() {
        String restApi = new HomePage(getDriver())
                .clickPeople()
                .goRestApiPage()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionPeople")
    public void testRestApiLinkRedirectionBuildHistory() {
        String restApi = new HomePage(getDriver())
                .clickBuildHistoryButton()
                .goRestApi()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionBuildHistory")
    public void testRestApiLinkRedirectionMyView() {
        String restApi = new HomePage(getDriver())
                .clickMyView()
                .goRestApi()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionMyView")
    public void testRestApiLinkRedirectionUserStatus() {
        String restApi = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickRestApiButton()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionUserStatus")
    public void testRestApiLinkRedirectionUserBuild() {
        String restApi = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickBuildsButton()
                .goRestApiPage()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionUserBuild")
    public void testRestApiLinkRedirectionUserConfigure() {
        String restApi = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickConfigure()
                .goRestApiPage()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionUserConfigure")
    public void testRestApiLinkRedirectionUserMyViews() {
        String resApi = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .clickUserMyViews()
                .goRestApiPage()
                .getHeadLineText();

        Assert.assertEquals(resApi, REST_API);
    }

    @Test(dependsOnMethods = "testRestApiLinkRedirectionUserMyViews")
    public void testRestApiLinkRedirectionUserCredentials() {
        String restApi = new HomePage(getDriver())
                .clickUserNameHeader("admin")
                .goCredentialsPage()
                .goRestApiPage()
                .getHeadLineText();

        Assert.assertEquals(restApi, REST_API);
    }
}
