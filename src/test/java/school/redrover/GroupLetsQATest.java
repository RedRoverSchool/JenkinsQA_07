package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;


public class GroupLetsQATest extends BaseTest {
    private static final String BASE_URL = "https://www.sawinery.net/";

    @Ignore
    @Test
    public void checkTitle() {
        getDriver().get(BASE_URL);

        String title = getDriver().getTitle();
        Assert.assertEquals("Sawinery - #1 Woodworking Education Resource", title);
    }

    @Ignore
    @Test
    public void searchTest() {
        try {
            getDriver().get(BASE_URL);

            WebElement searchField = getDriver().findElement(By.xpath("//*[@id='elementor-search-form-77435a6']"));
            searchField.sendKeys("saw");
            searchField.sendKeys(Keys.ENTER);

            Thread.sleep(1000);

            WebElement searchResult = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/section[2]/div/div/div/div/div/div/article[1]/div/h3/a"));
            String searchText = searchResult.getAttribute("textContent").toLowerCase();
            Assert.assertTrue(searchText.contains("saw"));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Ignore
    @AfterTest
    public void quitBrowser() {
        //driver.quit();
    }



    @Test
    public void testLOadDelays(){

        getDriver().get("http://www.uitestingplayground.com/loaddelay");

            WebElement homrPageLink = getDriver().findElement(By.cssSelector(".nav-link[href='/home']"));
            homrPageLink.click();

            WebElement laodDelaysLink = getDriver().findElement(By.xpath("//a[@href='/loaddelay']"));
            laodDelaysLink.click();
        WebElement buttonAppearigAfterDelay = getDriver().findElement(By.cssSelector(".btn-primary"));

            try {
                buttonAppearigAfterDelay.click();
                Assert.assertTrue(true);
            } catch (Exception e) {
                Assert.fail("The button Appearig After Delay is not clickable.");
            }

    }
    @Ignore
    @Test
    public void testTextInput() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.uitestingplayground.com/textinput");
        try {

            WebElement newButtonNameField = driver.findElement(By.cssSelector("#newButtonName"));
            String newButtonName = "Changed Button Name";
            newButtonNameField.sendKeys(newButtonName);

            WebElement updatingButton = driver.findElement(By.cssSelector("#updatingButton"));
            updatingButton.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            String value = updatingButton.getText();
            Assert.assertEquals(newButtonName, value);

        } finally {

            driver.quit();
        }
    }
    @Ignore
    @Test
    public void confIXBT(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ixbt.com/");

        String title = driver.getTitle();
        Assert.assertEquals("Новости технологий, обзоры гаджетов, смартфонов, бытовой техники и автомобилей", title);

        WebElement conference = driver.findElement(By.linkText("Конференция"));
        conference.click();

        String title_k = driver.getTitle();
        Assert.assertEquals("Конференция iXBT.com", title_k);

        driver.quit();
    }

    @Ignore
    @Test
    public void clickSafariTest() {
       WebDriver driver = new SafariDriver();
        driver.get("http://www.uitestingplayground.com/click");

        WebElement badButton = driver.findElement(By.id("badButton"));
        badButton.click();

        Assert.assertFalse("rgba(0, 123, 255, 1)".equals(badButton.getCssValue("background-color")));


        driver.quit();
    }

    @Ignore
    @Test
    public void clickChromeTest() {
        getDriver().get("http://www.uitestingplayground.com/click");

        WebElement badButton = getDriver().findElement(By.id("badButton"));
        badButton.click();

        Assert.assertFalse("rgba(0, 123, 255, 1)".equals(badButton.getCssValue("background-color")));


    }

    @Ignore
    @Test
    public void verifyTextTest() {
        getDriver().get("http://www.uitestingplayground.com/verifytext");

        WebElement textBlock = getDriver().findElement(By.xpath("//html/body/section/div/div[4]/span"));
        String text = textBlock.getText();

        Assert.assertEquals(text, "Welcome UserName!");


    }
}