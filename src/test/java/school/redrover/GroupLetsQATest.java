package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

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
        Assert.assertTrue(searchText.contains("saw"));
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


    @Test
    public void testConfIxbt(){

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
    public void testLOadDelays(){
        getDriver().get("http://www.uitestingplayground.com/loaddelay");
        WebElement homePageLink = getDriver().findElement(By.cssSelector(".nav-link[href='/home']"));
        homePageLink.click();
        WebElement loadDelaysLink = getDriver().findElement(By.xpath("//a[@href='/loaddelay']"));
        loadDelaysLink.click();
        WebElement buttonAppearingAfterDelay = getDriver().findElement(By.cssSelector(".btn-primary"));

            try {
                buttonAppearingAfterDelay.click();
                Assert.assertTrue(true);
            } catch (Exception e) {
                Assert.fail("The button Appearing After Delay is not clickable.");
            }

    }

    @Test
    public void searchDialogLichess() {
        getDriver().get("https://lichess.org");

        String title = getDriver().getTitle();
        assertEquals(title, "lichess.org • Бесплатные шахматы онлайн");

        WebElement button = getDriver().findElement(By.className("config_hook"));
        button.click();

        WebElement dialog = getDriver().findElement(By.className("dialog-content"));
        assertNotNull(dialog);
    }
}