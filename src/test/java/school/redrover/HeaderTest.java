package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class HeaderTest extends BaseTest {
    @Ignore
    @Test
    public void testReturningBackToMainPageFromMainMenuPages() {

        List<By> mainPageMenuItems = List.of(
            By.xpath("//a[@href='/view/all/newJob']"),
            By.xpath("//a[@href='/asynchPeople/']"),
            By.xpath("//a[@href='/view/all/builds']"),
            By.xpath("//a[@href='/manage']"),
            By.xpath("//a[@href='/me/my-views']"));

        for (By locator : mainPageMenuItems) {
            getDriver().findElement(locator).click();
            getDriver().findElement(By.id("jenkins-home-link")).click();

            Assert.assertEquals(getDriver().getTitle(), "Dashboard [Jenkins]");
        }
    }
    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createFreeStyleProject(String projectName) {
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testExactMatchSearchFunctionality() {
        final String itemName = "Test project";
        createFreeStyleProject(itemName);

        getDriver().findElement(By.name("q")).click();
        getDriver().findElement(By.name("q")).sendKeys(itemName);
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();

        String title = getDriver().getTitle();
        boolean isStatusPageSelected = getDriver()
                .findElement(By.linkText("Status"))
                .getAttribute("class")
                .contains("active");

        assertTrue(title.contains(itemName));
        assertTrue(isStatusPageSelected);
    }

    @Test
    public void testPartialMatchSearchFunctionality() {
        final String itemName1 = "Test project1";
        final String itemName2 = "Test project2";
        final String itemName3 = "Test project3";
        final String searchingRequest = itemName1.substring(0, 5);
        createFreeStyleProject(itemName1);
        createFreeStyleProject(itemName2);
        createFreeStyleProject(itemName3);

        getDriver().findElement(By.name("q")).click();
        getDriver().findElement(By.name("q")).sendKeys(searchingRequest);
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();

        boolean isResultMatchQuery = getDriver()
                .findElements(By.xpath("//div[@id='main-panel']//li"))
                .stream()
                .map(WebElement::getText)
                .allMatch(x -> x.contains(searchingRequest));
        assertTrue(isResultMatchQuery);
    }

    @Test
    public void testRedirectionToStatusPageFromResultList() {
        final String itemName = "Test project";
        final String searchRequest = itemName.substring(0, 5);
        createFreeStyleProject(itemName);

        getDriver().findElement(By.name("q")).click();
        getDriver().findElement(By.name("q")).sendKeys(searchRequest);
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();
        getDriver().findElement(By.linkText(itemName)).click();

        String title = getDriver().getTitle();
        boolean isStatusPageSelected = getDriver()
                .findElement(By.linkText("Status"))
                .getAttribute("class")
                .contains("active");

        assertTrue(title.contains(itemName));
        assertTrue(isStatusPageSelected);
    }

    @Ignore
    @Test
    public void testHotKeysSearchAreaSelection() {
        new Actions(getDriver())
                .keyDown(Keys.CONTROL)
                .sendKeys("k")
                .keyUp(Keys.CONTROL)
                .perform();

        boolean isFocused = (Boolean) ((JavascriptExecutor) getDriver()).executeScript(
                "return document.activeElement === arguments[0]", getDriver().findElement(By.name("q")));
        assertTrue(isFocused);
    }



}
