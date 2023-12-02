package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.AssertJUnit.assertTrue;

public class BreadcrumbTest extends BaseTest {
	private static final String MAIN_PAGE_HEADING = "Welcome to Jenkins!";

	private void createDescriptionMainPage() {
		getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
		getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).sendKeys(MAIN_PAGE_HEADING);
		getWait2().until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//button[@name='Submit' and contains(@class, 'jenkins-button jenkins-button--primary')]")))
				.click();
	}

	private void clickDashboardOnBreadcrumbBar() {
		List<WebElement> breadcrumbItems = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
				By.id("breadcrumbBar"))).findElements(By.cssSelector("li.jenkins-breadcrumbs__list-item"));
		for (WebElement item : breadcrumbItems) {
			if (item.getText().equals("Dashboard")) {
				getWait2().until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//a[@href='/' and contains(@class, 'model-link')]"))).click();
				break;
			}
		}
	}

	private boolean isBreadcrumbPresent() {
		List<WebElement> breadcrumbs = getDriver().findElements(By.id("breadcrumbBar"));
		return !breadcrumbs.isEmpty() && breadcrumbs.get(0).isDisplayed();
	}

	private boolean thisIsDashboardPage() {
		List<WebElement> dashboardElements = getDriver().findElements(By.id("main-panel"));
		return !dashboardElements.isEmpty() && dashboardElements.get(0).isDisplayed();
	}

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
				.goUserDatabasePage()
				.clickUserByName("admin")
				.clickDashboardBreadCrumb()
				.getHeadLineText();

		Assert.assertEquals(homePage, MAIN_PAGE_HEADING);
	}

	@Test
	public void testBreadcrumbOnMenuPages() {
		List<By> pages = List.of(
				By.xpath("//*[@id='tasks']/div[2]/span/a"),
				By.xpath("//*[@id='tasks']/div[3]/span/a"),
				By.xpath("//*[@id='tasks']/div[4]/span/a"),
				By.xpath("//*[@id='page-header']/div[3]/a[1]"));
		for (By locator : pages) {
			getWait2().until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

			assertTrue(isBreadcrumbPresent());
		}
	}

	@Test
	public void testConfigPageAndReturnOnMainPage() {
		String homePage = new HomePage(getDriver())
				.clickNewItem()
				.createFreestyleProject("Project Name")
				.clickDashboardBreadCrumb()
				.getTitle();

		Assert.assertEquals(homePage, "Dashboard [Jenkins]");
	}
}





















