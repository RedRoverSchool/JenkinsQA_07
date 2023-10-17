package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupSurvivorsTest extends BaseTest {

    @Test
    public void  testEvgenyFindJavaPage() throws InterruptedException {
        getDriver().get("https://ru.wikipedia.org/wiki/");

        getDriver().findElement(By.className("vector-search-box-input")).sendKeys("Java");

        getDriver().findElement(By.xpath("//input[@class='searchButton']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("mw-page-title-main")).getText(), "Java");
    }

    @Test
    public void  testEvgenySetEnglish() throws InterruptedException {
        getDriver().get("https://www.erarta.com/");

        getDriver().findElement(By.cssSelector(".header__second-menu-link.lang-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='header__menu-list']/*[1]")).getText(), "museum");
    }

    @Test
    public void  testEvgenyFindMembershipPageBySearch() throws InterruptedException {
        getDriver().get("https://www.erarta.com/");

        getDriver().findElement(By.className("header__search-svg")).click();
        getDriver().findElement(By.cssSelector(".search-popup__input.base-input.search-input")).sendKeys("клубный билет");
        getDriver().findElement(By.className("search-popup__submit")).click();
        getDriver().findElement(By.xpath("//*[@class='search-page__result-item'][1]/*[@class='search-page__result-title']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("hero__title")).getText(), "клубный билет в музей Эрарта");
    }

    @Test
    public void testIuliaRadioGarden() throws InterruptedException {

        getDriver().get("https://radio.garden/");
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Radio Garden"));

        WebElement searchButton = getDriver().findElement(By.cssSelector("a[href='/search']"));
        searchButton.click();

        Thread.sleep(1000);

        WebElement textBox = getDriver().findElement(By.cssSelector("#search-input"));
        textBox.sendKeys("radiojazz\r\n");

        Thread.sleep(1000);

        WebElement searchResult = getDriver().findElement(By.xpath("//a[contains(@class, 'linkContainer')]"));
        searchResult.click();

        Thread.sleep(1000);

        WebElement channelBox = getDriver().findElement(By.xpath("//div[contains(@class, 'channelTitle')]"));
        String value = channelBox.getText();
        Assert.assertTrue(value.contains("RadioJAZZ.FM"));
    }
}