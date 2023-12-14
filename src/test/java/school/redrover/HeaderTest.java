package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.jobs.details.FreestyleProjectDetailsPage;
import school.redrover.model.HomePage;
import school.redrover.model.SearchResultQueryPage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class HeaderTest extends BaseTest {

    private static final String HOME_PAGE_HEAD_LINE_TEXT = "Welcome to Jenkins!";
    private static final String PROJECT_NAME = "Test project";

    @Test
    public void testComeHomePageFromNewItemPage() {
        String homePageHeadLineText = new HomePage(getDriver())
                .clickNewItem()
                .goHomePage()
                .getHeadLineText();

        Assert.assertEquals(homePageHeadLineText, HOME_PAGE_HEAD_LINE_TEXT);
    }

    @Test(dependsOnMethods = "testComeHomePageFromNewItemPage")
    public void testComeHomePageFromPeoplePage() {
        String homePageHeadLineText = new HomePage(getDriver())
                .clickPeople()
                .goHomePage()
                .getHeadLineText();

        Assert.assertEquals(homePageHeadLineText, HOME_PAGE_HEAD_LINE_TEXT);
    }

    @Test(dependsOnMethods = "testComeHomePageFromPeoplePage")
    public void testComeHomePageFromBuildHistoryPage() {
        String homePageHeadlineText = new HomePage(getDriver())
                .clickBuildHistoryButton()
                .goHomePage()
                .getHeadLineText();

        Assert.assertEquals(homePageHeadlineText, HOME_PAGE_HEAD_LINE_TEXT);
    }

    @Test(dependsOnMethods = "testComeHomePageFromBuildHistoryPage")
    public void testComeHomePageFromManageJenkinsPage() {
        String homePageHeadLineText = new HomePage(getDriver())
                .clickManageJenkins()
                .goHomePage()
                .getHeadLineText();

        Assert.assertEquals(homePageHeadLineText, HOME_PAGE_HEAD_LINE_TEXT);
    }

    @Test(dependsOnMethods = "testComeHomePageFromManageJenkinsPage")
    public void testComeHomePageFromMyViewsPage() {
        String homePageHeadLineText = new HomePage(getDriver())
                .clickMyViews()
                .goHomePage()
                .getHeadLineText();

        Assert.assertEquals(homePageHeadLineText, HOME_PAGE_HEAD_LINE_TEXT);
    }

    @Test(dependsOnMethods = "testComeHomePageFromMyViewsPage")
    public void testExactMatchSearchFunctionality() {
        String header = new HomePage(getDriver()).clickNewItem()
                .createFreestyleProject(PROJECT_NAME)
                .goSearchBox(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .getHeadLineText();

        Assert.assertEquals(header, "Project " + PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testExactMatchSearchFunctionality")
    public void testPartialMatchSearchFunctionality() {
        final String searchingRequest = PROJECT_NAME.substring(0, 5);

        List<String> resultSearch = new HomePage(getDriver())
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME + "1")
                .goHomePage()
                .clickNewItem()
                .createFreestyleProject(PROJECT_NAME + "2")
                .goSearchBox(searchingRequest, new SearchResultQueryPage(getDriver()))
                .getSearchResultQueryText();

        for (String x : resultSearch) {
            Assert.assertTrue(x.contains(searchingRequest));
        }
    }

    @Test(dependsOnMethods = "testPartialMatchSearchFunctionality")
    public void testRedirectionToStatusPageFromResultList() {
        final String searchingRequest = PROJECT_NAME.substring(0, 5);

        String statusPage = new HomePage(getDriver())
                .goSearchBox(searchingRequest, new SearchResultQueryPage(getDriver()))
                .clickSearchResultQuery(PROJECT_NAME, new FreestyleProjectDetailsPage(getDriver()))
                .getHeadLineText();

        Assert.assertEquals(statusPage, "Project " + PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRedirectionToStatusPageFromResultList")
    public void testHotKeysSearchAreaSelection() {
        WebElement searchHotKeys = new HomePage(getDriver())
                .getHotKeysFocusSearch()
                .getSearchBoxWebElement();

        Assert.assertTrue(searchHotKeys.equals(getDriver().switchTo().activeElement()));
    }
}
