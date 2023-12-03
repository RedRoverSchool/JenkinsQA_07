package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class BreadcrumbTest extends BaseTest {
	private static final String MAIN_PAGE_HEADING = "Welcome to Jenkins!";

	@Test
	public void testReturnHomePageFromNewItem() {
		String homePage = new HomePage(getDriver())
				.clickNewItem()
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testReturnHomePageFromNewItem")
	public void testReturnHomePageFromPeople() {
		String homePage = new HomePage(getDriver())
				.clickPeople()
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testReturnHomePageFromPeople")
	public void testReturnHomePageFromBuildHistory() {
		String homePage = new HomePage(getDriver())
				.clickBuildHistoryButton()
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testReturnHomePageFromBuildHistory")
	public void testReturnHomePageFromManageJenkins() {
		String homePage = new HomePage(getDriver())
				.clickManageJenkins()
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testReturnHomePageFromManageJenkins")
	public void testReturnHomePageFromMyViews() {
		String homePage = new HomePage(getDriver())
				.clickMyView()
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testReturnHomePageFromMyViews")
	public void testBuildInNodePageAndReturnOnMainPage() {
		String homePage = new HomePage(getDriver())
				.clickManageJenkins()
				.goNodesListPage()
				.clickNodeByName("")
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testBuildInNodePageAndReturnOnMainPage")
	public void testOnAdminPageAndReturnOnMainPage() {
		String homePage = new HomePage(getDriver())
				.clickManageJenkins()
				.clickUsersButton()
				.clickUserByName("admin")
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test(dependsOnMethods = "testOnAdminPageAndReturnOnMainPage")
	public void testConfigPageAndReturnOnMainPage() {
		String homePage = new HomePage(getDriver())
				.clickNewItem()
				.createFreestyleProject("Project Name")
				.clickDashboardBreadCrumb()
				.getTitle();

		Assert.assertEquals(homePage, "Dashboard [Jenkins]");
	}
}





















