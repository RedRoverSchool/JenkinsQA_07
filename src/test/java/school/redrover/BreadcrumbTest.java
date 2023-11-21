package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;

public class BreadcrumbTest extends BaseTest {
    private final String MAIN_PAGE = "Main Page";

    private void createDescriptionMainPage() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).sendKeys(MAIN_PAGE);
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
    public void testReturnHomepageFromAnyPageClickingDashboard() {
        List<By> sidebarItems = List.of(
                By.xpath("//a[@href='/view/all/newJob']"),
                By.xpath("//a[@href='/asynchPeople/']"),
                By.xpath("//a[@href='/view/all/builds']"),
                By.xpath("//a[@href='/manage']"),
                By.xpath("//a[@href='/me/my-views']"));
        for (By locator : sidebarItems) {
            getWait2().until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

            getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li/a[@class='model-link']"))).click();

            Assert.assertEquals(getDriver().findElement(By.xpath(
                            "//div[@class='empty-state-block']/h1")).getText()
                    , "Welcome to Jenkins!");
        }
    }

    @Test
    public void testReturnMainPageClickOnDashboardBreadcrumb() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span/a[@href='/asynchPeople/']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//div[@class ='jenkins-app-bar__content']/h1")).getText()
                , "People");

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//li[@class ='jenkins-breadcrumbs__list-item']/a"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//div[@class='empty-state-block']/h1")).getText()
                , "Welcome to Jenkins!");
    }

    @Test
    public void testBuildInNodePageAndReturnOnMainPage() {
        createDescriptionMainPage();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = '/manage']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = 'computer']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = '../computer/(built-in)/']"))).click();

        clickDashboardOnBreadcrumbBar();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("description"))).getText().contains(MAIN_PAGE));
    }

    @Test
    public void testConfigPageAndReturnOnMainPage() {
        createDescriptionMainPage();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a/span[1]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("jenkins-input"))).sendKeys("Project Name");

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("hudson_model_FreeStyleProject"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@id = 'ok-button']"))).click();

        clickDashboardOnBreadcrumbBar();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("description"))).getText().contains(MAIN_PAGE));
    }

    @Test
    public void testOnAdminPageAndReturnOnMainPage() {
        createDescriptionMainPage();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = '/manage']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = 'securityRealm/']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='people']/tbody/tr/td[2]/a"))).click();

        clickDashboardOnBreadcrumbBar();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("description"))).getText().contains(MAIN_PAGE));
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
    public void testReturnToDashboardFromMenuPages() {
        List<By> menuPages = List.of(
                By.xpath("//*[@id='tasks']/div[2]/span/a"),
                By.xpath("//*[@id='tasks']/div[3]/span/a"),
                By.xpath("//*[@id='tasks']/div[4]/span/a"),
                By.xpath("//*[@id='page-header']/div[3]/a[1]"));

        for (By locator : menuPages) {
            getWait2().until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

            assertTrue(thisIsDashboardPage());
        }
    }

    @Test
    public void testBreadcrumbDashboardMenuItemsSameAsSideMenuItems() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//a[text()='Dashboard']"))).perform();

        WebElement dashboardChevron = getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']//a[text()='Dashboard']/button"));
        new Actions(getDriver())
                .moveToElement(dashboardChevron)
                .pause(500)
                .perform();
        dashboardChevron.sendKeys(Keys.RETURN);

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tippy-3']")));

        List<WebElement> itemsListBreadcrumb = getDriver().findElements(By.xpath("//div[@id='tippy-3']//a"));

        Assert.assertTrue(itemsListBreadcrumb.size() > 0);

        List<String> nameListBreadcrumb = new ArrayList<>();
        for (WebElement element : itemsListBreadcrumb) {
            nameListBreadcrumb.add(element.getText());
        }

        List<WebElement> itemsListSideMenu = getDriver().findElements(By.xpath("//div[@class='task ']"));

        Assert.assertTrue(itemsListSideMenu.size() > 0);

        List<String> nameListSideMenu = new ArrayList<>();
        for (WebElement element : itemsListSideMenu) {
            nameListSideMenu.add(element.getText());
        }

        Assert.assertEquals(nameListBreadcrumb, nameListSideMenu);
    }
}





















