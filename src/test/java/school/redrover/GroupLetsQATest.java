package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;

import static org.testng.Assert.*;

public class GroupLetsQATest extends BaseTest {
    private static final String BASE_URL = "https://www.sawinery.net/";

    @Test
    public void checkTitleTest() {
        getDriver().get(BASE_URL);

        String title = getDriver().getTitle();
        Assert.assertEquals("Sawinery - #1 Woodworking Education Resource", title);
    }

    @Test
    public void searchTest() {
        getDriver().get(BASE_URL);

        WebElement searchField = getDriver().findElement(By.xpath("//*[@id='elementor-search-form-77435a6']"));
        searchField.sendKeys("saw");
        searchField.sendKeys(Keys.ENTER);


        WebElement searchResult = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/section[2]/div/div/div/div/div/div/article[1]/div/h3/a"));
        String searchText = searchResult.getAttribute("textContent").toLowerCase();
        assertTrue(searchText.contains("saw"));
    }


    @Test
    public void clickChromeTest() {
        getDriver().get("http://www.uitestingplayground.com/click");

        WebElement badButton = getDriver().findElement(By.id("badButton"));
        badButton.click();

        Assert.assertFalse("rgba(0, 123, 255, 1)".equals(badButton.getCssValue("background-color")));
    }


    @Test
    public void verifyTextTest() {
        getDriver().get("http://www.uitestingplayground.com/verifytext");

        WebElement textBlock = getDriver().findElement(By.xpath("//html/body/section/div/div[4]/span"));
        String text = textBlock.getText();

        Assert.assertEquals(text, "Welcome UserName!");
    }

    @Ignore
    @Test
    public void testConfIxbt() {

        getDriver().get("https://www.ixbt.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals("Новости технологий, обзоры гаджетов, смартфонов, бытовой техники и автомобилей", title);

        WebElement conference = getDriver().findElement(By.linkText("Конференция"));
        conference.click();

        String title_k = getDriver().getTitle();
        Assert.assertEquals("Конференция iXBT.com", title_k);
    }

    @Test
    public void testTextInput() {

        getDriver().get("http://www.uitestingplayground.com/textinput");
        WebElement newButtonNameField = getDriver().findElement(By.cssSelector("#newButtonName"));
        String newButtonName = "Changed Button Name";
        newButtonNameField.sendKeys(newButtonName);

        WebElement updatingButton = getDriver().findElement(By.cssSelector("#updatingButton"));
        updatingButton.click();

        String value = updatingButton.getText();
        Assert.assertEquals(newButtonName, value);

    }

    @Test
    public void testLOadDelays() {
        getDriver().get("http://www.uitestingplayground.com/loaddelay");
        WebElement homePageLink = getDriver().findElement(By.cssSelector(".nav-link[href='/home']"));
        homePageLink.click();
        WebElement loadDelaysLink = getDriver().findElement(By.xpath("//a[@href='/loaddelay']"));
        loadDelaysLink.click();
        WebElement buttonAppearingAfterDelay = getDriver().findElement(By.cssSelector(".btn-primary"));

        try {
            buttonAppearingAfterDelay.click();
            assertTrue(true);
        } catch (Exception e) {
            Assert.fail("The button Appearing After Delay is not clickable.");
        }

    }

    @Test
    public void searchDialogLichess() {
        getDriver().get("https://lichess.org");

        String title = getDriver().getTitle();
        assertTrue(title.contains("lichess.org"));

        WebElement button = getDriver().findElement(By.className("config_hook"));
        button.click();

        WebElement dialog = getDriver().findElement(By.className("dialog-content"));
        assertNotNull(dialog);
    }

    @Test
    public void testDescriptionTextAreaAppearsJenkinsProject() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        JenkinsUtils.login(getDriver());
        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();
        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testSaveDescriptionButtonAppearsJenkinsProject() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        JenkinsUtils.login(getDriver());
        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();

        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-button--primary")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void searchBoxJenkinsTest() {
        JenkinsUtils.login(getDriver());
        WebElement searchBox = getDriver().findElement(By.name("q"));
        searchBox.sendKeys("admin");
        searchBox.sendKeys(Keys.ENTER);

        try {
            WebElement searchResult = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]"));
            Assert.assertTrue(searchResult.getText().contains("admin"));
        } catch (Exception e) {
            System.out.println("You have no admin user");
        }

    }

    @Test
    public void versionJenkinsTest() {
        JenkinsUtils.login(getDriver());
        WebElement versionBox = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"));

        Assert.assertEquals(versionBox.getText(), "Jenkins 2.414.2");
    }

    @Test
    public void newItemButtonTest() {
        JenkinsUtils.login(getDriver());
        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span"));
        newItemButton.click();

        WebElement newItemSpan = getDriver().findElement(By.xpath("//*[@id='createItem']/div[1]/div/label"));

        Assert.assertEquals(newItemSpan.getText().trim(), "Enter an item name");
    }
}