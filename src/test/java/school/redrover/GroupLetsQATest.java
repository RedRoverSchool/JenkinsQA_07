package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupLetsQATest extends BaseTest {

    private static final String BASE_URL = "https://www.sawinery.net/";
    @Ignore
    @Test
    public void checkTitleTest() {
        getDriver().get(BASE_URL);

        String title = getDriver().getTitle();
        Assert.assertEquals("Sawinery - #1 Woodworking Education Resource", title);
    }

    @Ignore
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
