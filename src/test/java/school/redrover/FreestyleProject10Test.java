package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject10Test extends BaseTest {

    private final static String NAME_FREESTYLE_PROJECT = "Freestyle Project 1 2 3";
    private final static By LINK_ON_A_CREATED_FREESTYLE_PROJECT = By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a");

    private void creatingFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreatingFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NAME_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//tr[@id='job_" + NAME_FREESTYLE_PROJECT + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]/span/a")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1['Configure']")).getText(),
                "Configure");
    }

    @Test
    public void testFreestyleProjectAddDescription() {
        final String nameDescription = "Test123%#";

        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(nameDescription);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-0']/div[1]")).getText(),
                nameDescription);
    }

    @Test
    public void testRenameFreestyleProject() {
        final String newName = "Test Rename Project 3210";

        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();

        getDriver().findElement(By.name("newName")).sendKeys(newName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@class=' job-status-nobuilt']/td[3]/a")).getText(),
                newName);
    }

    @Test
    public void testDeleteFreestyleProject() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.xpath("//a[@data-message='Delete the Project ‘" + NAME_FREESTYLE_PROJECT + "’?']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElements(By.id("job_" + NAME_FREESTYLE_PROJECT)).size(), 0);
    }

    @Test
    public void testTooltipDiscardIsVisible() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]")).click();

        WebElement helpButton = getDriver().findElement(By.xpath("//a[@helpurl='/descriptor/hudson.model.FreeStyleProject/help/concurrentBuild']"));

        boolean toolTipIsVisible = true;
        new Actions(getDriver()).
                moveToElement(helpButton).
                perform();

        if (helpButton.getAttribute("title").equals("Help for feature: Execute concurrent builds if necessary")) {
            toolTipIsVisible = false;
        }

        Assert.assertTrue(toolTipIsVisible, "The tooltip message is not visible");
    }

    @Test
    public void testCheckTheBoxes() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]")).click();

        getDriver().findElement(By.xpath("//label[contains(text(),'GitHub project')]")).click();

        Assert.assertTrue(getDriver().findElement(By.id("cb5")).isSelected(), "Checkbox is not click");
    }

    @Test
    public void testDeletePermalinksOnProjectsStatusPage() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);

        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.cssSelector("a[onclick^='return build_']")).click();
        getDriver().navigate().refresh();

        getDriver().findElement(By.cssSelector("a[href='lastBuild/']")).click();
        getDriver().findElement(By.cssSelector("a[href$='confirmDelete']")).click();
        getDriver().findElement(By.cssSelector("button[formnovalidate]")).click();

        List<By> permaLinks = List.of(
                By.xpath("//ul[@class='permalinks-list']/li[1]"),
                By.xpath("//ul[@class='permalinks-list']/li[2]"),
                By.xpath("//ul[@class='permalinks-list']/li[3]"),
                By.xpath("//ul[@class='permalinks-list']/li[4]"));

        for (By link : permaLinks) {
        Assert.assertEquals(
                getDriver().findElements(link).size(),
                0);
        }
    }

    @Test
    public void testRenameUnsafeCharacters() {
        creatingFreestyleProject(NAME_FREESTYLE_PROJECT);
        getDriver().findElement(LINK_ON_A_CREATED_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.cssSelector("a[href$='confirm-rename']")).click();
        WebElement newName = getDriver().findElement(By.name("newName"));

        List<String> unsafeCharacters = List.of("%", "<", ">", "[", "]", "&", "#", "|", "/", "^");

        for (String x : unsafeCharacters) {
            newName.clear();
            newName.sendKeys(x);
            newName.sendKeys(Keys.TAB);
            getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='error']")));

            Assert.assertEquals(getDriver().findElement(By.cssSelector("div[class='error']")).getText(),
                    "‘" + x + "’ is an unsafe character");
        }
    }
}
